package com.jijia.operational.strategy.service.deskInsert.impl;

import com.jijia.common.core.constant.SecurityConstants;
import com.jijia.common.core.exception.GlobalException;
import com.jijia.common.core.web.domain.AjaxResult;
import com.jijia.operational.annotation.DeskInsertTypeAnnotation;
import com.jijia.operational.domain.OpDesk;
import com.jijia.operational.domain.OpProjectMsg;
import com.jijia.operational.domain.vo.OpDeskVo;
import com.jijia.operational.mapper.OpDeskMapper;
import com.jijia.operational.mapper.OpProjectMsgMapper;
import com.jijia.operational.service.IOpParameterService;
import com.jijia.operational.service.IOpProjectMsgService;
import com.jijia.operational.service.IOpReadyDeskService;
import com.jijia.operational.strategy.handler.HandlerProgramContext;
import com.jijia.operational.strategy.service.deskInsert.abstractImpl.AbstractDeskInsertStrategy;
import com.jijia.operational.strategy.service.programInsert.ProgramInsertStrategy;
import com.jijia.operational.utils.constants.ConfigConstants;
import com.jijia.operational.utils.constants.DeskConstants;
import com.jijia.operational.utils.enums.DeskInsertType;
import com.jijia.system.api.RemoteConfigService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author leitianyu
 */
@Component
@DeskInsertTypeAnnotation(deskInsertType = DeskInsertType.SHUI_LI_24)
public class Desk24ShuiliInsert extends AbstractDeskInsertStrategy {

    @Resource
    private RemoteConfigService remoteConfigService;
    @Resource
    private IOpReadyDeskService opReadyDeskService;
    @Resource
    private OpProjectMsgMapper opProjectMsgMapper;
    @Resource
    private IOpProjectMsgService opProjectMsgService;
    @Resource
    private OpDeskMapper opDeskMapper;

    private final IOpParameterService opParameterService;
    public Desk24ShuiliInsert(IOpParameterService opParameterService) {
        this.opParameterService = opParameterService;
    }



    private static final String SHUI_LI = "SL-JJ";
    private static final String SHUI_LI_24 = "SL-JJB";
    private static final String SHUI_LI_ENTRUST_NEW = "SL-JJW";


    /**
     * 编号生成（默认）
     * @param opDeskVo 处理后的新增数据
     * @return 默认数据
     */
    @Override
    public int addDesk(OpDeskVo opDeskVo) {
        if (opDeskVo.getEntrustmentTime().isAfter(LocalDate.now())) {
            return opReadyDeskService.addOpReadyDeskByDeskVo(opDeskVo);
        }
        creatNumber(opDeskVo);
        return super.addDesk(opDeskVo);
    }

    /**
     * 根据工程类型生成编号
     *
     * @param opProjectMsg 工程信息
     * @return 行数
     */
    @Override
    public int insertOpProjectMsg(OpProjectMsg opProjectMsg) {
        if (opProjectMsgMapper.searchOne(opProjectMsg)!= 0) {
            throw new GlobalException("已有相同名称与委托单位的工程编号存在");
        }
        OpProjectMsg lastMsg = opProjectMsgMapper.selectOpProjectMsgBylast(opProjectMsg.getProjectType());
        //如果没有最后一条数据
        if (lastMsg==null) {
            opProjectMsg.setProjectCode(DeskConstants.A001);
            opProjectMsg.setProjectChahaoCode(DeskConstants.N001);

            return super.insertOpProjectMsg(opProjectMsg);
        }
        super.creatProjectCode(opProjectMsg,lastMsg);
        opProjectMsg.setRegion(null);
        return super.insertOpProjectMsg(opProjectMsg);
    }

    /**
     * 修改工程编号
     *
     * @param opProjectMsg 工程信息
     * @return 行数
     */
    @Override
    public int updateOpProjectMsg(OpProjectMsg opProjectMsg) {
        return opProjectMsgMapper.updateOpProjectMsgByUser(opProjectMsg);
    }

    @Override
    public OpDeskVo creatNumber(OpDeskVo opDeskVo) {
        ProgramInsertStrategy programInsertStrategy = HandlerProgramContext.getInstance(opDeskVo.getProgramType());
        programInsertStrategy.startBegin(opDeskVo);
        super.checkTime(remoteConfigService.getConfigKey(ConfigConstants.DESK_SHUILI_TIME, SecurityConstants.INNER),opDeskVo.getEntrustmentTime());
        //查询插号状态
        this.checkInterpolation(opDeskVo);

        //生成初步编号
        //查询最后一条数据
        OpDesk lastDesk = this.findLast(opDeskVo);
        //输入编号前缀
        opDeskVo.setRecordSerialNumber(SHUI_LI);
        opDeskVo.setReportSerialNumber(SHUI_LI_24);
        opDeskVo.setEntrustSerialNumber(SHUI_LI_ENTRUST_NEW);
        createReportNumberAndEntrustNumberAndRecord(opDeskVo,lastDesk);

        return opDeskVo;
    }


    public void createReportNumberAndEntrustNumberAndRecord(OpDeskVo opDeskVo, OpDesk last) {
        //创造计数器
        AtomicInteger atomicInteger = new AtomicInteger(0);
        //获取委托日期
        String entrustment = String.valueOf(opDeskVo.getEntrustmentTime());
        //查询编号台账
        OpProjectMsg opProjectMsg = opProjectMsgService.selectOpProjectMsgByProjectId(opDeskVo.getProjectId());

        StringBuilder reportBuilder = new StringBuilder(opDeskVo.getReportSerialNumber());
        StringBuilder entrustBuilder = new StringBuilder(opDeskVo.getEntrustSerialNumber());
        String code;
        //判断插号情况
        if (opDeskVo.getIsInterpolation().equals(DeskConstants.DESK_IS_INTERPOLATION)) {
            code = opProjectMsg.getProjectChahaoCode();
            //插号
        } else {
            code = opProjectMsg.getProjectCode();
            //不插号
        }
        reportBuilder.append(code);
        entrustBuilder.append(code);
        ProgramInsertStrategy programInsertStrategy = HandlerProgramContext.getInstance(opDeskVo.getProgramType());
        //生成样品编号
        programInsertStrategy.creatNumberBy24(opDeskVo, last, code);
        //获取报告编号尾数
        atomicInteger.set(super.getReportSerialNumberLast(opDeskVo));
        //拼接字符
        reportBuilder.append(opDeskVo.getParameterCode())
                .append(entrustment, 2, 4)
                .append(entrustment, 5, 7)
                .append(String.format("%04d",atomicInteger.incrementAndGet()));
        //获取委托编号尾数
        atomicInteger.set(0);
        if (last != null) {
            atomicInteger.set(Integer.parseInt(last.getEntrustSerialNumber().substring(last.getEntrustSerialNumber().length() - 4)));
        } else if (opDeskVo.getIsInterpolation().equals(DeskConstants.DESK_IS_INTERPOLATION)) {
            atomicInteger.set(opParameterService.selectMaxNumber(opDeskVo.getParameterCode()));
        }
        entrustBuilder.append(opDeskVo.getParameterCode())
                .append(entrustment, 2, 4)
                .append(entrustment, 5, 7)
                .append(String.format("%04d",atomicInteger.incrementAndGet()));

        //报告编号输入
        opDeskVo.setReportSerialNumber(reportBuilder.toString());
        opDeskVo.setEntrustSerialNumber(entrustBuilder.toString());
    }

    /**
     * 判断是否超出额度
     *
     * @param opDeskVo 查询对象
     * @param lastDesk 最后一条数据
     * @return 是否为插号
     */
    @Override
    public String checkExceedNumber(OpDeskVo opDeskVo, OpDesk lastDesk) {
        //判断是否直接超出限额
        Integer maxNumber = opParameterService.selectMaxNumber(opDeskVo.getParameterCode());
        if (maxNumber < opDeskVo.getSampleAmount()) {
            return DeskConstants.DESK_IS_INTERPOLATION;
        }

        //判断是否此条工程代码这月超过限额
        AtomicInteger atomicInteger = new AtomicInteger(0);
        if (lastDesk != null) {
            int maxRecordSerialNumber = Integer.parseInt(lastDesk.getRecordSerialNumber().substring(lastDesk.getRecordSerialNumber().length() - 4));
            atomicInteger.addAndGet(maxRecordSerialNumber);
            atomicInteger.addAndGet(lastDesk.getSampleAmount());
            atomicInteger.decrementAndGet();
        }
        if (opDeskVo.getRepeatNumber() != null && opDeskVo.getRepeatNumber() > 1) {
            for (int integer = 0; integer < opDeskVo.getRepeatNumber(); integer++) {
                atomicInteger.addAndGet(opDeskVo.getSampleAmount());
            }
        } else {
            atomicInteger.addAndGet(opDeskVo.getSampleAmount());
        }
        if (atomicInteger.get() > maxNumber) {
            return DeskConstants.DESK_IS_INTERPOLATION;
        }

        return DeskConstants.DESK_NOT_INTERPOLATION;
    }


    @Override
    public int updateDesk(OpDeskVo opDeskVo, OpDeskVo oldDesk) {
        return super.updateDesk(opDeskVo, oldDesk);
    }

    @Override
    public OpDesk findLast(OpDeskVo opDeskVo) {
        //添加月份条件
        opDeskVo.setFirstTime(opDeskVo.getEntrustmentTime().with(TemporalAdjusters.firstDayOfMonth()));
        opDeskVo.setLastTime(opDeskVo.getEntrustmentTime().with(TemporalAdjusters.lastDayOfMonth()));
        //返回数据
        return opDeskMapper.getLastOpDeskByProjectId(opDeskVo);
    }


    @Override
    public void checkInterpolation(OpDeskVo opDeskVo) {
        //插号直接返回
        if (opDeskVo.getIsInterpolation().equals(DeskConstants.DESK_IS_INTERPOLATION)) {
            return;
        }

        //判断是否上传
        AjaxResult configKey = remoteConfigService.getConfigKey(ConfigConstants.CONFIG_PAST_TIME, SecurityConstants.INNER);
        if (configKey.isError()) {
            throw new GlobalException("配置日期信息错误，请立刻联系管理员！");
        }
        LocalDate past = LocalDate.parse(configKey.get(AjaxResult.MSG_TAG).toString());
        if (!opDeskVo.getEntrustmentTime().isAfter(past)) {
            opDeskVo.setIsInterpolation(DeskConstants.DESK_IS_INTERPOLATION);
            return;
        }

        //寻找最后一条数据
        OpDesk lastDesk = findLast(opDeskVo);
        if (lastDesk != null && lastDesk.getEntrustmentTime().isAfter(opDeskVo.getEntrustmentTime())) {
            opDeskVo.setIsInterpolation(DeskConstants.DESK_IS_INTERPOLATION);
            return;
        }
        //判断数量限制
        opDeskVo.setIsInterpolation(checkExceedNumber(opDeskVo,lastDesk));
    }
}
