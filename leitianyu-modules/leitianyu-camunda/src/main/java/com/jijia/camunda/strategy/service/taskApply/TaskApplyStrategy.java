package com.jijia.camunda.strategy.service.taskApply;


import com.jijia.camunda.domain.dto.HandleDataDTO;

/**
 * 台账生成接口
 *
 * @author leitianyu
 */
public interface TaskApplyStrategy {


    public void CompleteTask(HandleDataDTO handleDataDTO);

}
