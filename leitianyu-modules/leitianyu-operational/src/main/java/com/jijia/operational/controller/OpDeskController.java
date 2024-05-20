package com.jijia.operational.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.AnalysisEventListener;
import com.jijia.common.core.constant.HttpStatus;
import com.jijia.common.core.constant.SecurityConstants;
import com.jijia.common.core.domain.R;
import com.jijia.common.core.exception.GlobalException;
import com.jijia.common.security.utils.SecurityUtils;
import com.jijia.operational.domain.info.OpDeskInfo;
import com.jijia.operational.domain.vo.OpDeskVo;
import com.jijia.operational.service.IOpCalibrationMsgService;
import com.jijia.operational.service.IOpDeskService;
import com.jijia.operational.service.IOpDetectMsgService;
import com.jijia.operational.utils.ExcelListenerUtils;
import com.jijia.system.api.RemoterDeptService;
import com.jijia.system.api.domain.SysDept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
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
 * 前台台账Controller
 *
 * @author leitianyu
 * @date 2023-02-04
 */
@RestController
@RequestMapping("/desk")
public class OpDeskController extends BaseController
{
    @Autowired
    private IOpDeskService opDeskService;
    @Autowired
    private IOpDetectMsgService opDetectMsgService;
    @Autowired
    private IOpCalibrationMsgService opCalibrationMsgService;
    @Autowired
    private RemoterDeptService remoterDeptService;

    /**
     * 查询前台台账列表
     */
    @RequiresPermissions("op:desk:list")
    @GetMapping("/list")
    public TableDataInfo list(OpDeskInfo opDesk)
    {
        startPage();
        List<OpDeskVo> list = opDeskService.selectOpDeskList(opDesk,Boolean.TRUE);
        return getDataTable(list);
    }


    /**
     * 导入工程类型列表
     */
    @Transactional(rollbackFor = Exception.class)
    @Log(title = "前台台账", businessType = BusinessType.IMPORT)
    @RequiresPermissions("op:desk:import")
    @PostMapping("/importData")
    public AjaxResult  importData(MultipartFile file) throws Exception {
        AnalysisEventListener<OpDeskVo> listener = ExcelListenerUtils.getListener(this.batchInsert());
        EasyExcel.read(file.getInputStream(), OpDeskVo.class, listener).sheet("前台收样台账").headRowNumber(2).doRead();
        return AjaxResult.success();
    }

    private Consumer<List<OpDeskVo>> batchInsert() {
        return opDeskVos -> opDeskService.saveAll(opDeskVos);
    }

    /**
     * 导出前台台账列表
     */
    @RequiresPermissions("op:desk:export")
    @Log(title = "前台台账", businessType = BusinessType.EXPORT)
    @PostMapping(value = {"/export/{deskIds}","/export/"})
    public void export(HttpServletResponse response,OpDeskInfo opDesk , @PathVariable(value = "deskIds",required = false) List<Long> deskIds)
    {
        // 选中导出相关
        opDesk.setDeskIds(deskIds);
        List<OpDeskVo> list = opDeskService.selectOpDeskList(opDesk,Boolean.FALSE);
        AtomicInteger atomicInteger = new AtomicInteger();
        R<List<SysDept>> listR = remoterDeptService.opList(SecurityConstants.INNER);
        if (listR.getCode() != HttpStatus.SUCCESS) {
            throw new GlobalException(listR.getMsg());
        }
        List<SysDept> data = listR.getData();
        list.forEach(item -> {
            if (item.getSampleAmount()!=1) {
                atomicInteger.set(Integer.parseInt(item.getRecordSerialNumber().substring(item.getRecordSerialNumber().length()-4)));
                atomicInteger.addAndGet(item.getSampleAmount());
                item.setRecordSerialNumber(item.getRecordSerialNumber()+"~"+atomicInteger.decrementAndGet());
            }
            if (item.getEditDeptName() != null) {
                String editDeptName = item.getEditDeptName();
                StringBuilder stringBuilder = new StringBuilder();
                for (String s : editDeptName.split(",")) {
                    for (SysDept datum : data) {
                        if (datum.getDeptId().equals(Long.parseLong(s))) {
                            if (stringBuilder.length() > 0) {
                                stringBuilder.append(",").append(datum.getDeptName());
                                break;
                            }
                            stringBuilder.append(datum.getDeptName());
                            break;
                        }
                    }
                };
                item.setEditDeptName(stringBuilder.toString());
            }
            if (item.getVisitDeptName() != null) {
                String visitDeptName = item.getVisitDeptName();
                StringBuilder stringBuilder = new StringBuilder();
                for (String s : visitDeptName.split(",")) {
                    for (SysDept datum : data) {
                        if (datum.getDeptId().equals(Long.parseLong(s))) {
                            if (stringBuilder.length() > 0) {
                                stringBuilder.append(",").append(datum.getDeptName());
                                break;
                            }
                            stringBuilder.append(datum.getDeptName());
                            break;
                        }
                    }
                };
                item.setVisitDeptName(stringBuilder.toString());
            }
        });
        ExcelUtil<OpDeskVo> util = new ExcelUtil<OpDeskVo>(OpDeskVo.class);
        // excel第一行标题
        util.hideColumn("editDeptName","visitDeptName");
        util.exportEasyExcel(response, list, "前台收样台账");
    }

    /**
     * 获取前台台账详细信息
     */
    @RequiresPermissions("op:desk:query")
    @GetMapping(value = "/{deskId}")
    public AjaxResult getInfo(@PathVariable("deskId") Long deskId)
    {
        return success(opDeskService.selectOpDeskByDeskId(deskId));
    }

    /**
     * 导出excel模板
     * @param response 请求
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws IOException
    {
        ExcelUtil<OpDeskVo> util = new ExcelUtil<OpDeskVo>(OpDeskVo.class);
        util.exportEasyExcel(response, null,"前台收样台账");
    }

    /**
     * 获取数据是否为最后一条
     */
    @RequiresPermissions("op:desk:judgment")
    @GetMapping(value = "/judgment/{deskId}")
    public AjaxResult judgmentIsLast (@PathVariable("deskId") Long deskId) {
        return success(opDeskService.judgmentIsLast(deskId));
    }

    /**
     * 新增前台台账
     */
    @RequiresPermissions("op:desk:add")
    @Log(title = "前台台账", businessType = BusinessType.INSERT)
    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult add(@Validated  @RequestBody OpDeskVo opDesk)
    {
        AtomicInteger atomicInteger = new AtomicInteger();
        opDesk.setCreateBy(SecurityUtils.getUsername());
        // 创建前台台账数据
        return toAjax(opDeskService.insertOpDesk(opDesk));
    }

    /**
     * 修改前台台账
     */
    @RequiresPermissions("op:desk:edit")
    @Log(title = "前台台账", businessType = BusinessType.UPDATE)
    @PutMapping
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult edit(@Validated @RequestBody OpDeskVo opDesk)
    {
        return toAjax(opDeskService.updateOpDesk(opDesk));
    }

    /**
     * 修改前台台账
     */
    @RequiresPermissions("op:desk:editone")
    @Log(title = "前台台账", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult editOne(@Validated @RequestBody OpDeskVo opDesk)
    {
        return toAjax(opDeskService.updateOpDeskOne(opDesk));
    }

    /**
     * 批量修改修改前台台账
     */
    @RequiresPermissions("op:desk:editall")
    @Log(title = "前台台账", businessType = BusinessType.UPDATE)
    @PostMapping("/edit/{deskIds}")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult editAll(@RequestBody OpDeskVo opDesk,@PathVariable("deskIds") Long[] deskIds)
    {
        return toAjax(opDeskService.updateOpDeskAll(opDesk,deskIds));
    }

    /**
     * 删除前台台账
     */
    @RequiresPermissions("op:desk:remove")
    @Log(title = "前台台账", businessType = BusinessType.DELETE)
    @DeleteMapping("/{deskIds}")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult remove(@PathVariable Long[] deskIds)
    {
        return toAjax(opDeskService.deleteOpDeskByDeskIds(deskIds));
    }

    /**
     * 批量更改部门权限
     */
    @RequiresPermissions("op:desk:dept")
    @Log(title = "前台台账", businessType = BusinessType.GRANT)
    @PostMapping("/dept/{deskIds}")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult updateDept(@PathVariable("deskIds") Long[] deskIds ,@RequestBody Map<String,List<Long>> map) {
        return toAjax(opDetectMsgService.updateDeptId(deskIds,map.get("editDept"),map.get("visitDept")));
    }


}

