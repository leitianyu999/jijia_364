package com.jijia.operational.strategy.service.deskInsert.impl;

import com.jijia.common.core.constant.SecurityConstants;
import com.jijia.common.core.exception.GlobalException;
import com.jijia.common.core.web.domain.AjaxResult;
import com.jijia.operational.annotation.DeskInsertTypeAnnotation;
import com.jijia.operational.domain.OpDesk;
import com.jijia.operational.domain.vo.OpDeskVo;
import com.jijia.operational.mapper.OpDeskDeptMapper;
import com.jijia.operational.mapper.OpDeskMapper;
import com.jijia.operational.service.IOpCalibrationMsgService;
import com.jijia.operational.service.IOpDeskDeptService;
import com.jijia.operational.service.IOpDetectMsgService;
import com.jijia.operational.service.IOpReadyDeskService;
import com.jijia.operational.strategy.service.deskInsert.abstractImpl.AbstractDeskInsertStrategy;
import com.jijia.operational.utils.constants.ConfigConstants;
import com.jijia.operational.utils.constants.DeskConstants;
import com.jijia.operational.utils.enums.DeskInsertType;
import com.jijia.system.api.RemoteConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author leitianyu
 */
@Component
@DeskInsertTypeAnnotation(deskInsertType = DeskInsertType.BAO_JIAN)
public class DeskBaojianInsert extends AbstractDeskInsertStrategy {

    @Resource
    private OpDeskMapper opDeskMapper;
    @Resource
    private RemoteConfigService remoteConfigService;
    @Resource
    private IOpReadyDeskService opReadyDeskService;
    @Resource
    private IOpDeskDeptService opDeskDeptService;
    @Resource
    private IOpDetectMsgService opDetectMsgService;
    @Resource
    private IOpCalibrationMsgService opCalibrationMsgService;




    @Override
    public String checkExceedNumber(OpDeskVo opDeskVo, OpDesk lastDesk) {
        throw new GlobalException("请立即联系管理员，报错位置在com.jijia.operational.strategy.service.deskInsert.impl.DeskBaojianInsert.checkExceedNumber");
    }


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

    @Override
    public OpDeskVo creatNumber(OpDeskVo opDeskVo) {
        super.checkTime(remoteConfigService.getConfigKey(ConfigConstants.DESK_BAOJIAN_TIME, SecurityConstants.INNER),opDeskVo.getEntrustmentTime());
        return super.creatNumber(opDeskVo);
    }

    @Override
    public int updateDesk(OpDeskVo opDeskVo, OpDeskVo oldDesk) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        //检查工程类型是否一致
        if (!opDeskVo.getProjectType().equals(oldDesk.getProjectType())) {
            throw new GlobalException(DeskConstants.Desk_BAOJIAN + "的不能修改成其他工程类型");
        }
        //检查是否改动分配部门
        atomicInteger.addAndGet(super.checkDeptId(opDeskVo));
        //检查是否改动检测台账相关内容
        atomicInteger.addAndGet(super.updateDetectMsg(opDeskVo));
        //修改前台台账信息
        atomicInteger.addAndGet(opDeskMapper.updateOpDeskVoByCha(opDeskVo));
        return atomicInteger.get();
    }

    @Override
    public int deleteDesk(Long deskId) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        atomicInteger.addAndGet(opDeskMapper.deleteOpDeskByDeskId(deskId));
        atomicInteger.addAndGet(opCalibrationMsgService.deleteOpCalibrationMsgByDeskId(deskId));
        atomicInteger.addAndGet(opDetectMsgService.deleteOpDetectMsgByDeskId(deskId));
        atomicInteger.addAndGet(opDeskDeptService.deleteDeptByDeskId(deskId));
        return atomicInteger.get();
    }
}
