package com.jijia.camunda.strategy.service.modelUpdate.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.jijia.camunda.annotation.ModelAnnotation;
import com.jijia.camunda.domain.CmdModel;
import com.jijia.camunda.domain.CmdModelForm;
import com.jijia.camunda.domain.dto.CmdModelDto;
import com.jijia.camunda.domain.enums.ModelType;
import com.jijia.camunda.mapper.newM.CmdModelFormMapper;
import com.jijia.camunda.mapper.newM.CmdModelMapper;
import com.jijia.camunda.strategy.service.modelUpdate.abstractImpl.AbstractModelStrategy;
import com.jijia.camunda.utils.BpmnUtils;
import com.jijia.common.security.utils.SecurityUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author leitianyu
 */
@Component
@ModelAnnotation(modelUpdateType = ModelType.is_NOT_PUBLISH)
public class ModelUnreleasedImpl extends AbstractModelStrategy {

    @Resource
    private CmdModelMapper cmdModelMapper;
    @Resource
    private CmdModelFormMapper cmdModelFormMapper;


    public int updateBpmnXml(CmdModelDto cmdModelDto, CmdModel cmdModel) {

        cmdModel.setNodeJsonData(cmdModelDto.getNodeJsonData());
        cmdModel.setUpdateBy(SecurityUtils.getUsername());
        cmdModel.setUpdateTime(new Date());
        return cmdModelMapper.updateById(cmdModel);
    }

    @Override
    public int updateModelForm(CmdModelDto cmdModelDto, CmdModel cmdModel) {
        LambdaQueryWrapper<CmdModelForm> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CmdModelForm::getModelId, cmdModel.getModelId());
        CmdModelForm cmdModelForm = cmdModelFormMapper.selectOne(queryWrapper);
        if (cmdModelForm != null) {
            if (cmdModel.getNodeJsonData() != null) {
                cmdModel.setNodeJsonData(null);
                cmdModel.setUpdateBy(SecurityUtils.getUsername());
                cmdModel.setUpdateTime(new Date());
                cmdModelMapper.updateById(cmdModel);
            }
            cmdModelForm.setFormId(cmdModelDto.getFormId());
            return cmdModelFormMapper.updateById(cmdModelForm);
        } else {
            CmdModelForm add = new CmdModelForm();
            add.setModelId(cmdModel.getModelId());
            add.setFormId(cmdModelDto.getFormId());
            return cmdModelFormMapper.insert(add);
        }
    }

    @Override
    public int updateModelStatus(CmdModelDto cmdModelDto, CmdModel cmdModel) {
        throw new RuntimeException("未发布模型不允许修改状态");
    }


}
