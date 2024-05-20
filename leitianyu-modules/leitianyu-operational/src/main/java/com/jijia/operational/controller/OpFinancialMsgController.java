package com.jijia.operational.controller;

import java.util.List;
import java.io.IOException;
import java.util.function.Consumer;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.AnalysisEventListener;
import com.jijia.operational.domain.info.OpFinancialMsgInfo;
import com.jijia.operational.domain.vo.OpBusinessMsgVo;
import com.jijia.operational.domain.vo.OpFinancialMsgVo;
import com.jijia.operational.utils.ExcelListenerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
import com.jijia.operational.domain.OpFinancialMsg;
import com.jijia.operational.service.IOpFinancialMsgService;
import com.jijia.common.core.web.controller.BaseController;
import com.jijia.common.core.web.domain.AjaxResult;
import com.jijia.common.core.utils.poi.ExcelUtil;
import com.jijia.common.core.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 财务台账Controller
 *
 * @author leitianyu
 * @date 2024-01-17
 */
@RestController
@RequestMapping("/financial/msg")
public class OpFinancialMsgController extends BaseController
{
    @Autowired
    private IOpFinancialMsgService opFinancialMsgService;

    /**
     * 查询财务台账列表
     */
    @RequiresPermissions("op:financial:list")
    @GetMapping("/list")
    public TableDataInfo list(OpFinancialMsgInfo opFinancialMsg)
    {
        startPage();
        List<OpFinancialMsgVo> list = opFinancialMsgService.selectOpFinancialMsgList(opFinancialMsg);
        return getDataTable(list);
    }


    /**
     * 导入工程类型列表
     */
    @Transactional(rollbackFor = Exception.class)
    @Log(title = "财务台账", businessType = BusinessType.IMPORT)
    @RequiresPermissions("op:financial:import")
    @PostMapping("/importData")
    public AjaxResult  importData(MultipartFile file) throws Exception {
        AnalysisEventListener<OpFinancialMsgVo> listener = ExcelListenerUtils.getListener(this.batchInsert());
        EasyExcel.read(file.getInputStream(), OpFinancialMsgVo.class, listener).sheet("财务台账数据").headRowNumber(2).doRead();
        return AjaxResult.success();
    }

    private Consumer<List<OpFinancialMsgVo>> batchInsert() {
        return opFinancialMsgVos -> opFinancialMsgService.saveAll(opFinancialMsgVos);
    }

    /**
     * 导出excel模板
     * @param response 请求
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws IOException
    {
        ExcelUtil<OpFinancialMsgVo> util = new ExcelUtil<OpFinancialMsgVo>(OpFinancialMsgVo.class);
        util.exportEasyExcel(response, null,"财务台账数据");
    }

    /**
     * 导出财务台账列表
     */
    @RequiresPermissions("op:financial:export")
    @Log(title = "财务台账", businessType = BusinessType.EXPORT)
    @PostMapping({"/export/{financialIds}","/export"})
    public void export(HttpServletResponse response, OpFinancialMsgInfo opFinancialMsg, @PathVariable(value = "financialIds",required = false) List<Long> financialIds)
    {
        opFinancialMsg.setFinancialIds(financialIds);
        List<OpFinancialMsgVo> list = opFinancialMsgService.selectOpFinancialMsgList(opFinancialMsg);
        ExcelUtil<OpFinancialMsgVo> util = new ExcelUtil<OpFinancialMsgVo>(OpFinancialMsgVo.class);
        util.exportEasyExcel(response, list, "财务台账数据");
    }

    /**
     * 获取财务台账详细信息
     */
    @RequiresPermissions("op:financial:query")
    @GetMapping(value = "/{financialId}")
    public AjaxResult getInfo(@PathVariable("financialId") Long financialId)
    {
        return success(opFinancialMsgService.selectOpFinancialMsgByFinancialId(financialId));
    }

    /**
     * 新增财务台账
     */
    @RequiresPermissions("op:financial:add")
    @Log(title = "财务台账", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OpFinancialMsgVo opFinancialMsg)
    {
        return toAjax(opFinancialMsgService.insertOpFinancialMsg(opFinancialMsg));
    }


    /**
     * 批量修改修改前台台账
     */
    @RequiresPermissions("op:financial:editall")
    @Log(title = "财务台账", businessType = BusinessType.UPDATE)
    @PostMapping("/edit/{financialIds}")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult editAll(@RequestBody OpFinancialMsgVo opFinancialMsg,@PathVariable("financialIds") Long[] financialIds)
    {
        return toAjax(opFinancialMsgService.updateOpDeskAll(opFinancialMsg,financialIds));
    }


    /**
     * 批量修改修改前台台账
     */
    @RequiresPermissions("op:financial:compute")
    @Log(title = "财务台账", businessType = BusinessType.UPDATE)
    @PutMapping("/compute/{financialIds}")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult compute(@PathVariable("financialIds") Long[] financialIds)
    {
        return toAjax(opFinancialMsgService.compute(financialIds));
    }





    /**
     * 修改财务台账
     */
    @RequiresPermissions("op:financial:edit")
    @Log(title = "财务台账", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OpFinancialMsgVo opFinancialMsg)
    {
        return toAjax(opFinancialMsgService.updateOpFinancialMsg(opFinancialMsg));
    }

    /**
     * 删除财务台账
     */
    @RequiresPermissions("op:financial:remove")
    @Log(title = "财务台账", businessType = BusinessType.DELETE)
    @DeleteMapping("/{financialIds}")
    public AjaxResult remove(@PathVariable Long[] financialIds)
    {
        return toAjax(opFinancialMsgService.deleteOpFinancialMsgByFinancialIds(financialIds));
    }
}
