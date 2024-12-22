package com.jijia.camunda.controller;

import com.jijia.camunda.domain.CmdModel;
import com.jijia.camunda.domain.dto.CmdModelDto;
import com.jijia.camunda.domain.vo.CmdModelVo;
import com.jijia.camunda.service.newS.CmdModelService;
import com.jijia.common.core.web.controller.BaseController;
import com.jijia.common.core.web.domain.AjaxResult;
import com.jijia.common.core.web.page.TableDataInfo;
import com.jijia.common.log.annotation.Log;
import com.jijia.common.log.enums.BusinessType;
import com.jijia.common.security.annotation.RequiresPermissions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * TODO
 *
 * @author Raoqian
 * @company ZhiDao Tech
 * @create 2024/11/12
 */
@RestController
@RequestMapping("/model")
public class CmdModelController extends BaseController {

    @Resource
    private CmdModelService cmdModelService;

    /**
     * 查询模型列表
     *
     */
    @RequiresPermissions("camunda:model:list")
    @GetMapping("/list")
    public TableDataInfo list(CmdModelDto cmdModel)
    {
        startPage();
        List<CmdModelVo> list = cmdModelService.getModelList(cmdModel);
        return getDataTable(list);
    }

    /**
     * 获取模型详细信息
     */
    @RequiresPermissions("camunda:model:query")
    @GetMapping(value = "/{modelId}")
    public AjaxResult getInfo(@PathVariable("modelId") Long modelId)
    {
        return success(cmdModelService.getModel(modelId));
    }



    /**
     * 新增模型
     */
    @RequiresPermissions("camunda:model:add")
    @Log(title = "新增模型", businessType = BusinessType.INSERT)
    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult add(@RequestBody CmdModelDto cmdModel)
    {
        return toAjax(cmdModelService.addModel(cmdModel));
    }

    /**
     * 修改模型
     */
    @RequiresPermissions("camunda:model:edit")
    @Log(title = "模型修改", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CmdModelDto cmdModel)
    {
        return toAjax(cmdModelService.updateModel(cmdModel));
    }

    /**
     * 删除模型
     */
    @RequiresPermissions("system:model:remove")
    @Log(title = "删除模型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{modelId}")
    public AjaxResult remove(@PathVariable Long modelId)
    {
        return toAjax(cmdModelService.deleteModel(modelId));
    }

    /**
     * 部署模型
     */
    @RequiresPermissions("camunda:model:deploy")
    @Log(title = "部署模型", businessType = BusinessType.OTHER)
    @PutMapping("/deploy/{modelId}")
    public AjaxResult deploy(@PathVariable Long modelId)
    {
        return toAjax(cmdModelService.reployModel(modelId));
    }

}
