package com.jijia.camunda.strategy.service.modelUpdate.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.jijia.camunda.annotation.ModelAnnotation;
import com.jijia.camunda.domain.CmdModel;
import com.jijia.camunda.domain.CmdModelForm;
import com.jijia.camunda.domain.dto.CmdModelDto;
import com.jijia.camunda.domain.enums.ModelType;
import com.jijia.camunda.mapper.newM.CmdModelFormMapper;
import com.jijia.camunda.mapper.newM.CmdModelMapper;
import com.jijia.camunda.strategy.service.modelUpdate.abstractImpl.AbstractModelStrategy;
import com.jijia.common.core.exception.GlobalException;
import com.jijia.common.security.utils.SecurityUtils;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author leitianyu
 */
@Component
@ModelAnnotation(modelUpdateType = ModelType.is_STOP)
public class ModelStopImpl extends AbstractModelStrategy {

    @Resource
    private CmdModelMapper cmdModelMapper;
    @Resource
    private CmdModelFormMapper cmdModelFormMapper;
    @Resource
    private RepositoryService repositoryService;


    public int updateBpmnXml(CmdModelDto cmdModelDto, CmdModel cmdModel) {
        return newModel(cmdModelDto, cmdModel);
    }

    @Override
    public int updateModelForm(CmdModelDto cmdModelDto, CmdModel cmdModel) {
        throw new GlobalException("已发布模型不允许修改表单");
    }

    @Override
    public int updateModelStatus(CmdModelDto cmdModelDto, CmdModel cmdModel) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(cmdModel.getDeploymentId()).singleResult();
        if (processDefinition == null) {
            throw new GlobalException("流程定义不存在");
        }
        repositoryService.activateProcessDefinitionById(processDefinition.getId());
        cmdModel.setStatus("1");
        cmdModel.setUpdateBy(SecurityUtils.getUsername());
        cmdModel.setUpdateTime(new Date());
        cmdModel.setDeployTime(new Date());
        return cmdModelMapper.updateById(cmdModel);
    }


}
