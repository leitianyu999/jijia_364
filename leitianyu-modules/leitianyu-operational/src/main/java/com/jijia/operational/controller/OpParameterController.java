package com.jijia.operational.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.jijia.operational.domain.OpParameter;
import com.jijia.operational.domain.vo.InfoVo;
import com.jijia.operational.service.IOpParameterService;
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

/**
 * 参数台账Controller
 *
 * @author leitianyu
 * @date 2023-02-07
 */
@RestController
@RequestMapping("/parameter")
public class OpParameterController extends BaseController
{
    @Autowired
    private IOpParameterService opParameterService;

    /**
     * 查询参数台账列表
     */
    @RequiresPermissions("op:parameter:list")
    @GetMapping("/list")
    public TableDataInfo list(OpParameter opParameter)
    {
        startPage();
        List<OpParameter> list = opParameterService.selectOpParameterList(opParameter);
        return getDataTable(list);
    }

    /**
     * 查询参数台账列表
     */
    @RequiresPermissions("op:parameter:listAll")
    @GetMapping("/listAll")
    public TableDataInfo listAll(InfoVo infoVo)
    {
        List<OpParameter> list = opParameterService.getOpParameterList(infoVo);
        return getDataTable(list);
    }



//    /**
//     * 导入工程类型列表
//     */
//    @Transactional(rollbackFor = GlobalException.class)
//    @Log(title = "参数台账", businessType = BusinessType.IMPORT)
//    @RequiresPermissions("op:parameter:import")
//    @PostMapping("/importData")
//    public void  importData(MultipartFile file, boolean updateSupport) throws Exception {
//        AnalysisEventListener<OpParameter> listener = ExcelListenerUtils.getListener(this.batchInsert());
//        EasyExcel.read(file.getInputStream(), OpParameter.class, listener).sheet("项目代码").doRead();
//    }
//
//    private Consumer<List<OpParameter>> batchInsert() {
//        return opParameter -> opParameterService.saveAll(opParameter);
//    }

    /**
     * 导出参数台账列表
     */
    @RequiresPermissions("op:parameter:export")
    @Log(title = "参数台账", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OpParameter opParameter)
    {
        List<OpParameter> list = opParameterService.selectOpParameterList(opParameter);
        ExcelUtil<OpParameter> util = new ExcelUtil<OpParameter>(OpParameter.class);
        util.exportEasyExcel(response, list, "参数台账数据");
    }

    /**
     * 获取参数台账详细信息
     */
    @RequiresPermissions("op:parameter:query")
    @GetMapping(value = "/{parameterId}")
    public AjaxResult getInfo(@PathVariable("parameterId") Long parameterId)
    {
        return success(opParameterService.selectOpParameterByParameterId(parameterId));
    }

    /**
     * 新增参数台账
     */
    @RequiresPermissions("op:parameter:add")
    @Log(title = "参数台账", businessType = BusinessType.INSERT)
    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult add(@RequestBody OpParameter opParameter)
    {
        return toAjax(opParameterService.insertOpParameter(opParameter));
    }

    /**
     * 修改参数台账
     */
    @RequiresPermissions("op:parameter:edit")
    @Log(title = "参数台账", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OpParameter opParameter)
    {
        return toAjax(opParameterService.updateOpParameter(opParameter));
    }

    /**
     * 删除参数台账
     */
    @RequiresPermissions("op:parameter:remove")
    @Log(title = "参数台账", businessType = BusinessType.DELETE)
    @DeleteMapping("/{parameterIds}")
    public AjaxResult remove(@PathVariable Long[] parameterIds)
    {
        return toAjax(opParameterService.deleteOpParameterByParameterIds(parameterIds));
    }
}

