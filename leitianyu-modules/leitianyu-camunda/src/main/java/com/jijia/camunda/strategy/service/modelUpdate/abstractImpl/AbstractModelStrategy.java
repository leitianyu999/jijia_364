package com.jijia.camunda.strategy.service.modelUpdate.abstractImpl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.google.common.collect.Lists;
import com.jijia.camunda.constants.CamundaWorkConstants;
import com.jijia.camunda.domain.CmdModel;
import com.jijia.camunda.domain.CmdModelForm;
import com.jijia.camunda.domain.dto.ChildNode;
import com.jijia.camunda.domain.dto.CmdModelDto;
import com.jijia.camunda.mapper.newM.CmdModelFormMapper;
import com.jijia.camunda.mapper.newM.CmdModelMapper;
import com.jijia.camunda.strategy.service.modelUpdate.ModelStrategy;
import com.jijia.common.security.utils.SecurityUtils;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.builder.ProcessBuilder;
import org.camunda.bpm.model.bpmn.builder.StartEventBuilder;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author leitianyu
 */
@Component
public abstract class AbstractModelStrategy implements ModelStrategy {


    @Resource
    private CmdModelFormMapper cmdModelFormMapper;
    @Resource
    private CmdModelMapper cmdModelMapper;

    public int updateModelOnly(CmdModelDto cmdModelDto, CmdModel cmdModel) {

        cmdModel.setName(cmdModelDto.getName());
        cmdModel.setDescription(cmdModelDto.getDescription());
        cmdModel.setUpdateBy(SecurityUtils.getUsername());
        cmdModel.setUpdateTime(new Date());
        return cmdModelMapper.updateById(cmdModel);
    };
    public abstract int updateBpmnXml(CmdModelDto cmdModelDto, CmdModel cmdModel);
    public abstract int updateModelForm(CmdModelDto cmdModelDto, CmdModel cmdModel);



    @Override
    public int updateModel(CmdModelDto cmdModelDto, CmdModel cmdModel) {
        switch (cmdModelDto.getUpdateType()) {
            case "1":
                return updateModelOnly(cmdModelDto, cmdModel);
            case "2":
                return updateBpmnXml(cmdModelDto, cmdModel);
            case "3":
                return updateModelForm(cmdModelDto, cmdModel);
            default:
                throw new RuntimeException("更新类型错误");
        }
    }

    public CmdModelForm getModelFormList(Long modelId) {
        return cmdModelFormMapper.selectOne(new LambdaQueryChainWrapper<>(cmdModelFormMapper).eq(CmdModelForm::getModelId, modelId));
    }







}
