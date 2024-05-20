package com.jijia.operational.controller;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import javax.servlet.http.HttpServletResponse;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.AnalysisEventListener;
import com.jijia.common.core.utils.StringUtils;
import com.jijia.common.security.utils.SecurityUtils;
import com.jijia.operational.domain.OpCalibrationMsg;
import com.jijia.operational.domain.info.OpCalibrationMsgInfo;
import com.jijia.operational.domain.vo.OpCalibrationMsgVo;
import com.jijia.operational.service.IOpCalibrationMsgService;
import com.jijia.operational.utils.ExcelListenerUtils;
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
import com.jijia.common.core.web.controller.BaseController;
import com.jijia.common.core.web.domain.AjaxResult;
import com.jijia.common.core.utils.poi.ExcelUtil;
import com.jijia.common.core.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 综合部台账Controller
 *
 * @author leitianyu
 * @date 2023-02-04
 */
@RestController
@RequestMapping("/calibration")
public class OpCalibrationMsgController extends BaseController
{
    private final IOpCalibrationMsgService opCalibrationMsgService;

    public OpCalibrationMsgController(IOpCalibrationMsgService opCalibrationMsgService) {
        this.opCalibrationMsgService = opCalibrationMsgService;
    }

    /**
     * 查询综合部台账列表
     */
    @RequiresPermissions("op:calibration:list")
    @GetMapping("/list")
    public TableDataInfo list(OpCalibrationMsgInfo opCalibrationMsg)
    {
        startPage();
        List<OpCalibrationMsgVo> list = opCalibrationMsgService.selectOpCalibrationMsgList(opCalibrationMsg);
        return getDataTable(list);
    }

    /**
     * 查询综合部台账列表
     */
    @RequiresPermissions("op:calibration:output")
    @GetMapping("/out/list")
    public TableDataInfo outList(OpCalibrationMsgInfo opCalibrationMsg)
    {
        startPage();
        opCalibrationMsg.setBusinessManager(SecurityUtils.getUsername());
        List<OpCalibrationMsgVo> list = opCalibrationMsgService.selectOpCalibrationMsgListOutPut(opCalibrationMsg);
        return getDataTable(list);
    }

    /**
     * 导入工程类型列表
     */
    @Transactional(rollbackFor = Exception.class)
    @Log(title = "综合部台账", businessType = BusinessType.IMPORT)
    @RequiresPermissions("op:calibration:import")
    @PostMapping("/importData")
    public AjaxResult  importData(MultipartFile file, boolean updateSupport) throws Exception {
        AnalysisEventListener<OpCalibrationMsgVo> listener = ExcelListenerUtils.getListener(this.batchInsert());
        EasyExcel.read(file.getInputStream(), OpCalibrationMsgVo.class, listener).sheet("综合部台账数据").headRowNumber(2).doRead();
        return AjaxResult.success();
    }

    private Consumer<List<OpCalibrationMsgVo>> batchInsert() {
        return opCalibrationMsgService::saveAll;
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws IOException
    {
        ExcelUtil<OpCalibrationMsgVo> util = new ExcelUtil<>(OpCalibrationMsgVo.class);
        util.exportEasyExcel(response,null , "综合部台账数据");
    }

    /**
     * 导出综合部台账列表
     */
    @RequiresPermissions("op:calibration:export")
    @Log(title = "综合部台账", businessType = BusinessType.EXPORT)
    @PostMapping(value = {"/export/{deskIds}","/export/"})
    public void export(HttpServletResponse response, OpCalibrationMsgInfo opCalibrationMsg, @PathVariable(value = "deskIds",required = false) List<Long> deskIds)
    {
        //选中导出相关数据
        opCalibrationMsg.setDeskIds(deskIds);

        List<OpCalibrationMsgVo> list = opCalibrationMsgService.selectOpCalibrationMsgList(opCalibrationMsg);
        AtomicInteger atomicInteger = new AtomicInteger();
        list.forEach(item -> {
            if (item.getSampleAmount()!=1) {
                atomicInteger.set(Integer.parseInt(item.getRecordSerialNumber().substring(item.getRecordSerialNumber().length()-4)));
                atomicInteger.addAndGet(item.getSampleAmount());
                item.setRecordSerialNumber(item.getRecordSerialNumber()+"~"+atomicInteger.decrementAndGet());
            }

            if (StringUtils.isNotEmpty(item.getCourierNumber()) || StringUtils.isNotEmpty(item.getCourierType())) {
                item.setCourierStatus("已寄出");
            } else {
                item.setCourierStatus("未寄出");
            }
        });
        ExcelUtil<OpCalibrationMsgVo> util = new ExcelUtil<>(OpCalibrationMsgVo.class);
        util.exportEasyExcel(response, list, "综合部台账数据");
    }

    /**
     * 业务经理导出综合部台账列表
     */
    @RequiresPermissions("op:calibration:output")
    @Log(title = "综合部台账", businessType = BusinessType.EXPORT)
    @PostMapping(value = {"/output/export/{deskIds}","/output/export/"})
    public void exportOutPut(HttpServletResponse response, OpCalibrationMsgInfo opCalibrationMsg, @PathVariable(value = "deskIds",required = false) List<Long> deskIds)
    {
        //选中导出相关数据
        opCalibrationMsg.setDeskIds(deskIds);
        opCalibrationMsg.setBusinessManager(SecurityUtils.getUsername());
        List<OpCalibrationMsgVo> list = opCalibrationMsgService.selectOpCalibrationMsgList(opCalibrationMsg);
        AtomicInteger atomicInteger = new AtomicInteger();
        list.forEach(item -> {
            if (item.getSampleAmount()!=1) {
                atomicInteger.set(Integer.parseInt(item.getRecordSerialNumber().substring(item.getRecordSerialNumber().length()-4)));
                atomicInteger.addAndGet(item.getSampleAmount());
                item.setRecordSerialNumber(item.getRecordSerialNumber()+"~"+atomicInteger.decrementAndGet());
            }
            // 寄出状态
            if (StringUtils.isNotEmpty(item.getCourierNumber()) || StringUtils.isNotEmpty(item.getCourierType())) {
                item.setCourierStatus("已寄出");
            } else {
                item.setCourierStatus("未寄出");
            }
        });
        // 导出
        ExcelUtil<OpCalibrationMsgVo> util = new ExcelUtil<>(OpCalibrationMsgVo.class);
        util.exportEasyExcel(response, list, "综合部台账数据");
    }

    /**
     * 获取综合部台账详细信息
     */
    @RequiresPermissions("op:calibration:query")
    @GetMapping(value = "/{deskId}")
    public AjaxResult getInfo(@PathVariable("deskId") Long deskId)
    {
        return success(opCalibrationMsgService.selectOpCalibrationMsgByDeskId(deskId));
    }

//    /**
//     * 新增综合部台账
//     */
//    @RequiresPermissions("op:calibration:add")
//    @Log(title = "综合部台账", businessType = BusinessType.INSERT)
//    @PostMapping
//    public AjaxResult add(@RequestBody OpCalibrationMsg opCalibrationMsg)
//    {
//        return toAjax(opCalibrationMsgService.insertOpCalibrationMsg(opCalibrationMsg));
//    }

    /**
     * 修改综合部台账
     */
    @RequiresPermissions("op:calibration:edit")
    @Log(title = "综合部台账", businessType = BusinessType.UPDATE)
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/edit/{deskIds}")
    public AjaxResult edit(@RequestBody OpCalibrationMsg opCalibrationMsg,@PathVariable("deskIds") Long[] deskIds)
    {
        return toAjax(opCalibrationMsgService.updateOpCalibrationMsg(opCalibrationMsg,deskIds));
    }

    /**
     * 删除综合部台账
     */
    @RequiresPermissions("op:calibration:remove")
    @Log(title = "综合部台账", businessType = BusinessType.DELETE)
    @DeleteMapping("/{deskIds}")
    public AjaxResult remove(@PathVariable Long[] deskIds)
    {
        return toAjax(opCalibrationMsgService.deleteOpCalibrationMsgByDeskIds(deskIds));
    }

    /**
     * 盖章
     */
    @RequiresPermissions("op:calibration:upgrade")
    @Log(title = "综合部台账", businessType = BusinessType.UPDATE)
    @PutMapping("/update/{deskIds}")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult statusUpdate(@PathVariable Long[] deskIds)
    {
        return toAjax(opCalibrationMsgService.statusUpdate(deskIds));
    }


    /**
     * 回退盖章
     */
    @RequiresPermissions("op:calibration:back")
    @Log(title = "综合部台账", businessType = BusinessType.UPDATE)
    @PutMapping("/back/{deskIds}")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult statusBack(@PathVariable Long[] deskIds)
    {
        return toAjax(opCalibrationMsgService.statusBack(deskIds));
    }

}

