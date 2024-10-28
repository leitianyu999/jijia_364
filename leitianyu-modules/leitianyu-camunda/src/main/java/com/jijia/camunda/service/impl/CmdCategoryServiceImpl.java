package com.jijia.camunda.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.jijia.camunda.domain.CmdCategory;
import com.jijia.camunda.domain.vo.CmdCategoryVo;
import com.jijia.camunda.mapper.CmdCategoryMapper;
import com.jijia.camunda.service.ICmdCategoryService;
import com.jijia.common.core.exception.GlobalException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class CmdCategoryServiceImpl implements ICmdCategoryService {

    @Resource
    private CmdCategoryMapper cmdCategoryMapper;

    @Override
    public Long createCategory(CmdCategoryVo createReqVO) {
        // 校验唯一
        validateCategoryNameUnique(createReqVO);
        validateCategoryCodeUnique(createReqVO);
        // 插入
        CmdCategory category = BeanUtil.toBean(createReqVO, CmdCategory.class);
        cmdCategoryMapper.insert(category);
        return category.getCategoryId();
    }

    @Override
    public void updateCategory(CmdCategoryVo updateReqVO) {
        // 校验存在
        validateCategoryExists(updateReqVO.getId());
        validateCategoryNameUnique(updateReqVO);
        validateCategoryCodeUnique(updateReqVO);
        // 更新
        CmdCategory updateObj = BeanUtil.toBean(updateReqVO, CmdCategory.class);
        cmdCategoryMapper.updateById(updateObj);
    }

    @Override
    public void deleteCategory(Long id) {
        // 校验存在
        validateCategoryExists(id);
        // 删除
        cmdCategoryMapper.deleteById(id);
    }

    @Override
    public CmdCategory getCategory(Long id) {
        return cmdCategoryMapper.selectCmdCategoryByCategoryId(id);
    }

    @Override
    public List<CmdCategory> getCategoryPage(CmdCategoryVo pageReqVO) {
        return Collections.emptyList();
    }


    private void validateCategoryNameUnique(CmdCategoryVo updateReqVO) {
        LambdaQueryWrapper<CmdCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CmdCategory::getName, updateReqVO.getName());
        CmdCategory category = cmdCategoryMapper.selectOne(queryWrapper);
        if (category == null) {
            return;
        }

        throw new GlobalException(" ");
    }

    private void validateCategoryCodeUnique(CmdCategoryVo updateReqVO) {
        CmdCategory category = cmdCategoryMapper.selectOne();
        if (category == null
                || ObjUtil.equal(category.getId(), updateReqVO.getId())) {
            return;
        }
    }

    private void validateCategoryExists(Long id) {
        if (cmdCategoryMapper.selectById(id) == null) {
            throw new GlobalException("类别不存在");
        }
    }
}
