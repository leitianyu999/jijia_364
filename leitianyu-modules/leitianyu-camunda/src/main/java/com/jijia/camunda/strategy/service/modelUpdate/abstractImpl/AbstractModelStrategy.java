package com.jijia.camunda.strategy.service.modelUpdate.abstractImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
import com.jijia.common.core.exception.GlobalException;
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
    public abstract int updateModelStatus(CmdModelDto cmdModelDto, CmdModel cmdModel);



    @Override
    public int updateModel(CmdModelDto cmdModelDto, CmdModel cmdModel) {
        switch (cmdModelDto.getUpdateType()) {
            case "1":
            case "2":
                int updated = updateModelOnly(cmdModelDto, cmdModel);
                if (vailForm(cmdModelDto, cmdModel)) {
                    return updated + updateModelForm(cmdModelDto, cmdModel);
                }
                return updated;
            case "3":
                vailIsNewer(cmdModelDto, cmdModel);
                return updateBpmnXml(cmdModelDto, cmdModel);
            case"4":
                return updateModelStatus(cmdModelDto, cmdModel);
            default:
                throw new RuntimeException("更新类型错误");
        }
    }

    private boolean vailForm(CmdModelDto cmdModelDto, CmdModel cmdModel) {
        if (cmdModelDto.getFormId() != null) {
            CmdModelForm cmdModelForm = cmdModelFormMapper.selectOne(new LambdaQueryWrapper<CmdModelForm>().eq(CmdModelForm::getModelId, cmdModel.getModelId()));
            return cmdModelForm == null || !cmdModelForm.getFormId().equals(cmdModelDto.getFormId());
        }
        return false;
    }

    private void vailIsNewer(CmdModelDto cmdModelDto, CmdModel cmdModel) {
        // 是否是最新的模型
        CmdModel newModel = cmdModelMapper.selectOne(new LambdaQueryWrapper<CmdModel>().eq(CmdModel::getCode,
                cmdModel.getCode()).eq(CmdModel::getVersion, cmdModel.getVersion() + 1));
        if (newModel != null) {
            throw new GlobalException("系统错误：存在新版本模型");
        }
    }

    public int newModel(CmdModelDto cmdModelDto, CmdModel cmdModel) {
        Long modelId = cmdModel.getModelId();

        // 新增未部署版本
        int insert =  addNewModel(cmdModelDto, cmdModel);

        insert += insertModelForm(cmdModel, modelId);

        return insert;
    }

    public int addNewModel(CmdModelDto cmdModelDto, CmdModel cmdModel) {
        // 新增未部署版本
        cmdModel.setModelId(null);
        cmdModel.setVersion(cmdModel.getVersion() + 1);
        cmdModel.setStatus("0");
        cmdModel.setDeploymentId(null);
        cmdModel.setBpmnXml(null);
        cmdModel.setNodeJsonData(cmdModelDto.getNodeJsonData());
        cmdModel.setCreateBy(SecurityUtils.getUsername());
        cmdModel.setCreateTime(new Date());
        cmdModel.setUpdateBy(SecurityUtils.getUsername());
        cmdModel.setUpdateTime(new Date());
        cmdModel.setDeployTime(null);
        return cmdModelMapper.insert(cmdModel);
    }

    public int addNewModelNotNode(CmdModelDto cmdModelDto, CmdModel cmdModel) {
        // 新增未部署版本
        cmdModel.setModelId(null);
        cmdModel.setVersion(cmdModel.getVersion() + 1);
        cmdModel.setStatus("0");
        cmdModel.setDeploymentId(null);
        cmdModel.setBpmnXml(null);
        cmdModel.setNodeJsonData(null);
        cmdModel.setCreateBy(SecurityUtils.getUsername());
        cmdModel.setCreateTime(new Date());
        cmdModel.setUpdateBy(SecurityUtils.getUsername());
        cmdModel.setUpdateTime(new Date());
        cmdModel.setDeployTime(null);
        return cmdModelMapper.insert(cmdModel);
    }


    public int insertModelForm(CmdModel cmdModel, Long modelId) {
        LambdaQueryWrapper<CmdModelForm> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CmdModelForm::getModelId, modelId);
        CmdModelForm cmdModelForm = cmdModelFormMapper.selectOne(queryWrapper);
        if (cmdModelForm != null) {
            CmdModelForm add = new CmdModelForm();
            add.setModelId(cmdModel.getModelId());
            add.setFormId(cmdModelForm.getFormId());
            return cmdModelFormMapper.insert(add);
        }
        return 0;
    }






}
