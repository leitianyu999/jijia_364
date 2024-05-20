package com.jijia.operational.service;


import java.util.List;

/**
 * 预备权限表Service接口
 *
 * @author leitianyu
 * @date 2023-08-08
 */
public interface IOpReadyDeptService
{

    /**
     * 修改权限
     *
     * @param readyId readyId
     * @param editDept 修改权限
     * @param visitDept 观看权限
     * @return 行数
     */
    public int updateDeptByOp(Long readyId, List<Long> editDept, List<Long> visitDept);

    /**
     * 根据readyId删除关系
     *
     * @param readyId 台账id
     * @return 行数
     */
    public int deleteDeptByReadyId(Long readyId);

}
