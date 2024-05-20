package com.jijia.operational.service;

import java.util.List;
import com.jijia.operational.domain.OpContract;
import com.jijia.operational.domain.info.OpContractInfo;
import com.jijia.operational.domain.vo.OpContractVo;

/**
 * 合同Service接口
 *
 * @author leitianyu
 * @date 2024-03-21
 */
public interface IOpContractService
{
    /**
     * 查询合同
     *
     * @param contractId 合同主键
     * @return 合同
     */
    public OpContractVo selectOpContractByContractId(Long contractId);

    /**
     * 查询合同列表
     *
     * @param opContract 合同
     * @return 合同集合
     */
    public List<OpContractVo> selectOpContractList(OpContractInfo opContract);

    /**
     * 新增合同
     *
     * @param opContract 合同
     * @return 结果
     */
    public int insertOpContract(OpContractVo opContract);

    /**
     * 修改合同
     *
     * @param opContract 合同
     * @return 结果
     */
    public int updateOpContract(OpContractVo opContract);

    /**
     * 批量删除合同
     *
     * @param contractIds 需要删除的合同主键集合
     * @return 结果
     */
    public int deleteOpContractByContractIds(Long[] contractIds);

    /**
     * 删除合同信息
     *
     * @param contractId 合同主键
     * @return 结果
     */
    public int deleteOpContractByContractId(Long contractId);

    void saveAll(List<OpContractVo> opFinancialStatementVos);

    int updateOpDeskAll(OpContractVo opContractVo, Long[] contractIds);
}
