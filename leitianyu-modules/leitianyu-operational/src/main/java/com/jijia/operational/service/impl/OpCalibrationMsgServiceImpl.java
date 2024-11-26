package com.jijia.operational.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.jijia.common.core.exception.GlobalException;
import com.jijia.common.core.utils.DateUtils;
import com.jijia.common.security.auth.AuthUtil;
import com.jijia.operational.domain.OpCalibrationMsg;
import com.jijia.operational.domain.info.OpCalibrationMsgInfo;
import com.jijia.operational.domain.vo.OpCalibrationMsgVo;
import com.jijia.operational.mapper.OpCalibrationMsgMapper;
import com.jijia.operational.service.IOpCalibrationMsgService;
import com.jijia.operational.utils.constants.DeskConstants;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * 综合部台账Service业务层处理
 *
 * @author leitianyu
 * @date 2023-02-04
 */
@Service
public class OpCalibrationMsgServiceImpl implements IOpCalibrationMsgService
{
    private final OpCalibrationMsgMapper opCalibrationMsgMapper;

    public OpCalibrationMsgServiceImpl(OpCalibrationMsgMapper opCalibrationMsgMapper) {
        this.opCalibrationMsgMapper = opCalibrationMsgMapper;
    }


    /**
     * 查询综合部台账
     *
     * @param deskId 综合部台账主键
     * @return 综合部台账
     */
    @Override
    public OpCalibrationMsgVo selectOpCalibrationMsgByDeskId(Long deskId)
    {
        return opCalibrationMsgMapper.selectOpCalibrationMsgByDeskId(deskId);
    }


    /**
     * 业务经理查询综合部台账
     *
     * @param opCalibrationMsg 综合部台账查询信息
     */
    @Override
    public List<OpCalibrationMsgVo> selectOpCalibrationMsgListOutPut(OpCalibrationMsgInfo opCalibrationMsg) {

        if (AuthUtil.hasPermi("op:calibration:isSettlement:out")) {
            opCalibrationMsg.setIsSettlement("1");
        } else {
            opCalibrationMsg.setIsSettlement("0");
        }

        return opCalibrationMsgMapper.selectOpCalibrationMsgListOutPut(opCalibrationMsg);
    }

    /**
     * 查询综合部台账列表
     *
     * @param opCalibrationMsg 综合部台账
     * @return 综合部台账
     */
    @Override
    public List<OpCalibrationMsgVo> selectOpCalibrationMsgList(OpCalibrationMsgInfo opCalibrationMsg)
    {

        if (AuthUtil.hasPermi("op:calibration:isSettlement")) {
            opCalibrationMsg.setIsSettlement("1");
        } else {
            opCalibrationMsg.setIsSettlement("0");
        }

        return opCalibrationMsgMapper.selectOpCalibrationMsgList(opCalibrationMsg);
    }

    /**
     * 新增综合部台账
     *
     * @param opCalibrationMsg 综合部台账
     * @return 结果
     */
    @Override
    public int insertOpCalibrationMsg(OpCalibrationMsg opCalibrationMsg)
    {
        opCalibrationMsg.setCreateTime(DateUtils.getNowDate());
        return opCalibrationMsgMapper.insertOpCalibrationMsg(opCalibrationMsg);
    }

    /**
     * 修改综合部台账
     *
     * @param opCalibrationMsg 综合部台账
     * @return 结果
     */
    @Override
    public int updateOpCalibrationMsg(OpCalibrationMsg opCalibrationMsg)
    {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        opCalibrationMsg.setUpdateTime(DateUtils.getNowDate());
        if (opCalibrationMsg.getStatus()!=null) {
            if (opCalibrationMsg.getStatus().equals(2)) {
                atomicInteger.addAndGet(statusUpdate(new Long[]{opCalibrationMsg.getDeskId()}));
            } else if (opCalibrationMsg.getStatus().equals(1)) {
                atomicInteger.addAndGet((statusBack(new Long[]{opCalibrationMsg.getDeskId()})));
            }
        }
        return atomicInteger.addAndGet(opCalibrationMsgMapper.updateOpCalibrationMsg(opCalibrationMsg));
    }

    /**
     * 批量修改台账
     * @param opCalibrationMsg 综合部台账
     * @param deskIds deskId
     */
    @Override
    public int updateOpCalibrationMsg(OpCalibrationMsg opCalibrationMsg, Long[] deskIds) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        Arrays.stream(deskIds).forEach(item -> {
            opCalibrationMsg.setDeskId(item);
            atomicInteger.addAndGet(updateOpCalibrationMsg(opCalibrationMsg));
        });
        return atomicInteger.get();
    }

    /**
     * 批量删除综合部台账
     *
     * @param deskIds 需要删除的综合部台账主键
     * @return 结果
     */
    @Override
    public int deleteOpCalibrationMsgByDeskIds(Long[] deskIds)
    {
        return opCalibrationMsgMapper.deleteOpCalibrationMsgByDeskIds(deskIds);
    }

    /**
     * 删除综合部台账信息
     *
     * @param deskId 综合部台账主键
     * @return 结果
     */
    @Override
    public int deleteOpCalibrationMsgByDeskId(Long deskId)
    {
        return opCalibrationMsgMapper.deleteOpCalibrationMsgByDeskId(deskId);
    }

    @Override
    public void saveAll(List<OpCalibrationMsgVo> opCalibrationMsgVo) {
        opCalibrationMsgVo.forEach(this::save);
    }

    @Override
    public int statusUpdate(Long[] deskIds) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        Arrays.stream(deskIds).forEach(item -> atomicInteger.addAndGet(opCalibrationMsgMapper.statusUpdate(item)));

        return atomicInteger.get();
    }

    @Override
    public int statusBack(Long[] deskIds) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        Arrays.stream(deskIds).forEach(item -> atomicInteger.addAndGet(opCalibrationMsgMapper.statusBack(item)));

        return atomicInteger.get();
    }

    @Override
    public void updateStatusToOne(OpCalibrationMsg opCalibrationMsg) {
        opCalibrationMsgMapper.updateStatusToOne(opCalibrationMsg);
    }

    public void save(OpCalibrationMsgVo opCalibrationMsgVo) {

        if (opCalibrationMsgVo.getReportSerialNumber()!=null&&opCalibrationMsgVo.getRecordSerialNumber()!=null&&opCalibrationMsgVo.getEntrustSerialNumber()!=null) {
            OpCalibrationMsg opCalibrationMsg;

            try {
                if (opCalibrationMsgVo.getRecordSerialNumber().startsWith(DeskConstants.DESK_NUMBER_SL)) {
                    opCalibrationMsgVo.setRecordSerialNumber(opCalibrationMsgVo.getRecordSerialNumber().split("~")[0]);
                } else if (opCalibrationMsgVo.getRecordSerialNumber().startsWith(DeskConstants.DESK_NUMBER_SLJ)){
                    opCalibrationMsgVo.setRecordSerialNumber(opCalibrationMsgVo.getRecordSerialNumber().substring(0,18));
                } else {
                    opCalibrationMsgVo.setRecordSerialNumber(opCalibrationMsgVo.getRecordSerialNumber().substring(0,16));
                }
            } catch (Exception e) {
                throw new GlobalException("该报告编号："+opCalibrationMsgVo.getReportSerialNumber()+"的样品记录编号出现问题");
            }

            try {
                opCalibrationMsg = opCalibrationMsgMapper.selectOne(opCalibrationMsgVo);
            } catch (Exception e) {
                throw new GlobalException("该报告编号："+opCalibrationMsgVo.getReportSerialNumber()+"出现问题，请检查");
            }

            if (opCalibrationMsg!=null) {
                BeanUtils.copyProperties(opCalibrationMsgVo,opCalibrationMsg, "deskId");
                this.updateOpCalibrationMsg(opCalibrationMsg);
            } else {
                throw new GlobalException("该报告编号："+opCalibrationMsgVo.getReportSerialNumber()+"未查询到相关数据");
            }

        }

    }
}

