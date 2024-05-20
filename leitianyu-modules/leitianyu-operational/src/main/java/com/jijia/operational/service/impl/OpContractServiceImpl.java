package com.jijia.operational.service.impl;

import java.util.Arrays;
import java.util.List;
import com.jijia.common.core.utils.DateUtils;
import com.jijia.operational.domain.info.OpContractInfo;
import com.jijia.operational.domain.vo.OpContractVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jijia.operational.mapper.OpContractMapper;
import com.jijia.operational.domain.OpContract;
import com.jijia.operational.service.IOpContractService;

/**
 * 合同Service业务层处理
 *
 * @author leitianyu
 * @date 2024-03-21
 */
@Service
public class OpContractServiceImpl implements IOpContractService
{
    @Autowired
    private OpContractMapper opContractMapper;

    /**
     * 查询合同
     *
     * @param contractId 合同主键
     * @return 合同
     */
    @Override
    public OpContractVo selectOpContractByContractId(Long contractId)
    {
        return opContractMapper.selectOpContractByContractId(contractId);
    }

    /**
     * 查询合同列表
     *
     * @param opContract 合同
     * @return 合同
     */
    @Override
    public List<OpContractVo> selectOpContractList(OpContractInfo opContract)
    {
        return opContractMapper.selectOpContractList(opContract);
    }

    /**
     * 新增合同
     *
     * @param opContract 合同
     * @return 结果
     */
    @Override
    public int insertOpContract(OpContractVo opContract)
    {
        opContract.setCreateTime(DateUtils.getNowDate());
        return opContractMapper.insertOpContract(opContract);
    }

    /**
     * 修改合同
     *
     * @param opContract 合同
     * @return 结果
     */
    @Override
    public int updateOpContract(OpContractVo opContract)
    {
        opContract.setUpdateTime(DateUtils.getNowDate());
        return opContractMapper.updateOpContract(opContract);
    }

    /**
     * 批量删除合同
     *
     * @param contractIds 需要删除的合同主键
     * @return 结果
     */
    @Override
    public int deleteOpContractByContractIds(Long[] contractIds)
    {
        return opContractMapper.deleteOpContractByContractIds(contractIds);
    }

    /**
     * 删除合同信息
     *
     * @param contractId 合同主键
     * @return 结果
     */
    @Override
    public int deleteOpContractByContractId(Long contractId)
    {
        return opContractMapper.deleteOpContractByContractId(contractId);
    }

    @Override
    public void saveAll(List<OpContractVo> opFinancialStatementVos) {
        for (OpContractVo opFinancialStatementVo : opFinancialStatementVos) {
            if (opFinancialStatementVo.getIsImport() != null) {
                insertOpContract(opFinancialStatementVo);
            }
        }
    }

    @Override
    public int updateOpDeskAll(OpContractVo opContractVo, Long[] contractIds) {
        long count = Arrays.stream(contractIds).map(o -> {
            opContractVo.setContractId(o);
            return this.updateOpContract(opContractVo);
        }).count();
        return (int) count;
    }
}
