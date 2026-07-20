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
 * 住建资质台账生成策略。
 *
 * @author leitianyu
 */
@Component
@DeskInsertTypeAnnotation(deskInsertType = DeskInsertType.ZHUJIAN_ZIZHI)
public class DeskZhujianZizhiInsert extends AbstractDeskInsertStrategy {

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

    public DeskZhujianZizhiInsert(IOpParameterService opParameterService) {
        this.opParameterService = opParameterService;
    }

    private static final String ZHUJIAN = "ZJ-JJ";
    private static final String ZHUJIAN_REPORT = "ZJ-JJB";
    private static final String ZHUJIAN_ENTRUST = "ZJ-JJW";

    @Override
    public int addDesk(OpDeskVo opDeskVo) {
        if (opDeskVo.getEntrustmentTime().isAfter(LocalDate.now())) {
            return opReadyDeskService.addOpReadyDeskByDeskVo(opDeskVo);
        }
        creatNumber(opDeskVo);
        return super.addDesk(opDeskVo);
    }

    @Override
    public int insertOpProjectMsg(OpProjectMsg opProjectMsg) {
        if (opProjectMsgMapper.searchOne(opProjectMsg) != 0) {
            throw new GlobalException("已有相同名称与委托单位的工程编号存在");
        }
        OpProjectMsg lastMsg = opProjectMsgMapper.selectOpProjectMsgBylast(opProjectMsg.getProjectType());
        if (lastMsg == null) {
            opProjectMsg.setProjectCode(DeskConstants.A001);
            opProjectMsg.setProjectChahaoCode(DeskConstants.N001);
            return super.insertOpProjectMsg(opProjectMsg);
        }
        super.creatProjectCode(opProjectMsg, lastMsg);
        opProjectMsg.setRegion(null);
        return super.insertOpProjectMsg(opProjectMsg);
    }

    @Override
    public int updateOpProjectMsg(OpProjectMsg opProjectMsg) {
        return opProjectMsgMapper.updateOpProjectMsgByUser(opProjectMsg);
    }

    @Override
    public OpDeskVo creatNumber(OpDeskVo opDeskVo) {
        ProgramInsertStrategy programInsertStrategy = HandlerProgramContext.getInstance(opDeskVo.getProgramType());
        programInsertStrategy.startBegin(opDeskVo);
        super.checkTime(remoteConfigService.getConfigKey(ConfigConstants.DESK_SHUILI_TIME, SecurityConstants.INNER), opDeskVo.getEntrustmentTime());
        checkInterpolation(opDeskVo);

        OpDesk lastDesk = findLast(opDeskVo);
        opDeskVo.setRecordSerialNumber(ZHUJIAN);
        opDeskVo.setReportSerialNumber(ZHUJIAN_REPORT);
        opDeskVo.setEntrustSerialNumber(ZHUJIAN_ENTRUST);
        createReportNumberAndEntrustNumberAndRecord(opDeskVo, lastDesk);
        return opDeskVo;
    }

    private void createReportNumberAndEntrustNumberAndRecord(OpDeskVo opDeskVo, OpDesk last) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        String entrustment = String.valueOf(opDeskVo.getEntrustmentTime());
        OpProjectMsg opProjectMsg = opProjectMsgService.selectOpProjectMsgByProjectId(opDeskVo.getProjectId());

        StringBuilder reportBuilder = new StringBuilder(opDeskVo.getReportSerialNumber());
        StringBuilder entrustBuilder = new StringBuilder(opDeskVo.getEntrustSerialNumber());
        String code;
        if (opDeskVo.getIsInterpolation().equals(DeskConstants.DESK_IS_INTERPOLATION)) {
            code = opProjectMsg.getProjectChahaoCode();
        } else {
            code = opProjectMsg.getProjectCode();
        }
        reportBuilder.append(code);
        entrustBuilder.append(code);

        ProgramInsertStrategy programInsertStrategy = HandlerProgramContext.getInstance(opDeskVo.getProgramType());
        programInsertStrategy.creatNumberBy24(opDeskVo, last, code);

        atomicInteger.set(super.getReportSerialNumberLast(opDeskVo));
        reportBuilder.append(opDeskVo.getParameterCode())
                .append(entrustment, 2, 4)
                .append(entrustment, 5, 7)
                .append(String.format("%04d", atomicInteger.incrementAndGet()));

        atomicInteger.set(0);
        if (last != null) {
            atomicInteger.set(Integer.parseInt(last.getEntrustSerialNumber().substring(last.getEntrustSerialNumber().length() - 4)));
        } else if (opDeskVo.getIsInterpolation().equals(DeskConstants.DESK_IS_INTERPOLATION)) {
            atomicInteger.set(opParameterService.selectMaxNumber(opDeskVo.getParameterCode()));
        }
        entrustBuilder.append(opDeskVo.getParameterCode())
                .append(entrustment, 2, 4)
                .append(entrustment, 5, 7)
                .append(String.format("%04d", atomicInteger.incrementAndGet()));

        opDeskVo.setReportSerialNumber(reportBuilder.toString());
        opDeskVo.setEntrustSerialNumber(entrustBuilder.toString());
    }

    @Override
    public String checkExceedNumber(OpDeskVo opDeskVo, OpDesk lastDesk) {
        Integer maxNumber = opParameterService.selectMaxNumber(opDeskVo.getParameterCode());
        if (maxNumber < opDeskVo.getSampleAmount()) {
            return DeskConstants.DESK_IS_INTERPOLATION;
        }

        AtomicInteger atomicInteger = new AtomicInteger(0);
        if (lastDesk != null) {
            int maxRecordSerialNumber = Integer.parseInt(lastDesk.getRecordSerialNumber()
                    .substring(lastDesk.getRecordSerialNumber().length() - 4));
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
        opDeskVo.setFirstTime(opDeskVo.getEntrustmentTime().with(TemporalAdjusters.firstDayOfMonth()));
        opDeskVo.setLastTime(opDeskVo.getEntrustmentTime().with(TemporalAdjusters.lastDayOfMonth()));
        return opDeskMapper.getLastOpDeskByProjectId(opDeskVo);
    }

    @Override
    public void checkInterpolation(OpDeskVo opDeskVo) {
        if (opDeskVo.getIsInterpolation().equals(DeskConstants.DESK_IS_INTERPOLATION)) {
            return;
        }

        AjaxResult configKey = remoteConfigService.getConfigKey(ConfigConstants.CONFIG_PAST_TIME, SecurityConstants.INNER);
        if (configKey.isError()) {
            throw new GlobalException("配置日期信息错误，请立刻联系管理员！");
        }
        LocalDate past = LocalDate.parse(configKey.get(AjaxResult.MSG_TAG).toString());
        if (!opDeskVo.getEntrustmentTime().isAfter(past)) {
            opDeskVo.setIsInterpolation(DeskConstants.DESK_IS_INTERPOLATION);
            return;
        }

        OpDesk lastDesk = findLast(opDeskVo);
        if (lastDesk != null && lastDesk.getEntrustmentTime().isAfter(opDeskVo.getEntrustmentTime())) {
            opDeskVo.setIsInterpolation(DeskConstants.DESK_IS_INTERPOLATION);
            return;
        }
        opDeskVo.setIsInterpolation(checkExceedNumber(opDeskVo, lastDesk));
    }
}
