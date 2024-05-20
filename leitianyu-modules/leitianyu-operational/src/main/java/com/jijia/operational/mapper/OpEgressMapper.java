package com.jijia.operational.mapper;

import com.jijia.operational.domain.OpEgress;

import java.util.List;
/**
 * 外出检测台账Mapper接口
 *
 * @author leitianyu
 * @date 2023-02-12
 */
public interface OpEgressMapper
{
    /**
     * 查询外出检测台账
     *
     * @param egressId 外出检测台账主键
     * @return 外出检测台账
     */
    public OpEgress selectOpEgressByEgressId(Long egressId);

    /**
     * 查询外出检测台账列表
     *
     * @param opEgress 外出检测台账
     * @return 外出检测台账集合
     */
    public List<OpEgress> selectOpEgressList(OpEgress opEgress);

    /**
     * 新增外出检测台账
     *
     * @param opEgress 外出检测台账
     * @return 结果
     */
    public int insertOpEgress(OpEgress opEgress);

    /**
     * 修改外出检测台账
     *
     * @param opEgress 外出检测台账
     * @return 结果
     */
    public int updateOpEgress(OpEgress opEgress);

    /**
     * 删除外出检测台账
     *
     * @param egressId 外出检测台账主键
     * @return 结果
     */
    public int deleteOpEgressByEgressId(Long egressId);

    /**
     * 批量删除外出检测台账
     *
     * @param egressIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOpEgressByEgressIds(Long[] egressIds);
}

