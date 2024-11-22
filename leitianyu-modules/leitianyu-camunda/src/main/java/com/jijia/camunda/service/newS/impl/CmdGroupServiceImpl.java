package com.jijia.camunda.service.newS.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jijia.camunda.constants.ConfigConstants;
import com.jijia.camunda.domain.CmdCategory;
import com.jijia.camunda.domain.CmdModel;
import com.jijia.camunda.domain.CmdModelCategory;
import com.jijia.camunda.domain.dto.CmdCategoryDto;
import com.jijia.camunda.domain.vo.CmdCategoryVo;
import com.jijia.camunda.mapper.newM.CmdCategoryMapper;
import com.jijia.camunda.mapper.newM.CmdModelCateGoryMapper;
import com.jijia.camunda.mapper.newM.CmdModelMapper;
import com.jijia.camunda.service.newS.CmdGroupService;
import com.jijia.common.core.constant.SecurityConstants;
import com.jijia.common.core.constant.UserConstants;
import com.jijia.common.core.exception.GlobalException;
import com.jijia.common.core.web.domain.AjaxResult;
import com.jijia.common.security.utils.SecurityUtils;
import com.jijia.system.api.RemoteConfigService;
import com.jijia.system.api.RemoteMenuService;
import com.jijia.system.api.domain.SysMenu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
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

    @Resource
    private CmdCategoryMapper categoryMapper;
    @Resource
    private RemoteConfigService remoteConfigService;
    @Resource
    private RemoteMenuService remoteMenuService;
    @Resource
    private CmdModelMapper cmdModelMapper;
    @Resource
    private CmdModelCateGoryMapper cmdModelCateGoryMapper;


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
        validateCategoryNameUnique(categoryDto);

        CmdCategory category = BeanUtil.toBean(categoryDto, CmdCategory.class);

        Long menuParentId;

        if (category.getParentId() == null) {
            category.setParentId(0L);
            AjaxResult configKey = remoteConfigService.getConfigKey(ConfigConstants.CAMUNDA_OA_ID, SecurityConstants.INNER);
            if (configKey.isError()) {
                throw new GlobalException("获取配置失败：" + configKey.get(AjaxResult.MSG_TAG));
            }

            menuParentId = Long.parseLong((String) configKey.get(AjaxResult.MSG_TAG));
        } else {
            CmdCategory parentCategory = categoryMapper.selectById(category.getParentId());
            menuParentId = parentCategory.getMenuId();
        }

        SysMenu sysMenu = BeanUtil.toBean(categoryDto, SysMenu.class);
        sysMenu.setParentId(menuParentId);
        sysMenu.setMenuName(categoryDto.getName());
        sysMenu.setOrderNum(categoryDto.getSort().intValue());
        sysMenu.setPath(categoryDto.getCode());


        switch (categoryDto.getMenuType()) {
            case UserConstants.TYPE_MENU:
                sysMenu.setPerms("camunda.oa:" + categoryDto.getCode() + ":request");
                AjaxResult addAjaxResult = remoteMenuService.add(sysMenu);
                if (addAjaxResult.isError()) {
                    throw new GlobalException("新增菜单失败：" + addAjaxResult.get(AjaxResult.MSG_TAG));
                }
                SysMenu result = BeanUtil.toBean(addAjaxResult.get(AjaxResult.DATA_TAG), SysMenu.class);
                category.setMenuId(result.getMenuId());
//                addF(result, categoryDto.getCode());

                break;
            case UserConstants.TYPE_DIR:
                AjaxResult addAjaxResult1 = remoteMenuService.add(sysMenu);
                if (addAjaxResult1.isError()) {
                    throw new GlobalException("新增菜单失败：" + addAjaxResult1.get(AjaxResult.MSG_TAG));
                }
                SysMenu result1 = BeanUtil.toBean(addAjaxResult1.get(AjaxResult.DATA_TAG), SysMenu.class);
                category.setMenuId(result1.getMenuId());
                break;
            default:
                throw new GlobalException("菜单类型不正确");
        }


        category.setCreateTime(new Date());
        category.setCreateBy(SecurityUtils.getUsername());

        int insert = categoryMapper.insert(category);

        // 绑定模型
        if (categoryDto.getModelId() != null) {
            CmdModel cmdModel = cmdModelMapper.selectById(categoryDto.getModelId());
            if (cmdModel != null) {
                CmdModelCategory cmdModelCategory = new CmdModelCategory();
                cmdModelCategory.setModelId(cmdModel.getModelId());
                cmdModelCategory.setCategoryId(category.getCategoryId());
                int insertModelCateGory = cmdModelCateGoryMapper.insert(cmdModelCategory);
                if (insertModelCateGory == 0) {
                    throw new GlobalException("绑定模型失败");
                }
                insert += insertModelCateGory;
            }
        }

        return insert;
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

    private void validateCategoryNameUnique(CmdCategoryDto categoryDto) {
        LambdaQueryWrapper<CmdCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CmdCategory::getName, categoryDto.getName());
        CmdCategory cmdCategory = categoryMapper.selectOne(queryWrapper);
        if (cmdCategory != null) {
            throw new GlobalException("分类名称已存在");
        }
    }

    private void addF(SysMenu sysMenu, String code) {
        SysMenu sysMenuAdd = new SysMenu();
        sysMenuAdd.setParentId(sysMenu.getMenuId());
        sysMenuAdd.setMenuName("查询" + sysMenu.getMenuName());
        sysMenuAdd.setOrderNum(1);
        sysMenuAdd.setIsCache(sysMenu.getIsCache());
        sysMenuAdd.setIsFrame(sysMenu.getIsFrame());
        sysMenuAdd.setMenuType(UserConstants.TYPE_BUTTON);
        sysMenuAdd.setPerms("camunda.oa:" + code + ":query");
        sysMenuAdd.setStatus("0");
        sysMenuAdd.setVisible("0");

        AjaxResult addAjaxResult = remoteMenuService.add(sysMenuAdd);
        if (addAjaxResult.isError()) {
            throw new GlobalException("新增菜单失败");
        }
    }
}
