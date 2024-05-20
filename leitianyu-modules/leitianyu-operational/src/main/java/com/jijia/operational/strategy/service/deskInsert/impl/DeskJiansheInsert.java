package com.jijia.operational.strategy.service.deskInsert.impl;

import com.jijia.common.core.constant.SecurityConstants;
import com.jijia.common.core.exception.GlobalException;
import com.jijia.common.core.utils.StringUtils;
import com.jijia.operational.annotation.DeskInsertTypeAnnotation;
import com.jijia.operational.domain.OpDesk;
import com.jijia.operational.domain.OpProjectMsg;
import com.jijia.operational.domain.vo.OpDeskVo;
import com.jijia.operational.mapper.OpProjectMsgMapper;
import com.jijia.operational.service.IOpParameterService;
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
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author leitianyu
 */
@Component
@DeskInsertTypeAnnotation(deskInsertType = DeskInsertType.JIAN_SHE)
public class DeskJiansheInsert extends AbstractDeskInsertStrategy {

    @Resource
    private  IOpParameterService opParameterService;
    @Resource
    private RemoteConfigService remoteConfigService;
    @Resource
    private IOpReadyDeskService opReadyDeskService;
    @Resource
    private OpProjectMsgMapper opProjectMsgMapper;


    private static final String JIAN_SHE = "JJ";
    private static final String JIAN_SHE_ENTRUST = "JJW-";

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
        if (StringUtils.isEmpty(opProjectMsg.getRegion())) {
            throw new GlobalException(opProjectMsg +"地区未填写");
        }
        OpProjectMsg lastMsg = opProjectMsgMapper.selectOpProjectMsgBylastAndType(opProjectMsg);
        //如果没有最后一条数据
        if (lastMsg==null) {
            if (opProjectMsg.getRegion().equals(DeskConstants.DONGGUAN)) {
                opProjectMsg.setProjectCode(DeskConstants.A001);
                opProjectMsg.setProjectChahaoCode(DeskConstants.N001);
            }else {
                opProjectMsg.setProjectCode(DeskConstants.G001);
                opProjectMsg.setProjectChahaoCode(DeskConstants.T001);
            }
            return super.insertOpProjectMsg(opProjectMsg);
        }
        super.creatProjectCode(opProjectMsg,lastMsg);
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
        super.checkTime(remoteConfigService.getConfigKey(ConfigConstants.DESK_JIANSHE_TIME, SecurityConstants.INNER),opDeskVo.getEntrustmentTime());
        //查询插号状态
        checkInterpolation(opDeskVo);

        //生成初步编号
        //查询最后一条数据
        OpDesk lastDesk = super.findLast(opDeskVo);
        //输入编号前缀
        opDeskVo.setReportSerialNumber(JIAN_SHE);
        opDeskVo.setRecordSerialNumber(JIAN_SHE);
        opDeskVo.setEntrustSerialNumber(JIAN_SHE_ENTRUST);
        //生成报告编号和委托编号
        super.createReportNumberAndEntrustNumber(opDeskVo,lastDesk);
        //生成样品编号
        programInsertStrategy.creatNumber(opDeskVo, lastDesk);

        return opDeskVo;

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
            int maxRecordSerialNumber = Integer.parseInt(lastDesk.getRecordSerialNumber().substring(12));
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
}
