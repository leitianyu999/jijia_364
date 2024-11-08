package com.jijia.camunda.controller;

import com.alibaba.nacos.api.model.v2.Result;
import com.jijia.camunda.domain.CmdForm;
import com.jijia.camunda.domain.dto.CmdFormDto;
import com.jijia.camunda.domain.vo.CmdFormVo;
import com.jijia.camunda.listener.ServiceListener;
import com.jijia.camunda.service.newS.CmdFormService;
import com.jijia.common.core.web.controller.BaseController;
import com.jijia.common.core.web.domain.AjaxResult;
import com.jijia.common.core.web.page.TableDataInfo;
import com.jijia.common.log.annotation.Log;
import com.jijia.common.log.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.jijia.common.security.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TODO
 *
 * @author Raoqian
 * @company ZhiDao Tech
 * @create 2024/11/7
 */
@RestController
@RequestMapping("/form")
public class CmdFormController extends BaseController {

    private final CmdFormService formService;
    private final ServiceListener serviceListener;

    public CmdFormController(CmdFormService formService, ServiceListener serviceListener) {
        this.formService = formService;
        this.serviceListener = serviceListener;
    }

    /**
     * 查询BPM 单列表
     */
    @RequiresPermissions("cmd:form:list")
    @GetMapping("/list")
    public TableDataInfo list(CmdFormDto cmdForm)
    {
        startPage();
        List<CmdFormVo> list = formService.getFormList(cmdForm);
        return getDataTable(list);
    }

    /**
     * 获取BPM 单详细信息
     */
    @RequiresPermissions("cmd:form:query")
    @GetMapping(value = "/{formId}")
    public AjaxResult getInfo(@PathVariable("formId") Long formId)
    {
        return success(formService.getForm(formId));
    }

    /**
     * 新增BPM 单
     */
    @RequiresPermissions("cmd:form:add")
    @PostMapping
    public AjaxResult add(@RequestBody CmdFormDto cmdForm)
    {
        return toAjax(formService.addForm(cmdForm));
    }

    /**
     * 修改BPM 单
     */
    @RequiresPermissions("cmd:form:edit")
    @PutMapping
    public AjaxResult edit(@RequestBody CmdFormDto cmdForm)
    {
        return toAjax(formService.updateForm(cmdForm));
    }

    /**
     * 删除BPM 单
     */
    @RequiresPermissions("cmd:form:remove")
    @DeleteMapping("/{formId}")
    public AjaxResult remove(@PathVariable Long formId)
    {
        return toAjax(formService.deleteForm(formId));
    }

}
