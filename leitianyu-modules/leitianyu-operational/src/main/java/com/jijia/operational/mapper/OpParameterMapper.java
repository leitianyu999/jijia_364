package com.jijia.operational.mapper;

import com.jijia.operational.domain.OpParameter;
import com.jijia.operational.domain.vo.InfoVo;
import com.jijia.operational.domain.vo.OpDeskVo;

import java.util.List;

/**
 * 参数台账Mapper接口
 *
 * @author leitianyu
 * @date 2023-02-07
 */
public interface OpParameterMapper
{
    /**
     * 查询参数台账
     *
     * @param parameterId 参数台账主键
     * @return 参数台账
     */
    public OpParameter selectOpParameterByParameterId(Long parameterId);

    /**
     * 查询参数台账列表
     *
     * @param opParameter 参数台账
     * @return 参数台账集合
     */
    public List<OpParameter> selectOpParameterList(OpParameter opParameter);

    /**
     * 新增参数台账
     *
     * @param opParameter 参数台账
     * @return 结果
     */
    public int insertOpParameter(OpParameter opParameter);

    /**
     * 修改参数台账
     *
     * @param opParameter 参数台账
     * @return 结果
     */
    public int updateOpParameter(OpParameter opParameter);

    /**
     * 删除参数台账
     *
     * @param parameterId 参数台账主键
     * @return 结果
     */
    public int deleteOpParameterByParameterId(Long parameterId);

    /**
     * 批量删除参数台账
     *
     * @param parameterIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOpParameterByParameterIds(Long[] parameterIds);

    public List<OpParameter> getOpParameterList(InfoVo infoVo);

    public OpParameter getOpParameterByParameterCode(String parameterCode);


    Integer chargeOnlyOne(OpParameter opParameter);

    /**
     * 查询工程代码最大数量
     *
     * @param parameterCode 工程代码
     * @return 最大数量
     */
    Integer selectMaxNumber(String parameterCode);
}

