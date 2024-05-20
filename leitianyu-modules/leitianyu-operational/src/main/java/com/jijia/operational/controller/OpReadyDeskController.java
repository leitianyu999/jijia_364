package com.jijia.operational.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.jijia.operational.domain.info.OpReadyInfo;
import com.jijia.operational.domain.vo.OpReadyDeskVo;
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
import com.jijia.operational.service.IOpReadyDeskService;
import com.jijia.common.core.web.controller.BaseController;
import com.jijia.common.core.web.domain.AjaxResult;
import com.jijia.common.core.utils.poi.ExcelUtil;
import com.jijia.common.core.web.page.TableDataInfo;

/**
 * 预备表Controller
 *
 * @author leitianyu
 * @date 2023-08-08
 */
@RestController
@RequestMapping("/ready")
public class OpReadyDeskController extends BaseController
{
    @Autowired
    private IOpReadyDeskService opReadyDeskService;

    /**
     * 查询预备表列表
     */
    @RequiresPermissions("op:ready:list")
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/list")
    public TableDataInfo list(OpReadyInfo opReadyInfo)
    {
        startPage();
        List<OpReadyDeskVo> list = opReadyDeskService.selectOpReadyDeskList(opReadyInfo);
        return getDataTable(list);
    }

    /**
     * 导出预备表列表
     */
    @RequiresPermissions("op:ready:export")
    @Log(title = "预备表", businessType = BusinessType.EXPORT)
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OpReadyInfo opReadyInfo)
    {
        List<OpReadyDeskVo> list = opReadyDeskService.selectOpReadyDeskList(opReadyInfo);
        ExcelUtil<OpReadyDeskVo> util = new ExcelUtil<OpReadyDeskVo>(OpReadyDeskVo.class);
        util.exportExcel(response, list, "预备表数据");
    }

    /**
     * 获取预备表详细信息
     */
    @RequiresPermissions("op:ready:query")
    @Transactional(rollbackFor = Exception.class)
    @GetMapping(value = "/{readyId}")
    public AjaxResult getInfo(@PathVariable("readyId") Long readyId)
    {
        return success(opReadyDeskService.selectOpReadyDeskByReadyId(readyId));
    }

    /**
     * 新增预备表
     */
    @RequiresPermissions("op:ready:add")
    @Log(title = "预备表", businessType = BusinessType.INSERT)
    @Transactional(rollbackFor = Exception.class)
    @PostMapping
    public AjaxResult add(@RequestBody OpReadyDeskVo opReadyDesk)
    {
        return toAjax(opReadyDeskService.insertOpReadyDesk(opReadyDesk));
    }

    /**
     * 修改预备表
     */
    @RequiresPermissions("op:ready:edit")
    @Log(title = "预备表", businessType = BusinessType.UPDATE)
    @Transactional(rollbackFor = Exception.class)
    @PutMapping
    public AjaxResult edit(@RequestBody OpReadyDeskVo opReadyDesk)
    {
        return toAjax(opReadyDeskService.updateOpReadyDesk(opReadyDesk));
    }

    /**
     * 删除预备表
     */
    @RequiresPermissions("op:ready:remove")
    @Log(title = "预备表", businessType = BusinessType.DELETE)
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/{readyIds}")
    public AjaxResult remove(@PathVariable Long[] readyIds)
    {
        return toAjax(opReadyDeskService.deleteOpReadyDeskByReadyIds(readyIds));
    }

    /**
     * 预备表传入Desk
     */
    @Log(title = "预备表", businessType = BusinessType.OTHER)
    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/insert")
    public AjaxResult addReadyToDesk() {
        return opReadyDeskService.addReadyToDesk();
    }

}

