package com.jijia.camunda.controller;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jijia.camunda.domain.dto.HandleDataDTO;
import com.jijia.camunda.domain.dto.ProcessInstanceDto;
import com.jijia.camunda.domain.vo.HistoryProcessInstanceVO;
import com.jijia.camunda.dto.StartProcessInstanceDTO;
import com.jijia.camunda.service.newS.CmdTaskService;
import com.jijia.common.core.web.controller.BaseController;
import com.jijia.common.core.web.domain.AjaxResult;
import com.jijia.common.core.web.page.TableDataInfo;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;

/**
 * TODO
 *
 * @author leitianyu999
 * @create 2024/11/25
 */
@RestController
@RequestMapping("/task")
public class CmdTaskController extends BaseController {

    @Resource
    private CmdTaskService cmdTaskService;



    // 启动流程
    @PostMapping("process/start")
    public AjaxResult start(@RequestBody ProcessInstanceDto startProcessInstanceDTO){
        Long start = cmdTaskService.start(startProcessInstanceDTO);
        return AjaxResult.success(start);
    }


    @PostMapping("process/applyList")
    public TableDataInfo applyList(){
        return cmdTaskService.applyList();
    }

    @PostMapping("process/toDoList")
    public TableDataInfo toDoList(){
        return cmdTaskService.toDoList();
    }

    @PostMapping("process/doneList")
    public TableDataInfo doneList(){
        return cmdTaskService.doneList();
    }

    @PostMapping("/complete")
    public AjaxResult CompleteTask(@RequestBody HandleDataDTO handleDataDTO){
        cmdTaskService.complete(handleDataDTO);
        return AjaxResult.success();
    }

    @PostMapping("/rollbackNodes")
    public AjaxResult rollbackNodes(@RequestBody HandleDataDTO handleDataDTO){

        return AjaxResult.success(cmdTaskService.getRollbackNodes(handleDataDTO));
    }

    @PostMapping("process/instanceInfo")
    public AjaxResult instanceInfo(@RequestBody HandleDataDTO HandleDataDTO){
        cmdTaskService.instanceInfo(HandleDataDTO);
        return AjaxResult.success();
    }


}
