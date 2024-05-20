package com.jijia.operational.service.impl;

import java.util.Arrays;
import java.util.List;
import com.jijia.common.core.utils.DateUtils;
import com.jijia.common.security.utils.SecurityUtils;
import com.jijia.operational.domain.info.OpFinancialDataInfo;
import com.jijia.operational.domain.vo.OpFinancialDataVo;
import com.jijia.operational.domain.vo.OpFinancialMsgVo;
import com.jijia.operational.mapper.OpFinancialMsgMapper;
import org.springframework.stereotype.Service;
import com.jijia.operational.mapper.OpFinancialDataMapper;
import com.jijia.operational.service.IOpFinancialDataService;

/**
 * 财务台账明细表Service业务层处理
 *
 * @author leitianyu
 * @date 2024-01-22
 */
@Service
public class OpFinancialDataServiceImpl implements IOpFinancialDataService
{
    private final OpFinancialDataMapper opFinancialDataMapper;
    private final OpFinancialMsgMapper opFinancialMsgMapper;



    public OpFinancialDataServiceImpl(OpFinancialDataMapper opFinancialDataMapper, OpFinancialMsgMapper opFinancialMsgMapper) {
        this.opFinancialDataMapper = opFinancialDataMapper;

        this.opFinancialMsgMapper = opFinancialMsgMapper;
    }

    /**
     * 查询财务台账明细表
     *
     * @param financialCode 财务台账明细表主键
     * @return 财务台账明细表
     */
    @Override
    public OpFinancialDataVo selectOpFinancialDataByFinancialCode(Long financialCode)
    {
        return opFinancialDataMapper.selectOpFinancialDataByFinancialCode(financialCode);
    }

    /**
     * 查询财务台账明细表列表
     *
     * @param opFinancialData 财务台账明细表
     * @return 财务台账明细表
     */
    @Override
    public List<OpFinancialDataVo> selectOpFinancialDataList(OpFinancialDataInfo opFinancialData)
    {
        List<OpFinancialDataVo> opFinancialDataVos = opFinancialDataMapper.selectOpFinancialDataList(opFinancialData);
        if (opFinancialDataVos != null && !opFinancialDataVos.isEmpty()) {
            double i = opFinancialDataMapper.selectOpFinancialDataListNumber(opFinancialData);
            opFinancialDataVos.get(0).setTotal(i);
        }
        return opFinancialDataVos;
    }

    /**
     * 新增财务台账明细表
     *
     * @param opFinancialData 财务台账明细表
     * @return 结果
     */
    @Override
    public int insertOpFinancialData(OpFinancialDataVo opFinancialData)
    {
        opFinancialData.setCreateBy(SecurityUtils.getUsername());
        opFinancialData.setCreateTime(DateUtils.getNowDate());
        OpFinancialMsgVo opFinancialMsgVo = opFinancialMsgMapper.selectOpFinancialMsgByFinancialId(opFinancialData.getFinancialId());
        if (opFinancialData.getReportUnit() == null) {
            opFinancialData.setReportUnit(opFinancialMsgVo.getReportUnit());
        }
        if (opFinancialData.getCompanyMsg() == null) {
            opFinancialData.setCompanyMsg(opFinancialMsgVo.getCompanyMsg());
        }
        if (opFinancialData.getProjectMsg() == null) {
            opFinancialData.setProjectMsg(opFinancialMsgVo.getProjectMsg());
        }
        int i = opFinancialDataMapper.insertOpFinancialData(opFinancialData);
        return i;
    }

    /**
     * 修改财务台账明细表
     *
     * @param opFinancialData 财务台账明细表
     * @return 结果
     */
    @Override
    public int updateOpFinancialData(OpFinancialDataVo opFinancialData)
    {
        opFinancialData.setUpdateBy(SecurityUtils.getUsername());
        opFinancialData.setUpdateTime(DateUtils.getNowDate());
        int i = opFinancialDataMapper.updateOpFinancialData(opFinancialData);
        return i;
    }

    /**
     * 批量删除财务台账明细表
     *
     * @param financialCodes 需要删除的财务台账明细表主键
     * @return 结果
     */
    @Override
    public int deleteOpFinancialDataByFinancialCodes(Long[] financialCodes)
    {
        return opFinancialDataMapper.deleteOpFinancialDataByFinancialCodes(financialCodes);
    }

    /**
     * 删除财务台账明细表信息
     *
     * @param financialCode 财务台账明细表主键
     * @return 结果
     */
    @Override
    public int deleteOpFinancialDataByFinancialCode(Long financialCode)
    {
        OpFinancialDataVo opFinancialDataVo = opFinancialDataMapper.selectOpFinancialDataByFinancialCode(financialCode);
        int i = opFinancialDataMapper.deleteOpFinancialDataByFinancialCode(financialCode);
        return i;
    }

    @Override
    public int deleteOpFinancialMsgByFinancialMsgId(Long financialId) {
        return opFinancialDataMapper.deleteOpFinancialMsgByFinancialMsgId(financialId);
    }

    @Override
    public void saveAll(List<OpFinancialDataVo> opFinancialDataVos, Long financialId) {
        opFinancialDataVos.forEach(o -> {
            if (o.getIsImport() != null) {
                o.setFinancialId(financialId);
                insertOpFinancialData(o);
            }
        });
    }

    @Override
    public int updateOpDeskAll(OpFinancialDataVo opFinancialData, Long[] financialCodes) {
        long count = Arrays.stream(financialCodes).map(o -> {
            opFinancialData.setFinancialCode(o);
            return this.updateOpFinancialData(opFinancialData);
        }).count();
        return (int) count;
    }



}
