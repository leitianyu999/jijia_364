package com.jijia.camunda.controller;


import com.jijia.camunda.domain.dto.CmdCategoryDto;
import com.jijia.camunda.domain.vo.CmdCategoryVo;
import com.jijia.camunda.service.newS.CmdGroupService;
import com.jijia.common.core.web.controller.BaseController;
import com.jijia.common.core.web.domain.AjaxResult;
import com.jijia.common.core.web.page.TableDataInfo;
import com.jijia.common.security.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : willian fu
 * @date : 2020/9/17
 */
@RestController
@RequestMapping("/groups")
public class CmdGroupsController extends BaseController {

    private final CmdGroupService groupService;

    public CmdGroupsController(CmdGroupService groupService) {
        this.groupService = groupService;
    }

//    /**
//     * 保存表单
//     */
//    @PostMapping("/form")
//    public Object saveForm(@RequestBody FlowEngineDTO flowEngineDTO) throws InvocationTargetException, IllegalAccessException {
//        settingService.jsonToBpmn(flowEngineDTO);
//        return R.ok("保存成功");
//    }

    /**
     * 查询分类列表
     */
    @RequiresPermissions("camunda:groups:list")
    @GetMapping("/list")
    public TableDataInfo list(CmdCategoryDto categoryDto) {
        startPage();
        List<CmdCategoryVo> list = groupService.getGroupList(categoryDto);
        return getDataTable(list);
    }

    /**
     * 获取合同详细信息
     */
    @RequiresPermissions("camunda:groups:query")
    @GetMapping(value = "/{categoryId}")
    public AjaxResult getInfo(@PathVariable("categoryId") Long categoryId) {
        return success(groupService.getGroup(categoryId));
    }

    /**
     * 修改分组
     *
     * @return 修改结果
     */
    @RequiresPermissions("camunda:groups:update")
    @PutMapping("form/group")
    public AjaxResult updateGroup(@RequestBody CmdCategoryDto group) {
        return toAjax(groupService.updateGroup(group));
    }

    /**
     * 新增表单分组
     *
     * @return 添加结果
     */
    @RequiresPermissions("camunda:groups:add")
    @PostMapping("form/group")
    public AjaxResult createGroup(@RequestBody CmdCategoryDto group) {
        return toAjax(groupService.addGroup(group));
    }

    /**
     * 删除分组
     *
     * @return 删除结果
     */
    @RequiresPermissions("camunda:groups:remove")
    @DeleteMapping("/{contractId}")
    public AjaxResult deleteGroup(@PathVariable Long contractId) {
        return toAjax(groupService.deleteGroup(contractId));
    }
}
