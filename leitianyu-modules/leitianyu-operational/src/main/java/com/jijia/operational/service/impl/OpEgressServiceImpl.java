package com.jijia.operational.service.impl;

import java.util.List;
import com.jijia.common.core.utils.DateUtils;
import com.jijia.common.security.utils.SecurityUtils;
import com.jijia.operational.domain.OpEgress;
import com.jijia.operational.mapper.OpEgressMapper;
import com.jijia.operational.service.IOpEgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 外出检测台账Service业务层处理
 *
 * @author leitianyu
 * @date 2023-02-12
 */
@Service
public class OpEgressServiceImpl implements IOpEgressService
{
    private final OpEgressMapper opEgressMapper;

    public OpEgressServiceImpl(OpEgressMapper opEgressMapper) {
        this.opEgressMapper = opEgressMapper;
    }

    /**
     * 查询外出检测台账
     *
     * @param egressId 外出检测台账主键
     * @return 外出检测台账
     */
    @Override
    public OpEgress selectOpEgressByEgressId(Long egressId)
    {
        return opEgressMapper.selectOpEgressByEgressId(egressId);
    }

    /**
     * 查询外出检测台账列表
     *
     * @param opEgress 外出检测台账
     * @return 外出检测台账
     */
    @Override
    public List<OpEgress> selectOpEgressList(OpEgress opEgress)
    {
        return opEgressMapper.selectOpEgressList(opEgress);
    }

    /**
     * 新增外出检测台账
     *
     * @param opEgress 外出检测台账
     * @return 结果
     */
    @Override
    public int insertOpEgress(OpEgress opEgress)
    {
        opEgress.setCreateTime(DateUtils.getNowDate());
        opEgress.setCreateBy(SecurityUtils.getUsername());
        return opEgressMapper.insertOpEgress(opEgress);
    }

    /**
     * 修改外出检测台账
     *
     * @param opEgress 外出检测台账
     * @return 结果
     */
    @Override
    public int updateOpEgress(OpEgress opEgress)
    {
        opEgress.setUpdateTime(DateUtils.getNowDate());
        return opEgressMapper.updateOpEgress(opEgress);
    }

    /**
     * 批量删除外出检测台账
     *
     * @param egressIds 需要删除的外出检测台账主键
     * @return 结果
     */
    @Override
    public int deleteOpEgressByEgressIds(Long[] egressIds)
    {
        return opEgressMapper.deleteOpEgressByEgressIds(egressIds);
    }

    /**
     * 删除外出检测台账信息
     *
     * @param egressId 外出检测台账主键
     * @return 结果
     */
    @Override
    public int deleteOpEgressByEgressId(Long egressId)
    {
        return opEgressMapper.deleteOpEgressByEgressId(egressId);
    }
}

