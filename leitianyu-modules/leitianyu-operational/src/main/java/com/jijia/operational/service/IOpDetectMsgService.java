package com.jijia.operational.service;

import com.jijia.operational.domain.OpDetectMsg;
import com.jijia.operational.domain.info.OpDetectMsgInfo;
import com.jijia.operational.domain.vo.OpDetectMsgVo;

import java.util.List;

/**
 * 检测台账Service接口
 *
 * @author leitianyu
 * @date 2023-02-04
 */
public interface IOpDetectMsgService
{
    /**
     * 查询检测台账
     *
     * @param deskId 检测台账主键
     * @return 检测台账
     */
    public OpDetectMsg selectOpDetectMsgByDeskId(Long deskId, Boolean bool);

    /**
     * 查询检测台账列表
     *
     * @param opDetectMsg 检测台账
     * @return 检测台账集合
     */
    public List<OpDetectMsgVo> selectOpDetectMsgList(OpDetectMsgInfo opDetectMsg,Boolean bool,Boolean boo);

    /**
     * 新增检测台账
     *
     * @param opDetectMsg 检测台账
     * @return 结果
     */
    public int insertOpDetectMsg(OpDetectMsg opDetectMsg);

    /**
     * 修改检测台账
     *
     * @param opDetectMsg 检测台账
     * @return 结果
     */
    public int updateOpDetectMsg(OpDetectMsg opDetectMsg,Boolean bool);

    /**
     * 批量删除检测台账
     *
     * @param deskIds 需要删除的检测台账主键集合
     * @return 结果
     */
    public int deleteOpDetectMsgByDeskIds(Long[] deskIds);

    /**
     * 删除检测台账信息
     *
     * @param deskId 检测台账主键
     * @return 结果
     */
    public int deleteOpDetectMsgByDeskId(Long deskId);

    public void saveAll(List<OpDetectMsgVo> opDetectMsg,Boolean bool);

    public int updateDeptId(Long[] deskIds, List<Long> editDept, List<Long> visitDept);

    public int updateMsgUser(Long[] deskIds, Long userId);

    public int updateMsgUserAll(Long[] deskIds, Long userId);

    public int updateOpDetectMsg(OpDetectMsg opDetectMsg, Long[] deskIds, Boolean aTrue);

    public int updateOpDetectMsgNote(OpDetectMsg opDetectMsg);
}

