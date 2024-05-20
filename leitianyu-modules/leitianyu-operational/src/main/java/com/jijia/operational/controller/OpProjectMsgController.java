package com.jijia.operational.controller;

import java.util.List;
import java.io.IOException;
import java.util.function.Consumer;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.AnalysisEventListener;
import com.jijia.common.core.exception.GlobalException;
import com.jijia.operational.domain.OpProjectMsg;
import com.jijia.operational.domain.vo.InfoVo;
import com.jijia.operational.domain.vo.OpProjectMsgVo;
import com.jijia.operational.service.IOpProjectMsgService;
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
import com.jijia.common.core.web.controller.BaseController;
import com.jijia.common.core.web.domain.AjaxResult;
import com.jijia.common.core.utils.poi.ExcelUtil;
import com.jijia.common.core.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 工程类型Controller
 *
 * @author leitianyu
 * @date 2023-02-05
 */
@RestController
@RequestMapping("/projectMsg")
public class OpProjectMsgController extends BaseController
{
    private final IOpProjectMsgService opProjectMsgService;

    public OpProjectMsgController(IOpProjectMsgService opProjectMsgService) {
        this.opProjectMsgService = opProjectMsgService;
    }

    /**
     * 查询工程类型列表
     */
    @RequiresPermissions("op:projectMsg:list")
    @GetMapping("/list")
    public TableDataInfo list(OpProjectMsg opProjectMsg)
    {
        startPage();
        List<OpProjectMsg> list = opProjectMsgService.selectOpProjectMsgList(opProjectMsg);
        return getDataTable(list);
    }

    /**
     * 查询工程类型列表
     */
    @RequiresPermissions("op:projectMsg:listAll")
    @GetMapping("/listAll")
    public TableDataInfo listAll(InfoVo infoVo)
    {
        List<OpProjectMsgVo> list = opProjectMsgService.getListAll(infoVo);
        return getDataTable(list);
    }


    /**
     * 导入工程类型列表
     */
    @Transactional(rollbackFor = GlobalException.class)
    @Log(title = "工程类型", businessType = BusinessType.IMPORT)
    @RequiresPermissions("op:projectMsg:import")
    @PostMapping("/importData")
    public void  importData(MultipartFile file, boolean updateSupport) throws Exception {
        AnalysisEventListener<OpProjectMsg> listener = ExcelListenerUtils.getListener(this.batchInsert());
        EasyExcel.read(file.getInputStream(), OpProjectMsg.class, listener).sheet("工程编号台账").doRead();
    }

    private Consumer<List<OpProjectMsg>> batchInsert() {
        return opProjectMsgService::saveAll;
    }

    /**
     * 导出工程类型列表
     */
    @RequiresPermissions("op:projectMsg:export")
    @Log(title = "工程类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OpProjectMsg opProjectMsg) throws IOException {
        List<OpProjectMsg> list = opProjectMsgService.selectOpProjectMsgList(opProjectMsg);
        ExcelUtil<OpProjectMsg> util = new ExcelUtil<OpProjectMsg>(OpProjectMsg.class);
        util.exportEasyExcel(response,list,"工程编号台账");
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws IOException
    {
        ExcelUtil<OpProjectMsg> util = new ExcelUtil<OpProjectMsg>(OpProjectMsg.class);
        util.exportEasyExcel(response, null,"工程编号台账");
    }

    /**
     * 获取工程类型详细信息
     */
    @RequiresPermissions("op:projectMsg:query")
    @GetMapping(value = "/{projectId}")
    public AjaxResult getInfo(@PathVariable("projectId") Long projectId)
    {
        return success(opProjectMsgService.selectOpProjectMsgByProjectId(projectId));
    }

    /**
     * 新增工程类型
     */
    @RequiresPermissions("op:projectMsg:add")
    @Log(title = "工程类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OpProjectMsg opProjectMsg)
    {
        return toAjax(opProjectMsgService.insertOpProjectMsg(opProjectMsg));
    }

    /**
     * 修改工程类型
     */
    @RequiresPermissions("op:projectMsg:edit")
    @Log(title = "工程类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OpProjectMsg opProjectMsg)
    {
        return toAjax(opProjectMsgService.updateOpProjectMsg(opProjectMsg));
    }

    /**
     * 删除工程类型
     */
    @RequiresPermissions("op:projectMsg:remove")
    @Log(title = "工程类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{projectIds}")
    public AjaxResult remove(@PathVariable Long[] projectIds)
    {
        return toAjax(opProjectMsgService.deleteOpProjectMsgByProjectIds(projectIds));
    }
}

