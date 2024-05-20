package com.jijia.operational.mapper;

import com.jijia.operational.domain.OpCalibrationMsg;
import com.jijia.operational.domain.info.OpCalibrationMsgInfo;
import com.jijia.operational.domain.vo.OpCalibrationMsgVo;

import java.util.List;

/**
 * 综合部台账Mapper接口
 *
 * @author leitianyu
 * @date 2023-02-04
 */
public interface OpCalibrationMsgMapper
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
     * 删除综合部台账
     *
     * @param deskId 综合部台账主键
     * @return 结果
     */
    public int deleteOpCalibrationMsgByDeskId(Long deskId);

    /**
     * 批量删除综合部台账
     *
     * @param deskIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOpCalibrationMsgByDeskIds(Long[] deskIds);

    public OpCalibrationMsg selectOne(OpCalibrationMsgVo opCalibrationMsgVo);

    public int statusUpdate(Long id);

    public int statusBack(Long item);

    public void updateStatusToOne(OpCalibrationMsg opCalibrationMsg);


    /**
     * 业务经理查询综合部台账列表
     *
     * @param opCalibrationMsg 综合部台账
     * @return 综合部台账集合
     */
    public List<OpCalibrationMsgVo> selectOpCalibrationMsgListOutPut(OpCalibrationMsgInfo opCalibrationMsg);
}

