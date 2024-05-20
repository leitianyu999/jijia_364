package com.jijia.operational.service.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import com.jijia.common.core.utils.DateUtils;
import com.jijia.common.security.utils.SecurityUtils;
import com.jijia.operational.domain.info.OpFinancialDataInfo;
import com.jijia.operational.domain.info.OpFinancialMsgInfo;
import com.jijia.operational.domain.vo.OpFinancialDataVo;
import com.jijia.operational.domain.vo.OpFinancialMsgVo;
import com.jijia.operational.mapper.OpFinancialDataMapper;
import com.jijia.operational.service.IOpFinancialDataService;
import org.springframework.stereotype.Service;
import com.jijia.operational.mapper.OpFinancialMsgMapper;
import com.jijia.operational.service.IOpFinancialMsgService;

/**
 * 财务台账Service业务层处理
 *
 * @author leitianyu
 * @date 2024-01-17
 */
@Service
public class OpFinancialMsgServiceImpl implements IOpFinancialMsgService
{
    private final OpFinancialMsgMapper opFinancialMsgMapper;
    private final IOpFinancialDataService opFinancialDataService;
    private final OpFinancialDataMapper opFinancialDataMapper;

    public OpFinancialMsgServiceImpl(OpFinancialMsgMapper opFinancialMsgMapper, IOpFinancialDataService opFinancialDataService, OpFinancialDataMapper opFinancialDataMapper) {
        this.opFinancialMsgMapper = opFinancialMsgMapper;
        this.opFinancialDataService = opFinancialDataService;
        this.opFinancialDataMapper = opFinancialDataMapper;
    }

    /**
     * 查询财务台账
     *
     * @param financialId 财务台账主键
     * @return 财务台账
     */
    @Override
    public OpFinancialMsgVo selectOpFinancialMsgByFinancialId(Long financialId)
    {
        return opFinancialMsgMapper.selectOpFinancialMsgByFinancialId(financialId);
    }

    /**
     * 查询财务台账列表
     *
     * @param opFinancialMsg 财务台账
     * @return 财务台账
     */
    @Override
    public List<OpFinancialMsgVo> selectOpFinancialMsgList(OpFinancialMsgInfo opFinancialMsg)
    {
        List<OpFinancialMsgVo> opFinancialMsgVos = opFinancialMsgMapper.selectOpFinancialMsgList(opFinancialMsg);
        if (opFinancialMsgVos != null && !opFinancialMsgVos.isEmpty()) {
            double i = opFinancialMsgMapper.selectOpFinancialMsgListNumber(opFinancialMsg);
            opFinancialMsgVos.get(0).setTotal(i);
        }
        return opFinancialMsgVos;
    }

    /**
     * 新增财务台账
     *
     * @param opFinancialMsg 财务台账
     * @return 结果
     */
    @Override
    public int insertOpFinancialMsg(OpFinancialMsgVo opFinancialMsg)
    {
        if (opFinancialMsg.getCollectionTime() != null) {
            opFinancialMsg.setCreateBy(SecurityUtils.getUsername());
            opFinancialMsg.setCreateTime(DateUtils.getNowDate());
            return opFinancialMsgMapper.insertOpFinancialMsg(opFinancialMsg);
        }
        return 0;
    }

    /**
     * 修改财务台账
     *
     * @param opFinancialMsg 财务台账
     * @return 结果
     */
    @Override
    public int updateOpFinancialMsg(OpFinancialMsgVo opFinancialMsg)
    {
        opFinancialMsg.setUpdateBy(SecurityUtils.getUsername());
        opFinancialMsg.setUpdateTime(DateUtils.getNowDate());
        return opFinancialMsgMapper.updateOpFinancialMsg(opFinancialMsg);
    }

    /**
     * 批量删除财务台账
     *
     * @param financialIds 需要删除的财务台账主键
     * @return 结果
     */
    @Override
    public int deleteOpFinancialMsgByFinancialIds(Long[] financialIds)
    {
        return (int) Arrays.stream(financialIds).map(this::deleteOpFinancialMsgByFinancialId).count();
    }

    /**
     * 删除财务台账信息
     *
     * @param financialId 财务台账主键
     * @return 结果
     */
    @Override
    public int deleteOpFinancialMsgByFinancialId(Long financialId)
    {
        return opFinancialDataService.deleteOpFinancialMsgByFinancialMsgId(financialId) + opFinancialMsgMapper.deleteOpFinancialMsgByFinancialId(financialId);
    }

    @Override
    public int updateOpDeskAll(OpFinancialMsgVo opFinancialMsg, Long[] financialIds) {
        long count = Arrays.stream(financialIds).map(o -> {
            opFinancialMsg.setFinancialId(o);
            return this.updateOpFinancialMsg(opFinancialMsg);
        }).count();
        return (int) count;
    }

    @Override
    public void saveAll(List<OpFinancialMsgVo> opFinancialMsgVos) {
        opFinancialMsgVos.forEach(o -> {
            if (o.getIsImport() != null) {
                insertOpFinancialMsg(o);
            }
        });
    }

    @Override
    public int compute(Long[] financialIds) {
        int a = 0;
        for (Long financialId : financialIds) {
            a += updateOpFinancialMsgCount(financialId);
        }
        return a;
    }

    public int updateOpFinancialMsgCount(Long financialMsgId) {
        OpFinancialDataInfo info = new OpFinancialDataInfo();
        info.setFinancialId(financialMsgId);
        List<OpFinancialDataVo> opFinancialDataVos = opFinancialDataMapper.selectOpFinancialDataList(info);
        if (opFinancialDataVos.isEmpty()) {
            return 0;
        }
        double v = opFinancialDataMapper.selectOpFinancialDataListNumber(info);
        OpFinancialMsgVo opFinancialMsgVo = new OpFinancialMsgVo();
        opFinancialMsgVo.setFinancialId(financialMsgId);
        if (v >= 0) {
            opFinancialMsgVo.setIntoAmount(BigDecimal.valueOf(v));
            opFinancialMsgVo.setOutAmount(BigDecimal.valueOf(0));
        } else {
            opFinancialMsgVo.setIntoAmount(BigDecimal.valueOf(0));
            opFinancialMsgVo.setOutAmount(BigDecimal.valueOf(-v));
        }
        opFinancialMsgVo.setUpdateBy(SecurityUtils.getUsername());
        opFinancialMsgVo.setUpdateTime(DateUtils.getNowDate());
        return opFinancialMsgMapper.updateOpFinancialMsg(opFinancialMsgVo);
    }

}
