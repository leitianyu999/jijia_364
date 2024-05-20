package com.jijia.operational.service.impl;

import com.jijia.common.core.exception.GlobalException;
import com.jijia.operational.mapper.OpDeskDeptMapper;
import com.jijia.operational.service.IOpDeskDeptService;
import com.jijia.operational.utils.constants.DeptLevel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 权限关系Service业务层处理
 *
 * @author leitianyu
 */
@Service
public class OpDeskDeptServiceImpl implements IOpDeskDeptService {

    private final OpDeskDeptMapper opDeskDeptMapper;

    public OpDeskDeptServiceImpl(OpDeskDeptMapper opDeskDeptMapper) {
        this.opDeskDeptMapper = opDeskDeptMapper;
    }




    @Override
    public int updateDeptByOp(Long deskId,List<Long> editDept,List<Long> visitDept) {
        //计数器
        AtomicInteger atomicInteger = new AtomicInteger(0);

        //修改检查权限是否为null
        if (visitDept == null || visitDept.size() == 0) {
            return updateDeptByOpWithEditDeptOnly(deskId,editDept);
        }

        //修改权限是否为null
        if (editDept == null || editDept.size() ==0) {
            return updateDeptByOpWithVisitDeptOnly(deskId,visitDept);
        }

        List<Long> checkList = new ArrayList<>(editDept);
        checkList.retainAll(visitDept);
        if (checkList.size() > 0) {
            throw new GlobalException("同一个部门不能出现在两种权限");
        }

        //获取旧权限
        List<Long> editOldList = opDeskDeptMapper.selectOpDeskDeptByDeptId(deskId, DeptLevel.EDIT_DEPT);
        List<Long> visitOldList = opDeskDeptMapper.selectOpDeskDeptByDeptId(deskId, DeptLevel.VISIT_DEPT);

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
                atomicInteger.addAndGet(opDeskDeptMapper.deleteOpDeskDeptByDeptId(deleteList, deskId));
            }
        }
        if (editDept.size() > 0) {
            atomicInteger.addAndGet(opDeskDeptMapper.insertOpDeskDept(editDept, deskId, DeptLevel.EDIT_DEPT));
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
                atomicInteger.addAndGet(opDeskDeptMapper.deleteOpDeskDeptByDeptId(deleteList, deskId));
            }
        }


        if (visitDept.size() > 0) {
            return atomicInteger.addAndGet(opDeskDeptMapper.insertOpDeskDept(visitDept, deskId, DeptLevel.VISIT_DEPT));
        }
        return atomicInteger.get();

    }

    private int updateDeptByOpWithEditDeptOnly(Long deskId,List<Long> editDept) {
        if (editDept == null || editDept.size() == 0) {
            return opDeskDeptMapper.deleteByDeskId(deskId);
        }
        AtomicInteger atomicInteger = new AtomicInteger(0);
        //获取旧权限
        List<Long> editOldList = opDeskDeptMapper.selectOpDeskDeptByDeptId(deskId, DeptLevel.EDIT_DEPT);
        List<Long> visitOldList = opDeskDeptMapper.selectOpDeskDeptByDeptId(deskId, DeptLevel.VISIT_DEPT);


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
                atomicInteger.addAndGet(opDeskDeptMapper.deleteOpDeskDeptByDeptId(deleteList, deskId));
            }
        }
        if (visitOldList !=null && visitOldList.size() > 0) {
            atomicInteger.addAndGet(opDeskDeptMapper.deleteOpDeskDeptByDeptId(visitOldList, deskId));
        }
        if (editDept.size() > 0) {
            return atomicInteger.addAndGet(opDeskDeptMapper.insertOpDeskDept(editDept, deskId, DeptLevel.EDIT_DEPT));
        }
        return atomicInteger.get();
    }


    private int updateDeptByOpWithVisitDeptOnly(Long deskId,List<Long> visitDept) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        //获取旧权限
        List<Long> editOldList = opDeskDeptMapper.selectOpDeskDeptByDeptId(deskId, DeptLevel.EDIT_DEPT);
        List<Long> visitOldList = opDeskDeptMapper.selectOpDeskDeptByDeptId(deskId, DeptLevel.VISIT_DEPT);
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
                atomicInteger.addAndGet(opDeskDeptMapper.deleteOpDeskDeptByDeptId(deleteList, deskId));
            }
        }
        if (editOldList !=null && editOldList.size() > 0) {
            atomicInteger.addAndGet(opDeskDeptMapper.deleteOpDeskDeptByDeptId(editOldList, deskId));
        }
        if (visitDept.size() > 0) {
            return atomicInteger.addAndGet(opDeskDeptMapper.insertOpDeskDept(visitDept, deskId, DeptLevel.VISIT_DEPT));
        }
        return atomicInteger.get();
    }


    @Override
    public int deleteDeptByDeskId(Long deskId) {
        return opDeskDeptMapper.deleteByDeskId(deskId);
    }


}


