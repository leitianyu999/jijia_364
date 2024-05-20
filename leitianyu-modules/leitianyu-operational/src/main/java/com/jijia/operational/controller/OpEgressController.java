package com.jijia.operational.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.jijia.operational.domain.OpEgress;
import com.jijia.operational.service.IOpEgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jijia.common.log.annotation.Log;
import com.jijia.common.log.enums.BusinessType;
import com.jijia.common.security.annotation.RequiresPermissions;
import com.jijia.common.core.web.controller.BaseController;
import com.jijia.common.core.web.domain.AjaxResult;
import com.jijia.common.core.utils.poi.ExcelUtil;
import com.jijia.common.core.web.page.TableDataInfo;

/**
 * 外出检测台账Controller
 *
 * @author leitianyu
 * @date 2023-02-12
 */
@RestController
@RequestMapping("/egress")
public class OpEgressController extends BaseController
{
    @Autowired
    private IOpEgressService opEgressService;

    /**
     * 查询外出检测台账列表
     */
    @RequiresPermissions("op:egress:list")
    @GetMapping("/list")
    public TableDataInfo list(OpEgress opEgress)
    {
        startPage();
        List<OpEgress> list = opEgressService.selectOpEgressList(opEgress);
        return getDataTable(list);
    }

    /**
     * 导出外出检测台账列表
     */
    @RequiresPermissions("op:egress:export")
    @Log(title = "外出检测台账", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OpEgress opEgress)
    {
        List<OpEgress> list = opEgressService.selectOpEgressList(opEgress);
        ExcelUtil<OpEgress> util = new ExcelUtil<OpEgress>(OpEgress.class);
        util.exportExcel(response, list, "外出检测台账数据");
    }

    /**
     * 获取外出检测台账详细信息
     */
    @RequiresPermissions("op:egress:query")
    @GetMapping(value = "/{egressId}")
    public AjaxResult getInfo(@PathVariable("egressId") Long egressId)
    {
        return success(opEgressService.selectOpEgressByEgressId(egressId));
    }

    /**
     * 新增外出检测台账
     */
    @RequiresPermissions("op:egress:add")
    @Log(title = "外出检测台账", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OpEgress opEgress)
    {
        return toAjax(opEgressService.insertOpEgress(opEgress));
    }

    /**
     * 修改外出检测台账
     */
    @RequiresPermissions("op:egress:edit")
    @Log(title = "外出检测台账", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OpEgress opEgress)
    {
        return toAjax(opEgressService.updateOpEgress(opEgress));
    }

    /**
     * 删除外出检测台账
     */
    @RequiresPermissions("op:egress:remove")
    @Log(title = "外出检测台账", businessType = BusinessType.DELETE)
    @DeleteMapping("/{egressIds}")
    public AjaxResult remove(@PathVariable Long[] egressIds)
    {
        return toAjax(opEgressService.deleteOpEgressByEgressIds(egressIds));
    }
}

