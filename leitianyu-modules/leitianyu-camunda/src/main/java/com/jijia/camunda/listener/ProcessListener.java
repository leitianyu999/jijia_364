package com.jijia.camunda.listener;


import javax.annotation.Resource;

import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

import static com.jijia.camunda.utils.CommonConstants.BUSINESS_STATUS_4;
import static com.jijia.camunda.utils.CommonConstants.PROCESS_STATUS;

/**
 * @author LoveMyOrange
 * @create 2022-10-15 19:47
 */
@Component
public class ProcessListener implements ExecutionListener {
    @Resource
    private RepositoryService repositoryService;
    @Override
    public void notify(DelegateExecution execution) {
        execution.setVariable(PROCESS_STATUS,BUSINESS_STATUS_4);
    }
}
