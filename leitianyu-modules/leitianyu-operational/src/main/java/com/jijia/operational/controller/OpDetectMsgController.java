package com.jijia.operational.controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import javax.servlet.http.HttpServletResponse;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.AnalysisEventListener;
import com.jijia.operational.domain.OpDetectMsg;
import com.jijia.operational.domain.demo.OpDetectMsgVoDemo;
import com.jijia.operational.domain.info.OpDetectMsgInfo;
import com.jijia.operational.domain.vo.OpDetectMsgVo;
import com.jijia.operational.service.IOpDetectMsgService;
import com.jijia.operational.utils.ExcelListenerUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
import org.springframework.web.multipart.MultipartFile;

/**
 * 检测台账Controller
 *
 * @author leitianyu
 * @date 2023-02-04
 */
@RestController
@RequestMapping("/msg")
public class OpDetectMsgController extends BaseController
{
    private final IOpDetectMsgService opDetectMsgService;

    public OpDetectMsgController(IOpDetectMsgService opDetectMsgService) {
        this.opDetectMsgService = opDetectMsgService;
    }

    /**
     * 查询检测台账列表
     */
    @RequiresPermissions("op:msg:list")
    @GetMapping("/list")
    public TableDataInfo list(OpDetectMsgInfo opDetectMsg)
    {
        startPage();
        List<OpDetectMsgVo> list = opDetectMsgService.selectOpDetectMsgList(opDetectMsg, Boolean.TRUE,Boolean.TRUE);
        return getDataTable(list);
    }


    /**
     * 查询检测台账列表(高权限)
     */
    @RequiresPermissions("op:msg:listall")
    @GetMapping("/gm/list")
    public TableDataInfo lists(OpDetectMsgInfo opDetectMsg)
    {
        startPage();
        List<OpDetectMsgVo> list = opDetectMsgService.selectOpDetectMsgList(opDetectMsg, Boolean.FALSE,Boolean.TRUE);
        return getDataTable(list);
    }


    /**
     * 导入工程类型列表
     */
    @Transactional(rollbackFor = Exception.class)
    @Log(title = "检测台账", businessType = BusinessType.IMPORT)
    @RequiresPermissions("op:msg:import")
    @PostMapping("/importData")
    public AjaxResult  importData(MultipartFile file) throws Exception {
        AnalysisEventListener<OpDetectMsgVo> listener = ExcelListenerUtils.getListener(this.batchInsert());
        EasyExcel.read(file.getInputStream(), OpDetectMsgVo.class, listener).sheet("检测台账数据").headRowNumber(2).doRead();
        return AjaxResult.success();
    }

    private Consumer<List<OpDetectMsgVo>> batchInsert() throws Exception {
        return opDetectMsgVos -> opDetectMsgService.saveAll(opDetectMsgVos,Boolean.TRUE);
    }

    /**
     * 导入工程类型列表(高权限)
     */
    @Transactional(rollbackFor = Exception.class)
    @Log(title = "检测台账", businessType = BusinessType.IMPORT)
    @RequiresPermissions("op:msg:importall")
    @PostMapping("/gm/importData")
    public AjaxResult  importDataAll(MultipartFile file) throws Exception {
        AnalysisEventListener<OpDetectMsgVo> listener = ExcelListenerUtils.getListener(this.batchInsertAll());
        EasyExcel.read(file.getInputStream(), OpDetectMsgVo.class, listener).sheet("检测台账数据").headRowNumber(2).doRead();
        return AjaxResult.success();
    }

    private Consumer<List<OpDetectMsgVo>> batchInsertAll() throws Exception {

        return opDetectMsgVos -> opDetectMsgService.saveAll(opDetectMsgVos,Boolean.FALSE);
    }


    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws IOException
    {
        ExcelUtil<OpDetectMsgVoDemo> util = new ExcelUtil<>(OpDetectMsgVoDemo.class);
        util.exportEasyExcel(response, null,"检测台账数据");
    }


    /**
     * 导出检测台账列表
     */
    @RequiresPermissions("op:msg:export")
    @Log(title = "检测台账", businessType = BusinessType.EXPORT)
    @PostMapping(value = {"/export/{deskIds}","/export/"})
    public void export(HttpServletResponse response, OpDetectMsgInfo opDetectMsg, @PathVariable(value = "deskIds",required = false) List<Long> deskIds)
    {
        opDetectMsg.setDeskIds(deskIds);
        List<OpDetectMsgVo> list = opDetectMsgService.selectOpDetectMsgList(opDetectMsg, Boolean.TRUE, Boolean.FALSE);
        AtomicInteger atomicInteger = new AtomicInteger();
        list.forEach(item -> {
            if (item.getSampleAmount()!=1) {
                atomicInteger.set(Integer.parseInt(item.getRecordSerialNumber().substring(item.getRecordSerialNumber().length()-4)));
                atomicInteger.addAndGet(item.getSampleAmount());
                item.setRecordSerialNumber(item.getRecordSerialNumber()+"~"+atomicInteger.decrementAndGet());
            }
        });
        ExcelUtil<OpDetectMsgVo> util = new ExcelUtil<>(OpDetectMsgVo.class);
        util.exportEasyExcel(response, list, "检测台账数据");
    }

    /**
     * 导出检测台账列表(高权限)
     */
    @RequiresPermissions("op:msg:exportall")
    @Log(title = "检测台账", businessType = BusinessType.EXPORT)
    @PostMapping(value = {"/gm/export/{deskIds}","/gm/export/"})
    public void exportAll(HttpServletResponse response, OpDetectMsgInfo opDetectMsg, @PathVariable(value = "deskIds",required = false) List<Long> deskIds)
    {
        opDetectMsg.setDeskIds(deskIds);
        List<OpDetectMsgVo> list = opDetectMsgService.selectOpDetectMsgList(opDetectMsg, Boolean.FALSE, Boolean.FALSE);
        list.forEach(item -> {
            if (item.getSampleAmount()!=1) {
                int i = Integer.parseInt(item.getRecordSerialNumber().substring(item.getRecordSerialNumber().length()-4)) + item.getSampleAmount();
                item.setRecordSerialNumber(item.getRecordSerialNumber()+"~"+i);
            }
        });
        ExcelUtil<OpDetectMsgVo> util = new ExcelUtil<>(OpDetectMsgVo.class);
        util.exportEasyExcel(response, list, "检测台账数据");
    }

    /**
     * 获取检测台账详细信息
     */
    @RequiresPermissions("op:msg:query")
    @GetMapping(value = "/{deskId}")
    public AjaxResult getInfo(@PathVariable("deskId") Long deskId)
    {
        return success(opDetectMsgService.selectOpDetectMsgByDeskId(deskId, Boolean.TRUE));
    }

    /**
     * 获取检测台账详细信息(高权限)
     */
    @RequiresPermissions("op:msg:queryall")
    @GetMapping(value = "/gm/{deskId}")
    public AjaxResult getInfoAll(@PathVariable("deskId") Long deskId)
    {
        return success(opDetectMsgService.selectOpDetectMsgByDeskId(deskId, Boolean.FALSE));
    }

//    /**
//     * 新增检测台账
//     */
//    @RequiresPermissions("op:msg:add")
//    @Log(title = "检测台账", businessType = BusinessType.INSERT)
//    @PostMapping
//    public AjaxResult add(@RequestBody OpDetectMsg opDetectMsg)
//    {
//        return toAjax(opDetectMsgService.insertOpDetectMsg(opDetectMsg));
//    }

    /**
     * 修改检测台账
     */
    @RequiresPermissions("op:msg:edit")
    @Log(title = "检测台账", businessType = BusinessType.UPDATE)
    @PutMapping("/{deskIds}")
    public AjaxResult edit(@Validated @RequestBody OpDetectMsg opDetectMsg, @PathVariable("deskIds") Long[] deskIds)
    {
        return toAjax(opDetectMsgService.updateOpDetectMsg(opDetectMsg,deskIds, Boolean.TRUE));
    }

    /**
     * 修改检测台账(高权限)
     */
    @RequiresPermissions("op:msg:editall")
    @Log(title = "检测台账", businessType = BusinessType.UPDATE)
    @PutMapping("/gm/{deskIds}")
    public AjaxResult editAll(@Validated  @RequestBody OpDetectMsg opDetectMsg,@PathVariable("deskIds") Long[] deskIds)
    {
        return toAjax(opDetectMsgService.updateOpDetectMsg(opDetectMsg,deskIds, Boolean.FALSE));
    }



    /**
     * 批量更改部门权限
     */
    @RequiresPermissions("op:msg:user")
    @Log(title = "检测台账", businessType = BusinessType.GRANT)
    @PutMapping("/dept/{deskIds}/{userId}")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult updateMsgUser(@PathVariable("deskIds") Long[] deskIds , @PathVariable("userId") Long userId) {
        return toAjax(opDetectMsgService.updateMsgUser(deskIds,userId));
    }

    /**
     * 批量更改部门权限(高权限)
     */
    @RequiresPermissions("op:msg:userall")
    @Log(title = "检测台账", businessType = BusinessType.GRANT)
    @PutMapping("/qm/dept/{deskIds}/{userId}")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult updateMsgUserAll(@PathVariable("deskIds") Long[] deskIds , @PathVariable("userId") Long userId) {
        return toAjax(opDetectMsgService.updateMsgUserAll(deskIds,userId));
    }
}

