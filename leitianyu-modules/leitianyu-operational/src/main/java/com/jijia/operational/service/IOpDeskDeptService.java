package com.jijia.operational.service;

import com.jijia.operational.domain.info.OpDeskInfo;
import com.jijia.operational.domain.vo.OpDeskVo;
import com.jijia.system.api.domain.OpDeskDept;

import java.util.List;

/**
 * 权限关系Service接口
 *
 * @author leitianyu
 * @date 2023-02-04
 */
public interface IOpDeskDeptService
{

    /**
     * 修改权限
     *
     * @param deskId 台账id
     * @param editDept 修改权限
     * @param visitDept 观看权限
     * @return 行数
     */
    public int updateDeptByOp(Long deskId,List<Long> editDept,List<Long> visitDept);

    /**
     * 根据deskId删除关系
     *
     * @param deskId 台账id
     * @return 行数
     */
    public int deleteDeptByDeskId(Long deskId);
}

