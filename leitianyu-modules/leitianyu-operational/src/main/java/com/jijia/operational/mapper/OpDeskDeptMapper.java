package com.jijia.operational.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  台账权限Mapper接口
 *
 * @author leitianyu
 * @date 2023-07-29
 */
public interface OpDeskDeptMapper
{
    /**
     * 查询分配部门
     *
     * @param deskId 台账id
     * @param permissionLevel 权限
     * @return  结果
     */
    public List<Long> selectOpDeskDeptByDeptId(@Param("deskId") Long deskId,@Param("permissionLevel") int permissionLevel);

    /**
     * 查询分配部门
     *
     * @param deskId 台账id
     * @return 结果
     */
    public List<Long> selectOpDeskDeptLongByDeptId(Long deskId);

    /**
     * 新增部门权限
     *
     * @param list 部门列表
     * @param deskId 指定的台账
     * @return 结果
     */
    public int insertOpDeskDept(@Param("list") List<Long> list,@Param("deskId")Long deskId,@Param("permissionLevel") int permissionLevel);

    /**
     * 删除部门权限
     *
     * @param list 部门列表
     * @param deskId 指定的台账
     * @return 结果
     */
    public int deleteOpDeskDeptByDeptId(@Param("list") List<Long> list,@Param("deskId") Long deskId);

    /**
     * 根据deskId删除关系
     * @param deskId 台账id
     * @return 行数
     */
    public int deleteByDeskId(Long deskId);
}

