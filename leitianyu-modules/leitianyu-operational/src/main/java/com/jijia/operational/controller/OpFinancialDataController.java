package com.jijia.operational.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.AnalysisEventListener;
import com.jijia.operational.domain.info.OpFinancialDataInfo;
import com.jijia.operational.domain.vo.OpFinancialDataVo;
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
import com.jijia.operational.service.IOpFinancialDataService;
import com.jijia.common.core.web.controller.BaseController;
import com.jijia.common.core.web.domain.AjaxResult;
import com.jijia.common.core.utils.poi.ExcelUtil;
import com.jijia.common.core.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 财务台账明细表Controller
 *
 * @author leitianyu
 * @date 2024-01-22
 */
@RestController
@RequestMapping("/financial/data")
public class OpFinancialDataController extends BaseController
{
    @Autowired
    private IOpFinancialDataService opFinancialDataService;

    /**
     * 查询财务台账明细表列表
     */
    @RequiresPermissions("op:financial:list")
    @GetMapping("/list")
    public TableDataInfo list(OpFinancialDataInfo opFinancialData)
    {
        startPage();
        List<OpFinancialDataVo> list = opFinancialDataService.selectOpFinancialDataList(opFinancialData);
        return getDataTable(list);
    }

    /**
     * 导入工程类型列表
     */
    @Transactional(rollbackFor = Exception.class)
    @Log(title = "财务台账明细表", businessType = BusinessType.IMPORT)
    @RequiresPermissions("op:financial:import")
    @PostMapping("/importData/{financialId}")
    public AjaxResult  importData(MultipartFile file ,@PathVariable("financialId") Long financialId) throws Exception {
        AnalysisEventListener<OpFinancialDataVo> listener = ExcelListenerUtils.getListener(this.batchInsert(financialId));
        EasyExcel.read(file.getInputStream(), OpFinancialDataVo.class, listener).sheet("财务台账明细表数据").headRowNumber(2).doRead();
        return AjaxResult.success();
    }

    private Consumer<List<OpFinancialDataVo>> batchInsert(Long financialId) {
        return opFinancialDataVos -> opFinancialDataService.saveAll(opFinancialDataVos, financialId);
    }

    /**
     * 导出excel模板
     * @param response 请求
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws IOException
    {
        ExcelUtil<OpFinancialDataVo> util = new ExcelUtil<OpFinancialDataVo>(OpFinancialDataVo.class);
        util.exportEasyExcel(response, null,"财务台账明细表数据");
    }

    /**
     * 导出财务台账明细表列表
     */
    @RequiresPermissions("op:financial:export")
    @Log(title = "财务台账明细表", businessType = BusinessType.EXPORT)
    @PostMapping({"/export/{financialCodes}", "/export"})
    public void export(HttpServletResponse response, OpFinancialDataInfo opFinancialData, @PathVariable(value = "financialCodes",required = false) List<Long> financialCodes)
    {
        opFinancialData.setFinancialCodes(financialCodes);
        List<OpFinancialDataVo> list = opFinancialDataService.selectOpFinancialDataList(opFinancialData);
        list.forEach(o -> {
            BigDecimal bigDecimal = BigDecimal.ZERO;
            if (o.getAmount() != null && o.getPrice() != null) {
                bigDecimal = o.getAmount().multiply(o.getPrice());
            }
            if (o.getManagementFee() != null) {
                bigDecimal = bigDecimal.subtract(o.getManagementFee());
            }
            if (o.getOtherFee() != null) {
                bigDecimal = bigDecimal.subtract(o.getOtherFee());
            }
            o.setSubtotal(bigDecimal);
        });
        ExcelUtil<OpFinancialDataVo> util = new ExcelUtil<OpFinancialDataVo>(OpFinancialDataVo.class);
        util.exportEasyExcel(response, list, "财务台账明细表数据");
    }

    /**
     * 获取财务台账明细表详细信息
     */
    @RequiresPermissions("op:financial:query")
    @GetMapping(value = "/{financialCode}")
    public AjaxResult getInfo(@PathVariable("financialCode") Long financialCode)
    {
        return success(opFinancialDataService.selectOpFinancialDataByFinancialCode(financialCode));
    }

    /**
     * 新增财务台账明细表
     */
    @Transactional(rollbackFor = Exception.class)
    @RequiresPermissions("op:financial:add")
    @Log(title = "财务台账明细表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OpFinancialDataVo opFinancialData)
    {
        return toAjax(opFinancialDataService.insertOpFinancialData(opFinancialData));
    }


    /**
     * 批量修改修改前台台账
     */
    @RequiresPermissions("op:financial:editall")
    @Log(title = "财务台账明细表", businessType = BusinessType.UPDATE)
    @PostMapping("/edit/{financialCodes}")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult editAll(@RequestBody OpFinancialDataVo opFinancialData,@PathVariable("financialCodes") Long[] financialCodes)
    {
        return toAjax(opFinancialDataService.updateOpDeskAll(opFinancialData,financialCodes));
    }

    /**
     * 修改财务台账明细表
     */
    @Transactional(rollbackFor = Exception.class)
    @RequiresPermissions("op:financial:edit")
    @Log(title = "财务台账明细表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OpFinancialDataVo opFinancialData)
    {
        return toAjax(opFinancialDataService.updateOpFinancialData(opFinancialData));
    }

    /**
     * 删除财务台账明细表
     */
    @Transactional(rollbackFor = Exception.class)
    @RequiresPermissions("op:financial:remove")
    @Log(title = "财务台账明细表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{financialCodes}")
    public AjaxResult remove(@PathVariable Long[] financialCodes)
    {
        return toAjax(opFinancialDataService.deleteOpFinancialDataByFinancialCodes(financialCodes));
    }
}
