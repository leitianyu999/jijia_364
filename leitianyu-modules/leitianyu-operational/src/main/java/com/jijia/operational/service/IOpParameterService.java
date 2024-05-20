package com.jijia.operational.service;

import com.jijia.operational.domain.OpParameter;
import com.jijia.operational.domain.vo.InfoVo;
import com.jijia.operational.domain.vo.OpDeskVo;

import java.util.List;


/**
 * 参数台账Service接口
 *
 * @author leitianyu
 * @date 2023-02-07
 */
public interface IOpParameterService
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
     * 批量删除参数台账
     *
     * @param parameterIds 需要删除的参数台账主键集合
     * @return 结果
     */
    public int deleteOpParameterByParameterIds(Long[] parameterIds);

    /**
     * 删除参数台账信息
     *
     * @param parameterId 参数台账主键
     * @return 结果
     */
    public int deleteOpParameterByParameterId(Long parameterId);

    public List<OpParameter> getOpParameterList(InfoVo infoVo);

    public boolean searchCode(OpDeskVo opDeskVo);

    /**
     * 查询最大数量
     *
     * @param parameterCode 工程代码
     * @return 最大数量
     */
    Integer selectMaxNumber(String parameterCode);
}

