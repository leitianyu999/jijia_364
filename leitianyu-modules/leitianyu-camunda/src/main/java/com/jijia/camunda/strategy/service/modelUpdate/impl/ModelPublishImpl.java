package com.jijia.camunda.strategy.service.modelUpdate.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.jijia.camunda.annotation.ModelAnnotation;
import com.jijia.camunda.domain.CmdModel;
import com.jijia.camunda.domain.CmdModelForm;
import com.jijia.camunda.domain.dto.CmdModelDto;
import com.jijia.camunda.domain.enums.ModelType;
import com.jijia.camunda.mapper.newM.CmdModelCateGoryMapper;
import com.jijia.camunda.mapper.newM.CmdModelFormMapper;
import com.jijia.camunda.mapper.newM.CmdModelMapper;
import com.jijia.camunda.strategy.service.modelUpdate.abstractImpl.AbstractModelStrategy;
import com.jijia.common.core.exception.GlobalException;
import com.jijia.common.security.utils.SecurityUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author leitianyu
 */
@Component
@ModelAnnotation(modelUpdateType = ModelType.is_PUBLISH)
public class ModelPublishImpl extends AbstractModelStrategy {

    @Resource
    private CmdModelMapper cmdModelMapper;
    @Resource
    private CmdModelFormMapper cmdModelFormMapper;
    @Resource
    private CmdModelCateGoryMapper cmdModelCateGoryMapper;



    @Override
    public int updateBpmnXml(CmdModelDto cmdModelDto, CmdModel cmdModel) {
        Long modelId = cmdModel.getModelId();

        // 新增未部署版本
        cmdModel.setUpdateBy(SecurityUtils.getUsername());
        cmdModel.setUpdateTime(new Date());
        cmdModel.setModelId(null);
        cmdModel.setVersion(cmdModel.getVersion() + 1);
        cmdModel.setStatus("0");
        int insert = cmdModelMapper.insert(cmdModel);

        insert += insertModelForm(cmdModel, modelId);

        return insert;
    }

    @Override
    public int updateModelForm(CmdModelDto cmdModelDto, CmdModel cmdModel) {
        throw new GlobalException("已发布模型不允许修改表单");
    }

    public int insertModelForm(CmdModel cmdModel, Long modelId) {
        CmdModelForm cmdModelForm = cmdModelFormMapper.selectOne(new LambdaQueryChainWrapper<>(cmdModelFormMapper).eq(CmdModelForm::getModelId, modelId));
        if (cmdModelForm != null) {
            CmdModelForm add = new CmdModelForm();
            add.setModelId(cmdModel.getModelId());
            add.setFormId(cmdModelForm.getFormId());
            return cmdModelFormMapper.insert(add);
        }
        return 0;
    }
}
