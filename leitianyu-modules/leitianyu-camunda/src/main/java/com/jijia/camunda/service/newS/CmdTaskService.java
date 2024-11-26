package com.jijia.camunda.service.newS;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jijia.camunda.domain.dto.HandleDataDTO;
import com.jijia.camunda.domain.dto.ProcessInstanceDto;
import com.jijia.camunda.domain.vo.HistoryProcessInstanceVO;
import com.jijia.common.core.web.page.TableDataInfo;

import java.util.Map;

public interface CmdTaskService {


    public Long start(ProcessInstanceDto startProcessInstanceDTO);

    public TableDataInfo applyList();

    public TableDataInfo toDoList();

    public TableDataInfo doneList();

    public void complete(HandleDataDTO handleDataDTO);

    public Map<String, String> getRollbackNodes(HandleDataDTO handleDataDTO);

    public void instanceInfo(HandleDataDTO handleDataDTO);
}
