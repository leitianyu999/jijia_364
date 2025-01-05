package com.jijia.operational.controller;

import com.jijia.common.core.constant.HttpStatus;
import com.jijia.common.core.constant.SecurityConstants;
import com.jijia.common.core.domain.R;
import com.jijia.common.core.exception.GlobalException;
import com.jijia.common.core.utils.poi.ExcelUtil;
import com.jijia.common.core.web.controller.BaseController;
import com.jijia.common.core.web.domain.AjaxResult;
import com.jijia.common.core.web.page.TableDataInfo;
import com.jijia.common.log.annotation.Log;
import com.jijia.common.log.enums.BusinessType;
import com.jijia.common.security.annotation.RequiresPermissions;
import com.jijia.operational.domain.info.OpDeskInfo;
import com.jijia.operational.domain.vo.OpDeskVo;
import com.jijia.operational.domain.vo.OpSettleVo;
import com.jijia.operational.service.IOpDeskService;
import com.jijia.operational.service.IOpSettleService;
import com.jijia.system.api.RemoterDeptService;
import com.jijia.system.api.domain.SysDept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@RestController
@RequestMapping("/settle")
public class OpSettleController extends BaseController {

    @Resource
    private IOpSettleService settleService;
    @Resource
    private RemoterDeptService remoterDeptService;


    /**
     * 查询前台台账列表
     */
    @RequiresPermissions("op:settle:list")
    @GetMapping("/list")
    public TableDataInfo list(OpDeskInfo opDesk)
    {
        startPage();
        List<OpSettleVo> list = settleService.selectOpDeskList(opDesk);
        return getDataTable(list);
    }

    /**
     * 导出前台台账列表
     */
    @RequiresPermissions("op:settle:export")
    @Log(title = "前台台账", businessType = BusinessType.EXPORT)
    @PostMapping(value = {"/export/{deskIds}","/export/"})
    public void export(HttpServletResponse response, OpDeskInfo opDesk , @PathVariable(value = "deskIds",required = false) List<Long> deskIds)
    {
        // 选中导出相关
        opDesk.setDeskIds(deskIds);
        List<OpSettleVo> list = settleService.selectOpDeskList(opDesk);
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
        ExcelUtil<OpSettleVo> util = new ExcelUtil<OpSettleVo>(OpSettleVo.class);
        // excel第一行标题
        util.hideColumn("editDeptName","visitDeptName");
        util.exportEasyExcel(response, list, "结算台账");
    }

    /**
     * 批量修改修改前台台账
     */
    @RequiresPermissions("op:settle:editall")
    @Log(title = "前台台账", businessType = BusinessType.UPDATE)
    @PostMapping("/edit/{deskIds}")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult editAll(@RequestBody OpSettleVo opSettleVo,@PathVariable("deskIds") Long[] deskIds)
    {
        return toAjax(settleService.updateOpDeskAll(opSettleVo,deskIds));
    }

}
