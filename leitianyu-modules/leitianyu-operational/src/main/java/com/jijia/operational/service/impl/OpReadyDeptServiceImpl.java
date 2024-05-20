package com.jijia.operational.service.impl;

import com.jijia.common.core.exception.GlobalException;
import com.jijia.operational.utils.constants.DeptLevel;
import org.springframework.stereotype.Service;
import com.jijia.operational.mapper.OpReadyDeptMapper;
import com.jijia.operational.service.IOpReadyDeptService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 预备权限表Service业务层处理
 *
 * @author leitianyu
 * @date 2023-08-08
 */
@Service
public class OpReadyDeptServiceImpl implements IOpReadyDeptService
{
    private final OpReadyDeptMapper opReadyDeptMapper;


    public OpReadyDeptServiceImpl(OpReadyDeptMapper opReadyDeptMapper) {
        this.opReadyDeptMapper = opReadyDeptMapper;
    }

    /**
     * 修改权限
     * @param readyId readyId
     * @param editDept 修改权限
     * @param visitDept 观看权限
     * @return 行数
     */
    @Override
    public int updateDeptByOp(Long readyId, List<Long> editDept, List<Long> visitDept) {
        //计数器
        AtomicInteger atomicInteger = new AtomicInteger(0);

        //修改检查权限是否为null
        if (visitDept == null || visitDept.size() == 0) {
            return updateDeptByOpWithEditDeptOnly(readyId,editDept);
        }

        //修改权限是否为null
        if (editDept == null || editDept.size() ==0) {
            return updateDeptByOpWithVisitDeptOnly(readyId,visitDept);
        }

        List<Long> checkList = new ArrayList<>(editDept);
        checkList.retainAll(visitDept);
        if (checkList.size() > 0) {
            throw new GlobalException("同一个部门不能出现在两种权限");
        }

        //获取旧权限
        List<Long> editOldList = opReadyDeptMapper.selectOpReadyIdDeptByDeptId(readyId, DeptLevel.EDIT_DEPT);
        List<Long> visitOldList = opReadyDeptMapper.selectOpReadyIdDeptByDeptId(readyId, DeptLevel.VISIT_DEPT);

        if (editOldList != null && editOldList.size() > 0) {
            //找出需要删除的数据
            List<Long> deleteList = new ArrayList<>();
            for (Long item : editOldList) {
                if (!editDept.contains(item)) {
                    deleteList.add(item);
                }
            }
            //找出需要添加的数据
            editDept = editDept.stream().filter(e -> !editOldList.contains(e)).collect(Collectors.toList());
            //删除数据
            if (deleteList.size() > 0) {
                atomicInteger.addAndGet(opReadyDeptMapper.deleteOpReadyDeptByDeptId(deleteList, readyId));
            }
        }
        if (editDept.size() > 0) {
            atomicInteger.addAndGet(opReadyDeptMapper.insertOpReadyDept(editDept, readyId, DeptLevel.EDIT_DEPT));
        }


        if (visitOldList != null && visitOldList.size() > 0) {
            //找出需要删除的数据
            List<Long> deleteList = new ArrayList<>();
            for (Long item : visitOldList) {
                if (!visitDept.contains(item)) {
                    deleteList.add(item);
                }
            }
            //找出需要添加的数据
            visitDept = visitDept.stream().filter(e -> !visitOldList.contains(e)).collect(Collectors.toList());
            //删除数据
            if (deleteList.size() > 0) {
                atomicInteger.addAndGet(opReadyDeptMapper.deleteOpReadyDeptByDeptId(deleteList, readyId));
            }
        }


        if (visitDept.size() > 0) {
            return atomicInteger.addAndGet(opReadyDeptMapper.insertOpReadyDept(visitDept, readyId, DeptLevel.VISIT_DEPT));
        }
        return atomicInteger.get();

    }

    private int updateDeptByOpWithEditDeptOnly(Long readyId,List<Long> editDept) {
        if (editDept == null || editDept.size() == 0) {
            return opReadyDeptMapper.deleteByReadyId(readyId);
        }
        AtomicInteger atomicInteger = new AtomicInteger(0);
        //获取旧权限
        List<Long> editOldList = opReadyDeptMapper.selectOpReadyIdDeptByDeptId(readyId, DeptLevel.EDIT_DEPT);
        List<Long> visitOldList = opReadyDeptMapper.selectOpReadyIdDeptByDeptId(readyId, DeptLevel.VISIT_DEPT);


        //修改权限是否为null
        if (editOldList != null && editOldList.size() != 0) {
            //找出需要删除的数据
            List<Long> deleteList = new ArrayList<>();
            for (Long item : editOldList) {
                if (!editDept.contains(item)) {
                    deleteList.add(item);
                }
            }
            //找出需要添加的数据
            editDept = editDept.stream().filter(e -> !editOldList.contains(e)).collect(Collectors.toList());
            //删除数据
            if (deleteList.size() > 0) {
                atomicInteger.addAndGet(opReadyDeptMapper.deleteOpReadyDeptByDeptId(deleteList, readyId));
            }
        }
        if (visitOldList !=null && visitOldList.size() > 0) {
            atomicInteger.addAndGet(opReadyDeptMapper.deleteOpReadyDeptByDeptId(visitOldList, readyId));
        }
        if (editDept.size() > 0) {
            return atomicInteger.addAndGet(opReadyDeptMapper.insertOpReadyDept(editDept, readyId, DeptLevel.EDIT_DEPT));
        }
        return atomicInteger.get();
    }


    private int updateDeptByOpWithVisitDeptOnly(Long readyId,List<Long> visitDept) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        //获取旧权限
        List<Long> editOldList = opReadyDeptMapper.selectOpReadyIdDeptByDeptId(readyId, DeptLevel.EDIT_DEPT);
        List<Long> visitOldList = opReadyDeptMapper.selectOpReadyIdDeptByDeptId(readyId, DeptLevel.VISIT_DEPT);
        //检查权限是否为null
        if (visitOldList != null && visitOldList.size() != 0) {
            //找出需要删除的数据
            List<Long> deleteList = new ArrayList<>();
            for (Long item : visitOldList) {
                if (!visitDept.contains(item)) {
                    deleteList.add(item);
                }
            }
            //找出需要添加的数据
            visitDept = visitDept.stream().filter(e -> !visitOldList.contains(e)).collect(Collectors.toList());
            //删除数据
            if (deleteList.size() > 0) {
                atomicInteger.addAndGet(opReadyDeptMapper.deleteOpReadyDeptByDeptId(deleteList, readyId));
            }
        }
        if (editOldList !=null && editOldList.size() > 0) {
            atomicInteger.addAndGet(opReadyDeptMapper.deleteOpReadyDeptByDeptId(editOldList, readyId));
        }
        if (visitDept.size() > 0) {
            return atomicInteger.addAndGet(opReadyDeptMapper.insertOpReadyDept(visitDept, readyId, DeptLevel.VISIT_DEPT));
        }
        return atomicInteger.get();
    }


    @Override
    public int deleteDeptByReadyId(Long readyId) {
        return opReadyDeptMapper.deleteByReadyId(readyId);
    }



}
