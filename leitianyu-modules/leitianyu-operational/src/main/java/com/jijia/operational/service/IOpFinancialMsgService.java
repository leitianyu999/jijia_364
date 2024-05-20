package com.jijia.operational.service;

import java.util.List;
import com.jijia.operational.domain.OpFinancialMsg;
import com.jijia.operational.domain.info.OpFinancialMsgInfo;
import com.jijia.operational.domain.vo.OpFinancialMsgVo;

/**
 * 财务台账Service接口
 *
 * @author leitianyu
 * @date 2024-01-17
 */
public interface IOpFinancialMsgService
{
    /**
     * 查询财务台账
     *
     * @param financialId 财务台账主键
     * @return 财务台账
     */
    public OpFinancialMsgVo selectOpFinancialMsgByFinancialId(Long financialId);

    /**
     * 查询财务台账列表
     *
     * @param opFinancialMsg 财务台账
     * @return 财务台账集合
     */
    public List<OpFinancialMsgVo> selectOpFinancialMsgList(OpFinancialMsgInfo opFinancialMsg);

    /**
     * 新增财务台账
     *
     * @param opFinancialMsg 财务台账
     * @return 结果
     */
    public int insertOpFinancialMsg(OpFinancialMsgVo opFinancialMsg);

    /**
     * 修改财务台账
     *
     * @param opFinancialMsg 财务台账
     * @return 结果
     */
    public int updateOpFinancialMsg(OpFinancialMsgVo opFinancialMsg);

    /**
     * 批量删除财务台账
     *
     * @param financialIds 需要删除的财务台账主键集合
     * @return 结果
     */
    public int deleteOpFinancialMsgByFinancialIds(Long[] financialIds);

    /**
     * 删除财务台账信息
     *
     * @param financialId 财务台账主键
     * @return 结果
     */
    public int deleteOpFinancialMsgByFinancialId(Long financialId);

    int updateOpDeskAll(OpFinancialMsgVo opFinancialMsg, Long[] financialIds);

    void saveAll(List<OpFinancialMsgVo> opFinancialMsgVos);

    int compute(Long[] financialIds);
}
