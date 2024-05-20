package com.jijia.operational.mapper;

import java.util.List;
import com.jijia.operational.domain.OpFinancialMsg;
import com.jijia.operational.domain.info.OpFinancialDataInfo;
import com.jijia.operational.domain.info.OpFinancialMsgInfo;
import com.jijia.operational.domain.vo.OpFinancialMsgVo;

/**
 * 财务台账Mapper接口
 *
 * @author leitianyu
 * @date 2024-01-17
 */
public interface OpFinancialMsgMapper
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
     * 删除财务台账
     *
     * @param financialId 财务台账主键
     * @return 结果
     */
    public int deleteOpFinancialMsgByFinancialId(Long financialId);


    double selectOpFinancialMsgListNumber(OpFinancialMsgInfo opFinancialMsg);
}
