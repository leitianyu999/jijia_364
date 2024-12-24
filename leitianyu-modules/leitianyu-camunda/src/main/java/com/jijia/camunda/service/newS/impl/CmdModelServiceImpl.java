package com.jijia.camunda.service.newS.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jijia.camunda.domain.CmdForm;
import com.jijia.camunda.domain.CmdModel;
import com.jijia.camunda.domain.CmdModelForm;
import com.jijia.camunda.domain.dto.CmdModelDto;
import com.jijia.camunda.domain.enums.ModelType;
import com.jijia.camunda.domain.vo.CmdFormVo;
import com.jijia.camunda.domain.vo.CmdModelVo;
import com.jijia.camunda.mapper.newM.CmdFormMapper;
import com.jijia.camunda.mapper.newM.CmdModelCateGoryMapper;
import com.jijia.camunda.mapper.newM.CmdModelFormMapper;
import com.jijia.camunda.mapper.newM.CmdModelMapper;
import com.jijia.camunda.service.newS.CmdModelService;
import com.jijia.camunda.strategy.handler.HandlerModelContext;
import com.jijia.camunda.strategy.service.modelUpdate.ModelStrategy;
import com.jijia.camunda.utils.BpmnUtils;
import com.jijia.camunda.utils.CamundaFlowUtils;
import com.jijia.common.core.exception.GlobalException;
import com.jijia.common.security.utils.SecurityUtils;
import org.bouncycastle.math.raw.Mod;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * TODO
 *
 * @author Raoqian
 * @company ZhiDao Tech
 * @create 2024/11/12
 */
@Service
public class CmdModelServiceImpl implements CmdModelService {

    @Resource
    private CmdModelMapper cmdModelMapper;
    @Resource
    private CmdModelCateGoryMapper cmdModelCateGoryMapper;
    @Resource
    private CmdModelFormMapper cmdModelFormMapper;
    @Resource
    private CmdFormMapper cmdFormMapper;
    @Resource
    private RepositoryService repositoryService;

    @Override
    public CmdModelVo getModel(Long id) {
        CmdModelVo cmdModelVo = BeanUtil.toBean(cmdModelMapper.selectById(id), CmdModelVo.class);
        if (cmdModelVo != null) {
            CmdModelForm cmdModelForm = cmdModelFormMapper.selectById(cmdModelVo.getModelId());
            if (cmdModelForm != null) {
                CmdForm cmdForm = cmdFormMapper.selectById(cmdModelForm.getFormId());
                cmdModelVo.setFormName(cmdForm.getName());
                cmdModelVo.setFormId(cmdForm.getFormId());
            }
        }
        return cmdModelVo;
    }

    @Override
    public List<CmdModelVo> getModelList(CmdModelDto cmdModelDto) {
        List<CmdModelVo> cmdModelVos = BeanUtil.copyToList(cmdModelMapper.selectCmdModelList(BeanUtil.toBean(cmdModelDto, CmdModel.class)), CmdModelVo.class);

        cmdModelVos.forEach(cmdModelVo -> {


            CmdModelForm cmdModelForm = cmdModelFormMapper.selectById(cmdModelVo.getModelId());
            if (cmdModelForm != null) {
                CmdForm cmdForm = cmdFormMapper.selectById(cmdModelForm.getFormId());
                cmdModelVo.setFormName(cmdForm.getName());
                cmdModelVo.setFormId(cmdForm.getFormId());
                cmdModelVo.setFormVersion(cmdForm.getVersion().toString());
            }

            if (cmdModelVo.getVersion() == 1 && !cmdModelVo.getStatus().equals("0")) {
                cmdModelVo.setDeployFormId(cmdModelVo.getFormId());
                cmdModelVo.setDeployFormName(cmdModelVo.getFormName());
                cmdModelVo.setDeployFormVersion(cmdModelVo.getFormVersion());
                cmdModelVo.setDeployDescription(cmdModelVo.getDescription());
                cmdModelVo.setDeployVersion(cmdModelVo.getVersion());
                cmdModelVo.setDeployModelId(cmdModelVo.getModelId());
                cmdModelVo.setDeployStatus(cmdModelVo.getStatus());
            } else if (cmdModelVo.getVersion() != 1 && !cmdModelVo.getStatus().equals("0")) {
                cmdModelVo.setDeployFormId(cmdModelVo.getFormId());
                cmdModelVo.setDeployFormName(cmdModelVo.getFormName());
                cmdModelVo.setDeployFormVersion(cmdModelVo.getFormVersion());
                cmdModelVo.setDeployDescription(cmdModelVo.getDescription());
                cmdModelVo.setDeployVersion(cmdModelVo.getVersion());
                cmdModelVo.setDeployModelId(cmdModelVo.getModelId());
                cmdModelVo.setDeployStatus(cmdModelVo.getStatus());
                cmdModelVo.setDeployTime(cmdModelVo.getDeployTime());
            } else if (cmdModelVo.getVersion() != 1) {
                LambdaQueryWrapper<CmdModel> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(CmdModel::getCode, cmdModelVo.getCode());
                wrapper.eq(CmdModel::getVersion, cmdModelVo.getVersion() - 1);
                CmdModel cmdModel = cmdModelMapper.selectOne(wrapper);
                if (cmdModel != null) {
                    cmdModelVo.setDeployDescription(cmdModel.getDescription());
                    cmdModelVo.setDeployVersion(cmdModel.getVersion());
                    cmdModelVo.setDeployModelId(cmdModel.getModelId());
                    cmdModelVo.setDeployStatus(cmdModel.getStatus());
                    cmdModelVo.setDeployTime(cmdModel.getDeployTime());

                    CmdModelForm cmdDeployModelForm = cmdModelFormMapper.selectById(cmdModel.getModelId());
                    if (cmdDeployModelForm != null) {
                        CmdForm cmdForm = cmdFormMapper.selectById(cmdDeployModelForm.getFormId());
                        cmdModelVo.setDeployFormName(cmdForm.getName());
                        cmdModelVo.setDeployFormId(cmdForm.getFormId());
                        cmdModelVo.setDeployFormVersion(cmdForm.getVersion().toString());
                    } else {
                        throw new GlobalException("上一版本模型表单不存在");
                    }
                } else {
                    throw new GlobalException("上一版本模型不存在");
                }
            }
        });

        return cmdModelVos;
    }

    @Override
    public int addModel(CmdModelDto cmdModelDto) {

        CmdModel cmdModel = cmdModelMapper.selectOne(new LambdaQueryWrapper<CmdModel>().eq(CmdModel::getCode, cmdModelDto.getCode()));
        if (cmdModel != null) {
            throw new GlobalException("模型编码已存在");
        }

        CmdModel model = BeanUtil.toBean(cmdModelDto, CmdModel.class);
        if (model.getVersion() == null || model.getVersion() != 1) {
            model.setModelId(1L);
        }
        model.setCreateBy(SecurityUtils.getUsername());
        model.setCreateTime(new Date());
        int insert = cmdModelMapper.insert(model);
        if (cmdModelDto.getFormId() != null) {
            CmdModelForm cmdModelForm = new CmdModelForm();
            cmdModelForm.setFormId(cmdModelDto.getFormId());
            cmdModelForm.setModelId(model.getModelId());
            int insert1 = cmdModelFormMapper.insert(cmdModelForm);
            if (insert1 == 0) {
                throw new GlobalException("模型表单关联失败");
            }
            insert += insert1;
        }
        return insert;
    }

    @Override
    public int updateModel(CmdModelDto cmdModelDto) {

        CmdModel cmdModel = cmdModelMapper.selectById(cmdModelDto.getModelId());
        if (cmdModel == null) {
            throw new GlobalException("模型不存在");
        }

        ModelStrategy instance = HandlerModelContext.getInstance(ModelType.getEnum(cmdModel.getStatus()));

        return instance.updateModel(cmdModelDto, cmdModel);
    }

    @Override
    public int deleteModel(Long id) {
        return cmdModelMapper.deleteById(id);
    }

    @Override
    public int reployModel(Long modelId) {
        CmdModel cmdModel = cmdModelMapper.selectById(modelId);
        if (cmdModel == null) {
            throw new GlobalException("模型不存在");
        }

        // 是否是最新的模型
        CmdModel newModel = cmdModelMapper.selectOne(new LambdaQueryWrapper<CmdModel>().eq(CmdModel::getCode,
                cmdModel.getCode()).eq(CmdModel::getVersion, cmdModel.getVersion() + 1));
        if (newModel != null) {
            throw new GlobalException("系统错误：存在新版本模型");
        }


        if (cmdModel.getStatus().equals("1")) {
            throw new GlobalException("模型已发布");
        } else if (cmdModel.getStatus().equals("2")) {
            if (cmdModel.getDeploymentId() == null) {
                throw new GlobalException("系统错误：模型未发布");
            }
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(cmdModel.getDeploymentId()).singleResult();
            if (processDefinition != null) {
                repositoryService.activateProcessDefinitionById(processDefinition.getId());
                cmdModel.setStatus("1");
                cmdModel.setDeployTime(new Date());
                cmdModel.setUpdateBy(SecurityUtils.getUsername());
                cmdModel.setUpdateTime(new Date());
                return cmdModelMapper.updateById(cmdModel);
            } else {
                throw new GlobalException("系统错误：流程定义不存在");
            }
        } else {
            // 发布模型
            // 1.校验表单是否存在
            validateForm(cmdModel);

            // 获取模型的xml
            BpmnModelInstance xml = BpmnUtils.getBpmnXmlByJson(cmdModel);
            // 2.校验bpmn
            Bpmn.validateModel(xml);
            validateBpmn(xml);

            // 3.发布模型
            Deployment deployment = repositoryService.createDeployment()
                    .addModelInstance(cmdModel.getName() + ".bpmn", xml)
                    .name(cmdModel.getName())
                    .deploy();
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();

            // 4.挂起模型
            LambdaQueryWrapper<CmdModel> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(CmdModel::getCode, cmdModel.getCode()).eq(CmdModel::getVersion, cmdModel.getVersion() - 1);
            CmdModel cmdModel1 = cmdModelMapper.selectOne(wrapper);
            if (cmdModel1 != null) {
                ProcessDefinition processDefinition1 = repositoryService.createProcessDefinitionQuery().deploymentId(cmdModel1.getDeploymentId()).singleResult();
                if (processDefinition1 != null) {
                    repositoryService.suspendProcessDefinitionById(processDefinition1.getId());
                } else {
                    throw new GlobalException("系统错误：上一版本流程定义不存在");
                }
                cmdModel1.setStatus("2");
                cmdModel1.setUpdateTime(new Date());
                cmdModel1.setUpdateBy(SecurityUtils.getUsername());
                cmdModelMapper.updateById(cmdModel1);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Bpmn.writeModelToStream(outputStream, xml);
            cmdModel.setStatus("1");
            cmdModel.setBpmnXml(outputStream.toString());
            cmdModel.setDeploymentId(processDefinition.getDeploymentId());
            cmdModel.setDeployTime(new Date());
            cmdModel.setUpdateBy(SecurityUtils.getUsername());
            cmdModel.setUpdateTime(new Date());
            return cmdModelMapper.updateById(cmdModel);
        }
    }

    private void validateBpmn(BpmnModelInstance xml) {
        if (xml == null) {
            throw new GlobalException("模型生成失败");
        }
        BpmnUtils.validateBpmn(xml);
    }

    private void validateForm(CmdModel cmdModel) {
        CmdModelForm cmdModelForm = cmdModelFormMapper.selectById(cmdModel.getModelId());
        if (cmdModelForm == null) {
            throw new GlobalException("模型表单关联不存在");
        }
        CmdForm cmdForm = cmdFormMapper.selectById(cmdModelForm.getFormId());
        if (cmdForm == null) {
            throw new GlobalException("模型表单不存在");
        }
    }


}
