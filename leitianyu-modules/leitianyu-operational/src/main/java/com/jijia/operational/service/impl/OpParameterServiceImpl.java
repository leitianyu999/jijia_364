package com.jijia.operational.service.impl;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import com.jijia.common.core.exception.GlobalException;
import com.jijia.common.core.utils.DateUtils;
import com.jijia.common.core.utils.StringUtils;
import com.jijia.operational.domain.OpParameter;
import com.jijia.operational.domain.vo.InfoVo;
import com.jijia.operational.domain.vo.OpDeskVo;
import com.jijia.operational.mapper.OpParameterMapper;
import com.jijia.operational.service.IOpParameterService;
import org.springframework.stereotype.Service;
/**
 * 参数台账Service业务层处理
 *
 * @author leitianyu
 * @date 2023-02-07
 */
@Service
public class OpParameterServiceImpl implements IOpParameterService
{
    private final OpParameterMapper opParameterMapper;

    public OpParameterServiceImpl(OpParameterMapper opParameterMapper) {
        this.opParameterMapper = opParameterMapper;
    }


    /**
     * 查询参数台账
     *
     * @param parameterId 参数台账主键
     * @return 参数台账
     */
    @Override
    public OpParameter selectOpParameterByParameterId(Long parameterId)
    {
        return opParameterMapper.selectOpParameterByParameterId(parameterId);
    }

    /**
     * 查询参数台账列表
     *
     * @param opParameter 参数台账
     * @return 参数台账
     */
    @Override
    public List<OpParameter> selectOpParameterList(OpParameter opParameter)
    {

        return opParameterMapper.selectOpParameterList(opParameter);
    }

    /**
     * 新增参数台账
     *
     * @param opParameter 参数台账
     * @return 结果
     */
    @Override
    public int insertOpParameter(OpParameter opParameter)
    {
        if (StringUtils.isEmpty(opParameter.getParameterCode())) {
            throw new GlobalException("无参数代码");
        }
        if (opParameter.getMaxNumber() < 1) {
            throw new GlobalException("最大份数错误");
        }
        if (!opParameterMapper.chargeOnlyOne(opParameter).equals(0)) {
            throw new GlobalException("已有相同参数代码");
        }
        opParameter.setCreateTime(DateUtils.getNowDate());
        return opParameterMapper.insertOpParameter(opParameter);
    }

    /**
     * 修改参数台账
     *
     * @param opParameter 参数台账
     * @return 结果
     */
    @Override
    public int updateOpParameter(OpParameter opParameter)
    {
        opParameter.setUpdateTime(DateUtils.getNowDate());
        OpParameter opParameterVo = opParameterMapper.selectOpParameterByParameterId(opParameter.getParameterId());
        if (StringUtils.isEmpty(opParameter.getParameterCode())) {
            throw new GlobalException("无参数代码");
        }
        if (!opParameter.getParameterCode().equals(opParameterVo.getParameterCode()) && !opParameterMapper.chargeOnlyOne(opParameter).equals(0)) {
            throw new GlobalException("已有相同参数代码");
        }
        if (opParameter.getMaxNumber() < 1) {
            throw new GlobalException("最大份数错误");
        }
        return opParameterMapper.updateOpParameter(opParameter);
    }

    /**
     * 批量删除参数台账
     *
     * @param parameterIds 需要删除的参数台账主键
     * @return 结果
     */
    @Override
    public int deleteOpParameterByParameterIds(Long[] parameterIds)
    {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        return atomicInteger.addAndGet(opParameterMapper.deleteOpParameterByParameterIds(parameterIds));
    }

    /**
     * 删除参数台账信息
     *
     * @param parameterId 参数台账主键
     * @return 结果
     */
    @Override
    public int deleteOpParameterByParameterId(Long parameterId)
    {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        return opParameterMapper.deleteOpParameterByParameterId(parameterId);
    }

    @Override
    public List<OpParameter> getOpParameterList(InfoVo infoVo) {
        return opParameterMapper.getOpParameterList(infoVo);
    }

    @Override
    public boolean searchCode(OpDeskVo opDeskVo) {
        OpParameter opParameter = new OpParameter();
        opParameter.setParameterCode(opDeskVo.getParameterCode());
        return opParameterMapper.chargeOnlyOne(opParameter).equals(0);
    }


    /**
     * 查询最大数量
     * @param parameterCode 工程代码
     * @return 最大数量
     */
    @Override
    public Integer selectMaxNumber(String parameterCode) {
        return opParameterMapper.selectMaxNumber(parameterCode);
    }



}

