package com.jijia.operational.controller;

import java.util.List;
import java.io.IOException;
import java.util.function.Consumer;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.AnalysisEventListener;
import com.jijia.operational.domain.info.OpBusinessInfo;
import com.jijia.operational.domain.info.OpDeskInfo;
import com.jijia.operational.domain.vo.OpBusinessMsgVo;
import com.jijia.operational.domain.vo.OpDeskVo;
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
import com.jijia.operational.domain.OpBusinessMsg;
import com.jijia.operational.service.IOpBusinessMsgService;
import com.jijia.common.core.web.controller.BaseController;
import com.jijia.common.core.web.domain.AjaxResult;
import com.jijia.common.core.utils.poi.ExcelUtil;
import com.jijia.common.core.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 业务部Controller
 *
 * @author leitianyu
 * @date 2024-01-15
 */
@RestController
@RequestMapping("/business")
public class OpBusinessMsgController extends BaseController
{
    @Autowired
    private IOpBusinessMsgService opBusinessMsgService;

    /**
     * 查询业务部列表
     */
    @RequiresPermissions("op:business:list")
    @GetMapping("/list")
    public TableDataInfo list(OpBusinessInfo opBusinessMsg)
    {
        startPage();
        List<OpBusinessMsgVo> list = opBusinessMsgService.selectOpBusinessMsgList(opBusinessMsg);
        return getDataTable(list);
    }



    /**
     * 导入工程类型列表
     */
    @Transactional(rollbackFor = Exception.class)
    @Log(title = "业务部", businessType = BusinessType.IMPORT)
    @RequiresPermissions("op:business:import")
    @PostMapping("/importData")
    public AjaxResult  importData(MultipartFile file) throws Exception {
        AnalysisEventListener<OpBusinessMsgVo> listener = ExcelListenerUtils.getListener(this.batchInsert());
        EasyExcel.read(file.getInputStream(), OpBusinessMsgVo.class, listener).sheet("业务部台账").headRowNumber(2).doRead();
        return AjaxResult.success();
    }

    private Consumer<List<OpBusinessMsgVo>> batchInsert() {
        return opDeskVos -> opBusinessMsgService.saveAll(opDeskVos);
    }

    /**
     * 导出excel模板
     * @param response 请求
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws IOException
    {
        ExcelUtil<OpBusinessMsgVo> util = new ExcelUtil<OpBusinessMsgVo>(OpBusinessMsgVo.class);
        util.hideColumn("filePath");
        util.exportEasyExcel(response, null,"业务部台账");
    }

    /**
     * 导出业务部列表
     */
    @RequiresPermissions("op:business:export")
    @Log(title = "业务部", businessType = BusinessType.EXPORT)
    @PostMapping({"/export/{businessIds}","/export"})
    public void export(HttpServletResponse response, OpBusinessInfo opBusinessMsg, @PathVariable(value = "businessIds",required = false) List<Long> businessIds)
    {
        opBusinessMsg.setBusinessIds(businessIds);
        List<OpBusinessMsgVo> list = opBusinessMsgService.selectOpBusinessMsgList(opBusinessMsg);
        ExcelUtil<OpBusinessMsgVo> util = new ExcelUtil<OpBusinessMsgVo>(OpBusinessMsgVo.class);
        util.hideColumn("filePath");
        util.exportEasyExcel(response, list, "业务部台账");
    }

    /**
     * 获取业务部详细信息
     */
    @RequiresPermissions("op:business:query")
    @GetMapping(value = "/{businessId}")
    public AjaxResult getInfo(@PathVariable("businessId") Long businessId)
    {
        return success(opBusinessMsgService.selectOpBusinessMsgByBusinessId(businessId));
    }

    /**
     * 新增业务部
     */
    @RequiresPermissions("op:business:add")
    @Log(title = "业务部", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OpBusinessMsgVo opBusinessMsg)
    {
        return toAjax(opBusinessMsgService.insertOpBusinessMsg(opBusinessMsg));
    }


    /**
     * 批量修改修改前台台账
     */
    @RequiresPermissions("op:business:editall")
    @Log(title = "业务部", businessType = BusinessType.UPDATE)
    @PostMapping("/edit/{businessIds}")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult editAll(@RequestBody OpBusinessMsgVo opBusinessMsgVo,@PathVariable("businessIds") Long[] businessIds)
    {
        return toAjax(opBusinessMsgService.updateOpDeskAll(opBusinessMsgVo,businessIds));
    }

    /**
     * 修改业务部
     */
    @RequiresPermissions("op:business:edit")
    @Log(title = "业务部", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OpBusinessMsgVo opBusinessMsg)
    {
        return toAjax(opBusinessMsgService.updateOpBusinessMsg(opBusinessMsg));
    }

    /**
     * 删除业务部
     */
    @RequiresPermissions("op:business:remove")
    @Log(title = "业务部", businessType = BusinessType.DELETE)
    @DeleteMapping("/{businessIds}")
    public AjaxResult remove(@PathVariable Long[] businessIds)
    {
        return toAjax(opBusinessMsgService.deleteOpBusinessMsgByBusinessIds(businessIds));
    }
}
