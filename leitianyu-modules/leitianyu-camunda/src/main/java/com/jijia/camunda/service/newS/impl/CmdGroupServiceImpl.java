package com.jijia.camunda.service.newS.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jijia.camunda.domain.CmdCategory;
import com.jijia.camunda.domain.dto.CmdCategoryDto;
import com.jijia.camunda.mapper.newM.CmdCategoryMapper;
import com.jijia.camunda.service.newS.CmdGroupService;
import com.jijia.camunda.domain.vo.CmdCategoryVo;
import com.jijia.common.core.exception.GlobalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO
 *
 * @author Raoqian
 * @company ZhiDao Tech
 * @create 2024/11/5
 */
@Service
public class CmdGroupServiceImpl implements CmdGroupService {

    @Autowired
    private CmdCategoryMapper categoryMapper;


    @Override
    public CmdCategoryVo getGroup(Long id) {
        CmdCategory cmdCategory = categoryMapper.selectById(id);
        return BeanUtil.toBean(cmdCategory, CmdCategoryVo.class);
    }

    @Override
    public List<CmdCategoryVo> getGroupList(CmdCategoryDto categoryDto) {
        List<CmdCategory> categoryList = categoryMapper.selectCmdCategoryList(BeanUtil.toBean(categoryDto, CmdCategory.class));
        return BeanUtil.copyToList(categoryList, CmdCategoryVo.class);
    }

    @Override
    public int addGroup(CmdCategoryDto categoryDto) {
        validateCategoryCodeUnique(categoryDto);

        CmdCategory cmdCategory = BeanUtil.toBean(categoryDto, CmdCategory.class);
        return categoryMapper.insert(cmdCategory);
    }

    @Override
    public int updateGroup(CmdCategoryDto categoryDto) {
        validateCategoryCodeUnique(categoryDto);
        CmdCategory cmdCategory = BeanUtil.toBean(categoryDto, CmdCategory.class);
        return categoryMapper.updateById(cmdCategory);
    }

    @Override
    public int deleteGroup(Long id) {
        // 校验模型状态
        checkModelExist(id);

        return categoryMapper.deleteById(id);
    }

    private void checkModelExist(Long id) {
        //
    }


    private void validateCategoryCodeUnique(CmdCategoryDto categoryDto) {
        LambdaQueryWrapper<CmdCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CmdCategory::getCode, categoryDto.getCode());
        CmdCategory cmdCategory = categoryMapper.selectOne(queryWrapper);
        if (cmdCategory != null) {
            throw new GlobalException("分类标志已存在");
        }
    }
}
