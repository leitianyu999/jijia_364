package com.jijia.operational.controller;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.io.IOException;
import java.util.function.Consumer;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.AnalysisEventListener;
import com.jijia.operational.domain.info.OpFinancialStatementInfo;
import com.jijia.operational.domain.vo.OpFinancialMsgVo;
import com.jijia.operational.domain.vo.OpFinancialStatementVo;
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
import com.jijia.operational.domain.OpFinancialStatement;
import com.jijia.operational.service.IOpFinancialStatementService;
import com.jijia.common.core.web.controller.BaseController;
import com.jijia.common.core.web.domain.AjaxResult;
import com.jijia.common.core.utils.poi.ExcelUtil;
import com.jijia.common.core.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 应收账款台账Controller
 *
 * @author leitianyu
 * @date 2024-01-18
 */
@RestController
@RequestMapping("/statement")
public class OpFinancialStatementController extends BaseController
{
    @Autowired
    private IOpFinancialStatementService opFinancialStatementService;

    /**
     * 查询应收账款台账列表
     */
    @RequiresPermissions("op:statement:list")
    @GetMapping("/list")
    public TableDataInfo list(OpFinancialStatementInfo opFinancialStatement)
    {
        startPage();
        List<OpFinancialStatementVo> list = opFinancialStatementService.selectOpFinancialStatementList(opFinancialStatement);
        return getDataTable(list);
    }



    /**
     * 导入工程类型列表
     */
    @Transactional(rollbackFor = Exception.class)
    @Log(title = "应收账款台账", businessType = BusinessType.IMPORT)
    @RequiresPermissions("op:statement:import")
    @PostMapping("/importData")
    public AjaxResult  importData(MultipartFile file) throws Exception {
        AnalysisEventListener<OpFinancialStatementVo> listener = ExcelListenerUtils.getListener(this.batchInsert());
        EasyExcel.read(file.getInputStream(), OpFinancialStatementVo.class, listener).sheet("应收账款台账数据").headRowNumber(2).doRead();
        return AjaxResult.success();
    }

    private Consumer<List<OpFinancialStatementVo>> batchInsert() {
        return opFinancialStatementVos -> opFinancialStatementService.saveAll(opFinancialStatementVos);
    }


    /**
     * 导出excel模板
     * @param response 请求
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws IOException
    {
        ExcelUtil<OpFinancialStatementVo> util = new ExcelUtil<OpFinancialStatementVo>(OpFinancialStatementVo.class);
        util.exportEasyExcel(response, null,"应收账款台账数据");
    }

    /**
     * 导出应收账款台账列表
     */
    @RequiresPermissions("op:statement:export")
    @Log(title = "应收账款台账", businessType = BusinessType.EXPORT)
    @PostMapping({"/export/{financialStatementIds}","/export"})
    public void export(HttpServletResponse response, OpFinancialStatementInfo opFinancialStatement, @PathVariable(value = "financialStatementIds",required = false) List<Long> financialStatementIds)
    {
        opFinancialStatement.setFinancialStatementIds(financialStatementIds);
        List<OpFinancialStatementVo> list = opFinancialStatementService.selectOpFinancialStatementList(opFinancialStatement);
        list.forEach(o -> {
            if (o.getReceivableTime() != null && o.getArrivalTime() != null && o.getArrivalTime().isAfter(o.getReceivableTime())) {
                o.setOverdueTime((int) o.getArrivalTime().until(o.getReceivableTime(), ChronoUnit.DAYS));
            }
        });
        ExcelUtil<OpFinancialStatementVo> util = new ExcelUtil<OpFinancialStatementVo>(OpFinancialStatementVo.class);
        util.exportEasyExcel(response, list, "应收账款台账数据");
    }

    /**
     * 获取应收账款台账详细信息
     */
    @RequiresPermissions("op:statement:query")
    @GetMapping(value = "/{financialStatementId}")
    public AjaxResult getInfo(@PathVariable("financialStatementId") Long financialStatementId)
    {
        return success(opFinancialStatementService.selectOpFinancialStatementByFinancialStatementId(financialStatementId));
    }

    /**
     * 新增应收账款台账
     */
    @RequiresPermissions("op:statement:add")
    @Log(title = "应收账款台账", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OpFinancialStatementVo opFinancialStatement)
    {
        return toAjax(opFinancialStatementService.insertOpFinancialStatement(opFinancialStatement));
    }

    /**
     * 批量修改修改前台台账
     */
    @RequiresPermissions("op:statement:editall")
    @Log(title = "应收账款台账", businessType = BusinessType.UPDATE)
    @PostMapping("/edit/{financialStatementIds}")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult editAll(@RequestBody OpFinancialStatementVo opFinancialStatementVo,@PathVariable("financialStatementIds") Long[] financialStatementIds)
    {
        return toAjax(opFinancialStatementService.updateOpDeskAll(opFinancialStatementVo,financialStatementIds));
    }

    /**
     * 修改应收账款台账
     */
    @RequiresPermissions("op:statement:edit")
    @Log(title = "应收账款台账", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OpFinancialStatementVo opFinancialStatement)
    {
        return toAjax(opFinancialStatementService.updateOpFinancialStatement(opFinancialStatement));
    }

    /**
     * 删除应收账款台账
     */
    @RequiresPermissions("op:statement:remove")
    @Log(title = "应收账款台账", businessType = BusinessType.DELETE)
    @DeleteMapping("/{financialStatementIds}")
    public AjaxResult remove(@PathVariable Long[] financialStatementIds)
    {
        return toAjax(opFinancialStatementService.deleteOpFinancialStatementByFinancialStatementIds(financialStatementIds));
    }
}
