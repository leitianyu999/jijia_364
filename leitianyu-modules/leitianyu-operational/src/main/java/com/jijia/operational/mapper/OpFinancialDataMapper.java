package com.jijia.operational.mapper;

import java.math.BigDecimal;
import java.util.List;
import com.jijia.operational.domain.OpFinancialData;
import com.jijia.operational.domain.info.OpFinancialDataInfo;
import com.jijia.operational.domain.vo.OpFinancialDataVo;

/**
 * 财务台账明细表Mapper接口
 *
 * @author leitianyu
 * @date 2024-01-22
 */
public interface OpFinancialDataMapper
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
     * 删除财务台账明细表
     *
     * @param financialCode 财务台账明细表主键
     * @return 结果
     */
    public int deleteOpFinancialDataByFinancialCode(Long financialCode);

    /**
     * 批量删除财务台账明细表
     *
     * @param financialCodes 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOpFinancialDataByFinancialCodes(Long[] financialCodes);

    int deleteOpFinancialMsgByFinancialMsgId(Long financialId);

    double selectOpFinancialDataListNumber(OpFinancialDataInfo opFinancialData);
}
