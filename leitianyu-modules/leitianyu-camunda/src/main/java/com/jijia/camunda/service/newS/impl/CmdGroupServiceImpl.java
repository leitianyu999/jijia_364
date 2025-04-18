package com.jijia.camunda.service.newS.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jijia.camunda.constants.ConfigConstants;
import com.jijia.camunda.domain.CmdCategory;
import com.jijia.camunda.domain.CmdModel;
import com.jijia.camunda.domain.CmdModelCategory;
import com.jijia.camunda.domain.dto.CmdCategoryDto;
import com.jijia.camunda.domain.vo.CmdCategoryVo;
import com.jijia.camunda.domain.vo.CmdModelVo;
import com.jijia.camunda.domain.vo.TreeSelect;
import com.jijia.camunda.mapper.newM.CmdCategoryMapper;
import com.jijia.camunda.mapper.newM.CmdModelCateGoryMapper;
import com.jijia.camunda.mapper.newM.CmdModelMapper;
import com.jijia.camunda.service.newS.CmdGroupService;
import com.jijia.camunda.service.newS.CmdModelService;
import com.jijia.common.core.constant.SecurityConstants;
import com.jijia.common.core.constant.UserConstants;
import com.jijia.common.core.exception.GlobalException;
import com.jijia.common.core.utils.SpringUtils;
import com.jijia.common.core.utils.StringUtils;
import com.jijia.common.core.web.domain.AjaxResult;
import com.jijia.common.security.utils.SecurityUtils;
import com.jijia.system.api.RemoteConfigService;
import com.jijia.system.api.RemoteMenuService;
import com.jijia.system.api.domain.SysDept;
import com.jijia.system.api.domain.SysMenu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

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
    @Resource
    private CmdModelService cmdModelService;


    @Override
    public CmdCategoryVo getGroup(Long id) {
        CmdCategory cmdCategory = categoryMapper.selectById(id);
        if (cmdCategory == null) {
            throw new GlobalException("分类不存在");
        }
        CmdCategoryVo result = BeanUtil.toBean(cmdCategory, CmdCategoryVo.class);
        CmdModelCategory cmdModelCategory = cmdModelCateGoryMapper.selectOne(new LambdaQueryWrapper<CmdModelCategory>()
                .eq(CmdModelCategory::getCategoryId, cmdCategory.getCategoryId()));
        // 获取模型信息，获取该标识的最后一个运行版本的模型
        if (cmdModelCategory == null) {
            CmdModelVo modelByCode = cmdModelService.getModelByCode(cmdModelCategory.getModelCode());
            if (modelByCode != null) {
                result.setModelCode(modelByCode.getCode());
                result.setModelName(modelByCode.getName());
                result.setCategoryId(cmdCategory.getCategoryId());
            }
        }
        return result;
    }

    @Override
    public List<CmdCategoryVo> getGroupList(CmdCategoryDto categoryDto) {
        List<CmdCategory> categoryList = categoryMapper.selectCmdCategoryList(BeanUtil.toBean(categoryDto, CmdCategory.class));
        List<CmdCategoryVo> categoryVos = BeanUtil.copyToList(categoryList, CmdCategoryVo.class);
        // 获取模型信息
        for (CmdCategoryVo categoryVo : categoryVos) {
            CmdModelCategory cmdModelCategory = cmdModelCateGoryMapper.selectOne(new LambdaQueryWrapper<CmdModelCategory>()
                    .eq(CmdModelCategory::getCategoryId, categoryVo.getCategoryId()));
            // 获取模型信息，获取该标识的最后一个运行版本的模型
            if (cmdModelCategory != null) {
                CmdModelVo modelByCode = cmdModelService.getModelByCode(cmdModelCategory.getModelCode());
                if (modelByCode != null) {
                    categoryVo.setModelCode(modelByCode.getCode());
                    categoryVo.setModelName(modelByCode.getName());
                    categoryVo.setModelId(modelByCode.getModelId());
                }
            }
        }
        return categoryVos;
    }

    @Override
    public int addGroup(CmdCategoryDto categoryDto) {
        validateCategoryCodeUnique(categoryDto);
        validateCategoryNameUnique(categoryDto);
        CmdCategory parenCategory = categoryMapper.selectById(categoryDto.getParentId());
        if (parenCategory.getStatus() == 2) {
            throw new GlobalException("上级分类已停用");
        }

        CmdCategory category = BeanUtil.toBean(categoryDto, CmdCategory.class);

//        Long menuParentId;
//
//        if (category.getParentId() == null) {
//            category.setParentId(0L);
//            AjaxResult configKey = remoteConfigService.getConfigKey(ConfigConstants.CAMUNDA_OA_ID, SecurityConstants.INNER);
//            if (configKey.isError()) {
//                throw new GlobalException("获取配置失败：" + configKey.get(AjaxResult.MSG_TAG));
//            }
//
//            menuParentId = Long.parseLong((String) configKey.get(AjaxResult.MSG_TAG));
//        } else {
//            CmdCategory parentCategory = categoryMapper.selectById(category.getParentId());
//            menuParentId = parentCategory.getMenuId();
//        }
//
//        SysMenu sysMenu = BeanUtil.toBean(categoryDto, SysMenu.class);
//        sysMenu.setParentId(menuParentId);
//        sysMenu.setMenuName(categoryDto.getName());
//        sysMenu.setOrderNum(categoryDto.getSort().intValue());
//        sysMenu.setPath(categoryDto.getCode());
//
//
//        switch (categoryDto.getMenuType()) {
//            case UserConstants.TYPE_MENU:
//                sysMenu.setPerms("camunda.oa:" + categoryDto.getCode() + ":request");
//                AjaxResult addAjaxResult = remoteMenuService.add(sysMenu);
//                if (addAjaxResult.isError()) {
//                    throw new GlobalException("新增菜单失败：" + addAjaxResult.get(AjaxResult.MSG_TAG));
//                }
//                SysMenu result = BeanUtil.toBean(addAjaxResult.get(AjaxResult.DATA_TAG), SysMenu.class);
//                category.setMenuId(result.getMenuId());
////                addF(result, categoryDto.getCode());

//
//                break;
//            case UserConstants.TYPE_DIR:
//                AjaxResult addAjaxResult1 = remoteMenuService.add(sysMenu);
//                if (addAjaxResult1.isError()) {
//                    throw new GlobalException("新增菜单失败：" + addAjaxResult1.get(AjaxResult.MSG_TAG));
//                }
//                SysMenu result1 = BeanUtil.toBean(addAjaxResult1.get(AjaxResult.DATA_TAG), SysMenu.class);
//                category.setMenuId(result1.getMenuId());
//                break;
//            default:
//                throw new GlobalException("菜单类型不正确");
//        }


        category.setCreateTime(new Date());
        category.setCreateBy(SecurityUtils.getUsername());
        category.setAncestors(parenCategory.getAncestors() + "," + category.getParentId());
        int insert = categoryMapper.insert(category);

        // 绑定模型
        if (categoryDto.getModelCode() != null) {
            CmdModelVo cmdModel = cmdModelService.getModelByCode(categoryDto.getModelCode());
            if (cmdModel != null) {
                CmdModelCategory cmdModelCategory = new CmdModelCategory();
                cmdModelCategory.setModelCode(cmdModel.getCode());
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
        int update = categoryMapper.updateById(cmdCategory);
        if (categoryDto.getModelCode() != null) {
            CmdModelCategory cmdModelCategory = cmdModelCateGoryMapper.selectOne(new LambdaQueryWrapper<CmdModelCategory>()
                    .eq(CmdModelCategory::getCategoryId, categoryDto.getCategoryId()));
            if (cmdModelCategory != null && !cmdModelCategory.getModelCode().equals(categoryDto.getModelCode())) {
                // 检查模型
                CmdModel cmdModel = cmdModelMapper.selectOne(new LambdaQueryWrapper<CmdModel>()
                        .eq(CmdModel::getCode, categoryDto.getModelCode())
                        .eq(CmdModel::getStatus, 1));
                if (cmdModel != null) {
                    cmdModelCategory.setModelCode(cmdModel.getCode());
                    int update1 = cmdModelCateGoryMapper.updateById(cmdModelCategory);
                    if (update1 == 0) {
                        throw new GlobalException("更新模型失败");
                    }
                    update += update1;
                } else {
                    throw new GlobalException("模型不存在");
                }
            }
        }
        return update;
    }

    @Override
    public int deleteGroup(Long id) {
        return categoryMapper.deleteById(id);
    }

    @Override
    public List<TreeSelect> selectCategoryTreeList(CmdCategoryDto cmdCategoryDto) {
        List<CmdCategoryVo> groupList = getGroupList(cmdCategoryDto);
        return buildDeptTreeSelect(groupList);
    }


    /**
     * 构建前端所需要树结构
     *
     * @param categoryVoList 分类
     * @return 树结构列表
     */
    public List<CmdCategoryVo> buildDeptTree(List<CmdCategoryVo> categoryVoList)
    {
        List<CmdCategoryVo> returnList = new ArrayList<CmdCategoryVo>();
        List<Long> tempList = categoryVoList.stream().map(CmdCategoryVo::getCategoryId).collect(Collectors.toList());
        for (CmdCategoryVo categoryVo : categoryVoList)
        {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(categoryVo.getParentId()))
            {
                recursionFn(categoryVoList, categoryVo);
                returnList.add(categoryVo);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = categoryVoList;
        }
        return returnList;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<CmdCategoryVo> list, CmdCategoryVo t)
    {
        // 得到子节点列表
        List<CmdCategoryVo> childList = getChildList(list, t);
        t.setChildren(childList);
        for (CmdCategoryVo tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<CmdCategoryVo> getChildList(List<CmdCategoryVo> list, CmdCategoryVo t)
    {
        List<CmdCategoryVo> tlist = new ArrayList<CmdCategoryVo>();
        Iterator<CmdCategoryVo> it = list.iterator();
        while (it.hasNext())
        {
            CmdCategoryVo n = (CmdCategoryVo) it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getCategoryId().longValue())
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<CmdCategoryVo> list, CmdCategoryVo t)
    {
        return !getChildList(list, t).isEmpty();
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param categoryVoList 部门列表
     * @return 下拉树结构列表
     */
    public List<TreeSelect> buildDeptTreeSelect(List<CmdCategoryVo> categoryVoList)
    {
        List<CmdCategoryVo> deptTrees = buildDeptTree(categoryVoList);
        return deptTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
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
