package com.jijia.operational.service;

import java.util.List;
import com.jijia.operational.domain.OpFinancialStatement;
import com.jijia.operational.domain.info.OpFinancialStatementInfo;
import com.jijia.operational.domain.vo.OpFinancialStatementVo;

/**
 * 应收账款台账Service接口
 *
 * @author leitianyu
 * @date 2024-01-18
 */
public interface IOpFinancialStatementService
{
    /**
     * 查询应收账款台账
     *
     * @param financialStatementId 应收账款台账主键
     * @return 应收账款台账
     */
    public OpFinancialStatementVo selectOpFinancialStatementByFinancialStatementId(Long financialStatementId);

    /**
     * 查询应收账款台账列表
     *
     * @param opFinancialStatement 应收账款台账
     * @return 应收账款台账集合
     */
    public List<OpFinancialStatementVo> selectOpFinancialStatementList(OpFinancialStatementInfo opFinancialStatement);

    /**
     * 新增应收账款台账
     *
     * @param opFinancialStatement 应收账款台账
     * @return 结果
     */
    public int insertOpFinancialStatement(OpFinancialStatementVo opFinancialStatement);

    /**
     * 修改应收账款台账
     *
     * @param opFinancialStatement 应收账款台账
     * @return 结果
     */
    public int updateOpFinancialStatement(OpFinancialStatementVo opFinancialStatement);

    /**
     * 批量删除应收账款台账
     *
     * @param financialStatementIds 需要删除的应收账款台账主键集合
     * @return 结果
     */
    public int deleteOpFinancialStatementByFinancialStatementIds(Long[] financialStatementIds);

    /**
     * 删除应收账款台账信息
     *
     * @param financialStatementId 应收账款台账主键
     * @return 结果
     */
    public int deleteOpFinancialStatementByFinancialStatementId(Long financialStatementId);

    int updateOpDeskAll(OpFinancialStatementVo opFinancialStatementVo, Long[] financialStatementIds);

    void saveAll(List<OpFinancialStatementVo> opFinancialStatementVos);
}
