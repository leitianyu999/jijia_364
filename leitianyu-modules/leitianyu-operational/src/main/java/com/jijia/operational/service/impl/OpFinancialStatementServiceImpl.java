package com.jijia.operational.service.impl;

import java.util.Arrays;
import java.util.List;
import com.jijia.common.core.utils.DateUtils;
import com.jijia.common.security.utils.SecurityUtils;
import com.jijia.operational.domain.info.OpFinancialStatementInfo;
import com.jijia.operational.domain.vo.OpFinancialStatementVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jijia.operational.mapper.OpFinancialStatementMapper;
import com.jijia.operational.domain.OpFinancialStatement;
import com.jijia.operational.service.IOpFinancialStatementService;

/**
 * 应收账款台账Service业务层处理
 *
 * @author leitianyu
 * @date 2024-01-18
 */
@Service
public class OpFinancialStatementServiceImpl implements IOpFinancialStatementService
{
    @Autowired
    private OpFinancialStatementMapper opFinancialStatementMapper;

    /**
     * 查询应收账款台账
     *
     * @param financialStatementId 应收账款台账主键
     * @return 应收账款台账
     */
    @Override
    public OpFinancialStatementVo selectOpFinancialStatementByFinancialStatementId(Long financialStatementId)
    {
        return opFinancialStatementMapper.selectOpFinancialStatementByFinancialStatementId(financialStatementId);
    }

    /**
     * 查询应收账款台账列表
     *
     * @param opFinancialStatement 应收账款台账
     * @return 应收账款台账
     */
    @Override
    public List<OpFinancialStatementVo> selectOpFinancialStatementList(OpFinancialStatementInfo opFinancialStatement)
    {
        return opFinancialStatementMapper.selectOpFinancialStatementList(opFinancialStatement);
    }

    /**
     * 新增应收账款台账
     *
     * @param opFinancialStatement 应收账款台账
     * @return 结果
     */
    @Override
    public int insertOpFinancialStatement(OpFinancialStatementVo opFinancialStatement)
    {
        if (opFinancialStatement.getReportUnit() != null) {
            opFinancialStatement.setCreateBy(SecurityUtils.getUsername());
            opFinancialStatement.setCreateTime(DateUtils.getNowDate());
            return opFinancialStatementMapper.insertOpFinancialStatement(opFinancialStatement);
        }
        return 0;
    }

    /**
     * 修改应收账款台账
     *
     * @param opFinancialStatement 应收账款台账
     * @return 结果
     */
    @Override
    public int updateOpFinancialStatement(OpFinancialStatementVo opFinancialStatement)
    {
        opFinancialStatement.setUpdateTime(DateUtils.getNowDate());
        return opFinancialStatementMapper.updateOpFinancialStatement(opFinancialStatement);
    }

    /**
     * 批量删除应收账款台账
     *
     * @param financialStatementIds 需要删除的应收账款台账主键
     * @return 结果
     */
    @Override
    public int deleteOpFinancialStatementByFinancialStatementIds(Long[] financialStatementIds)
    {
        return opFinancialStatementMapper.deleteOpFinancialStatementByFinancialStatementIds(financialStatementIds);
    }

    /**
     * 删除应收账款台账信息
     *
     * @param financialStatementId 应收账款台账主键
     * @return 结果
     */
    @Override
    public int deleteOpFinancialStatementByFinancialStatementId(Long financialStatementId)
    {
        return opFinancialStatementMapper.deleteOpFinancialStatementByFinancialStatementId(financialStatementId);
    }

    @Override
    public int updateOpDeskAll(OpFinancialStatementVo opFinancialStatementVo, Long[] financialStatementIds) {
        long count = Arrays.stream(financialStatementIds).map(o -> {
            opFinancialStatementVo.setFinancialStatementId(o);
            return this.updateOpFinancialStatement(opFinancialStatementVo);
        }).count();
        return (int) count;
    }

    @Override
    public void saveAll(List<OpFinancialStatementVo> opFinancialStatementVos) {
        opFinancialStatementVos.forEach(o -> {
            if (o.getIsImport() != null) {
                insertOpFinancialStatement(o);
            }
        });
    }
}
