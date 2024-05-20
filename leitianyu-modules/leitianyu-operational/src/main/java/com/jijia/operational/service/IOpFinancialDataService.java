package com.jijia.operational.service;

import java.util.List;
import com.jijia.operational.domain.OpFinancialData;
import com.jijia.operational.domain.info.OpFinancialDataInfo;
import com.jijia.operational.domain.vo.OpFinancialDataVo;

/**
 * 财务台账明细表Service接口
 *
 * @author leitianyu
 * @date 2024-01-22
 */
public interface IOpFinancialDataService
{
    /**
     * 查询财务台账明细表
     *
     * @param financialCode 财务台账明细表主键
     * @return 财务台账明细表
     */
    public OpFinancialDataVo selectOpFinancialDataByFinancialCode(Long financialCode);

    /**
     * 查询财务台账明细表列表
     *
     * @param opFinancialData 财务台账明细表
     * @return 财务台账明细表集合
     */
    public List<OpFinancialDataVo> selectOpFinancialDataList(OpFinancialDataInfo opFinancialData);

    /**
     * 新增财务台账明细表
     *
     * @param opFinancialData 财务台账明细表
     * @return 结果
     */
    public int insertOpFinancialData(OpFinancialDataVo opFinancialData);

    /**
     * 修改财务台账明细表
     *
     * @param opFinancialData 财务台账明细表
     * @return 结果
     */
    public int updateOpFinancialData(OpFinancialDataVo opFinancialData);

    /**
     * 批量删除财务台账明细表
     *
     * @param financialCodes 需要删除的财务台账明细表主键集合
     * @return 结果
     */
    public int deleteOpFinancialDataByFinancialCodes(Long[] financialCodes);

    /**
     * 删除财务台账明细表信息
     *
     * @param financialCode 财务台账明细表主键
     * @return 结果
     */
    public int deleteOpFinancialDataByFinancialCode(Long financialCode);

    int deleteOpFinancialMsgByFinancialMsgId(Long financialId);

    void saveAll(List<OpFinancialDataVo> opFinancialDataVos, Long financialId);

    int updateOpDeskAll(OpFinancialDataVo opFinancialData, Long[] financialCodes);
}
