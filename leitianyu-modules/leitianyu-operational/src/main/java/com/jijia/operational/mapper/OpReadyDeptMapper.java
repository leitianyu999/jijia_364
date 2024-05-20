package com.jijia.operational.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 预备权限表Mapper接口
 *
 * @author leitianyu
 * @date 2023-08-08
 */
public interface OpReadyDeptMapper
{
    /**
     * 查询分配部门
     *
     * @param readyId 台账id
     * @param permissionLevel 权限
     * @return  结果
     */
    public List<Long> selectOpReadyIdDeptByDeptId(@Param("readyId") Long readyId, @Param("permissionLevel") int permissionLevel);

    /**
     * 查询分配部门
     *
     * @param readyId 台账id
     * @return 结果
     */
    public List<Long> selectOpReadyDeptLongByDeptId(Long readyId);

    /**
     * 新增部门权限
     *
     * @param list 部门列表
     * @param readyId 指定的台账
     * @return 结果
     */
    public int insertOpReadyDept(@Param("list") List<Long> list,@Param("readyId")Long readyId,@Param("permissionLevel") int permissionLevel);

    /**
     * 删除部门权限
     *
     * @param list 部门列表
     * @param readyId 指定的台账
     * @return 结果
     */
    public int deleteOpReadyDeptByDeptId(@Param("list") List<Long> list,@Param("readyId") Long readyId);

    /**
     * 根据readyIdId删除关系
     * @param readyId 台账id
     * @return 行数
     */
    public int deleteByReadyId(Long readyId);
}
