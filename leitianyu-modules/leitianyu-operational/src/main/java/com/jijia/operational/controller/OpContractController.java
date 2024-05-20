package com.jijia.operational.controller;

import java.io.InputStream;
import java.util.*;
import java.io.IOException;
import java.util.function.Consumer;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.jijia.operational.domain.info.OpContractInfo;
import com.jijia.operational.domain.vo.OpContractVo;
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
import com.jijia.operational.service.IOpContractService;
import com.jijia.common.core.web.controller.BaseController;
import com.jijia.common.core.web.domain.AjaxResult;
import com.jijia.common.core.utils.poi.ExcelUtil;
import com.jijia.common.core.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 合同Controller
 *
 * @author leitianyu
 * @date 2024-03-21
 */
@RestController
@RequestMapping("/contract")
public class OpContractController extends BaseController
{
    @Autowired
    private IOpContractService opContractService;

    /**
     * 查询合同列表
     */
    @RequiresPermissions("op:contract:list")
    @GetMapping("/list")
    public TableDataInfo list(OpContractInfo opContract)
    {
        startPage();
        List<OpContractVo> list = opContractService.selectOpContractList(opContract);
        return getDataTable(list);
    }


    /**
     * 导入工程类型列表
     */
    @Transactional(rollbackFor = Exception.class)
    @Log(title = "合同台账", businessType = BusinessType.IMPORT)
    @RequiresPermissions("op:contract:import")
    @PostMapping("/importData")
    public AjaxResult  importData(MultipartFile file) throws Exception {
        AnalysisEventListener<OpContractVo> listener = ExcelListenerUtils.getListener(this.batchInsert());
        EasyExcel.read(file.getInputStream(), OpContractVo.class, listener).sheet("合同台账数据").headRowNumber(2).doRead();
        return AjaxResult.success();
    }

    private Consumer<List<OpContractVo>> batchInsert() {
        return opContractVos -> opContractService.saveAll(opContractVos);
    }


    /**
     * 导出excel模板
     * @param response 请求
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws IOException
    {
        ExcelUtil<OpContractVo> util = new ExcelUtil<OpContractVo>(OpContractVo.class);
        util.exportEasyExcel(response, null,"合同台账数据");
    }



    /**
     * 导出合同列表
     */
    @RequiresPermissions("op:contract:export")
    @Log(title = "合同台账", businessType = BusinessType.EXPORT)
    @PostMapping({"/export/{contractIds}","/export"})
    public void export(HttpServletResponse response, OpContractInfo opContract, @PathVariable(value = "contractIds",required = false) List<Long> contractIds)
    {
        opContract.setContractIds(contractIds);
        List<OpContractVo> list = opContractService.selectOpContractList(opContract);
        ExcelUtil<OpContractVo> util = new ExcelUtil<OpContractVo>(OpContractVo.class);
        util.exportEasyExcel(response, list, "合同台账数据");
    }

    /**
     * 获取合同详细信息
     */
    @RequiresPermissions("op:contract:query")
    @GetMapping(value = "/{contractId}")
    public AjaxResult getInfo(@PathVariable("contractId") Long contractId)
    {
        return success(opContractService.selectOpContractByContractId(contractId));
    }

    /**
     * 新增合同
     */
    @RequiresPermissions("op:contract:add")
    @Log(title = "合同台账", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OpContractVo opContract)
    {
        return toAjax(opContractService.insertOpContract(opContract));
    }


    /**
     * 批量修改修改前台台账
     */
    @RequiresPermissions("op:contract:editall")
    @Log(title = "合同台账", businessType = BusinessType.UPDATE)
    @PostMapping("/edit/{contractIds}")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult editAll(@RequestBody OpContractVo opContractVo,@PathVariable("contractIds") Long[] contractIds)
    {

        return toAjax(opContractService.updateOpDeskAll(opContractVo,contractIds));
    }

    /**
     * 修改合同
     */
    @RequiresPermissions("op:contract:edit")
    @Log(title = "合同台账", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OpContractVo opContract)
    {
        return toAjax(opContractService.updateOpContract(opContract));
    }

    /**
     * 删除合同
     */
    @RequiresPermissions("op:contract:remove")
    @Log(title = "合同台账", businessType = BusinessType.DELETE)
    @DeleteMapping("/{contractIds}")
    public AjaxResult remove(@PathVariable Long[] contractIds)
    {
        return toAjax(opContractService.deleteOpContractByContractIds(contractIds));
    }
}
