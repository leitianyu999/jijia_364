package com.jijia.operational.service;

import com.jijia.operational.domain.OpCalibrationMsg;
import com.jijia.operational.domain.info.OpCalibrationMsgInfo;
import com.jijia.operational.domain.vo.OpCalibrationMsgVo;

import java.util.List;

/**
 * 综合部台账Service接口
 *
 * @author leitianyu
 * @date 2023-02-04
 */
public interface IOpCalibrationMsgService
{
    /**
     * 查询综合部台账
     *
     * @param deskId 综合部台账主键
     * @return 综合部台账
     */
    public OpCalibrationMsgVo selectOpCalibrationMsgByDeskId(Long deskId);

    /**
     * 查询综合部台账列表
     *
     * @param opCalibrationMsg 综合部台账
     * @return 综合部台账集合
     */
    public List<OpCalibrationMsgVo> selectOpCalibrationMsgList(OpCalibrationMsgInfo opCalibrationMsg);

    /**
     * 新增综合部台账
     *
     * @param opCalibrationMsg 综合部台账
     * @return 结果
     */
    public int insertOpCalibrationMsg(OpCalibrationMsg opCalibrationMsg);

    /**
     * 修改综合部台账
     *
     * @param opCalibrationMsg 综合部台账
     * @return 结果
     */
    public int updateOpCalibrationMsg(OpCalibrationMsg opCalibrationMsg);

    /**
     * 批量删除综合部台账
     *
     * @param deskIds 需要删除的综合部台账主键集合
     * @return 结果
     */
    public int deleteOpCalibrationMsgByDeskIds(Long[] deskIds);

    /**
     * 删除综合部台账信息
     *
     * @param deskId 综合部台账主键
     * @return 结果
     */
    public int deleteOpCalibrationMsgByDeskId(Long deskId);

    public void saveAll(List<OpCalibrationMsgVo> opCalibrationMsgVo);

    public int statusUpdate(Long[] deskIds);

    public int statusBack(Long[] deskIds);

    public int updateOpCalibrationMsg(OpCalibrationMsg opCalibrationMsg, Long[] deskIds);

    public void updateStatusToOne(OpCalibrationMsg opCalibrationMsg);

    /**
     * 业务经理查询台账信息
     * @param opCalibrationMsg 综合部台账查询信息
     * @return
     */
    public List<OpCalibrationMsgVo> selectOpCalibrationMsgListOutPut(OpCalibrationMsgInfo opCalibrationMsg);
}

