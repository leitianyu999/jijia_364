package com.jijia.system.controller;


import com.jijia.common.core.utils.poi.ExcelUtil;
import com.jijia.common.core.web.controller.BaseController;
import com.jijia.common.core.web.domain.AjaxResult;
import com.jijia.common.core.web.page.TableDataInfo;
import com.jijia.common.log.annotation.Log;
import com.jijia.common.log.enums.BusinessType;
import com.jijia.common.security.annotation.RequiresPermissions;
import com.jijia.common.security.utils.SecurityUtils;
import com.jijia.system.api.domain.SysUserInfo;
import com.jijia.system.service.ISysUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * 用户详细信息Controller
 *
 * @author leitianyu
 * @date 2023-02-03
 */
@RestController
@RequestMapping("/info")
public class SysUserInfoController extends BaseController
{
    @Autowired
    private ISysUserInfoService sysUserInfoService;

    /**
     * 查询用户详细信息列表
     */
    @RequiresPermissions("system:info:list")
    @GetMapping("/list")
    public TableDataInfo list(SysUserInfo sysUserInfo)
    {
        startPage();
        List<SysUserInfo> list = sysUserInfoService.selectSysUserInfoList(sysUserInfo);
        return getDataTable(list);
    }

    /**
     * 导出用户详细信息列表
     */
    @RequiresPermissions("system:info:export")
    @Log(title = "用户详细信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysUserInfo sysUserInfo)
    {
        List<SysUserInfo> list = sysUserInfoService.selectSysUserInfoList(sysUserInfo);
        ExcelUtil<SysUserInfo> util = new ExcelUtil<SysUserInfo>(SysUserInfo.class);
        util.exportExcel(response, list, "用户详细信息数据");
    }

    /**
     * 获取用户详细信息详细信息
     */
    @RequiresPermissions("system:info:query")
    @GetMapping(value = "/{userId}")
    public AjaxResult getInfo(@PathVariable("userId") Long userId)
    {
        return success(sysUserInfoService.selectSysUserInfoByUserId(userId));
    }

    /**
     * 获取用户详细信息详细信息
     */
    @RequiresPermissions("system:info:query")
    @GetMapping(value = "/Byself")
    public AjaxResult getInfoByself()
    {
        return success(sysUserInfoService.selectSysUserInfoByUserId(SecurityUtils.getUserId()));
    }

    /**
     * 新增用户详细信息
     */
    @RequiresPermissions("system:info:add")
    @Log(title = "用户详细信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysUserInfo sysUserInfo)
    {
        return toAjax(sysUserInfoService.insertSysUserInfo(sysUserInfo));
    }

    /**
     * 修改用户详细信息
     */
    @RequiresPermissions("system:info:edit")
    @Log(title = "用户详细信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysUserInfo sysUserInfo)
    {
        return toAjax(sysUserInfoService.updateSysUserInfo(sysUserInfo));
    }

    /**
     * 删除用户详细信息
     */
    @RequiresPermissions("system:info:remove")
    @Log(title = "用户详细信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds)
    {
        return toAjax(sysUserInfoService.deleteSysUserInfoByUserIds(userIds));
    }
}
