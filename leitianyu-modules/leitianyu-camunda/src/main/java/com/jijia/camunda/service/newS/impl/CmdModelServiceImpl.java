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
import com.jijia.common.core.exception.GlobalException;
import com.jijia.common.security.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Override
    public CmdFormVo getModel(Long id) {
        return BeanUtil.toBean(cmdModelMapper.selectById(id), CmdFormVo.class);
    }

    @Override
    public List<CmdModelVo> getModelList(CmdModelDto cmdModelDto) {
        List<CmdModelVo> cmdModelVos = BeanUtil.copyToList(cmdModelMapper.selectCmdModelList(BeanUtil.toBean(cmdModelDto, CmdModel.class)), CmdModelVo.class);

        cmdModelVos.forEach(cmdModelVo -> {
            CmdModelForm cmdModelForm = cmdModelFormMapper.selectById(cmdModelVo.getFormId());
            if (cmdModelForm != null) {
                CmdForm cmdForm = cmdFormMapper.selectById(cmdModelForm.getFormId());
                cmdModelVo.setFormName(cmdForm.getName());
                cmdModelVo.setFormId(cmdForm.getFormId());
            }
        });

        return cmdModelVos;
    }

    @Override
    public int addModel(CmdModelDto cmdModelDto) {
        CmdModel model = BeanUtil.toBean(cmdModelDto, CmdModel.class);
        if (model.getVersion() == null || model.getVersion() != 1) {
            model.setModelId(1L);
        }
        model.setCreateBy(SecurityUtils.getUsername());
        model.setCreateTime(new Date());
        return cmdModelMapper.insert(model);
    }

    @Override
    public int updateModel(CmdModelDto cmdModelDto) {

        CmdModel cmdModel = cmdModelMapper.selectById(cmdModelDto.getModelId());
        if (cmdModel == null) {
            throw new GlobalException("模型不存在");
        }

        ModelStrategy instance;

        if (cmdModel.getStatus().equals("1")) {
            instance = HandlerModelContext.getInstance(ModelType.is_PUBLISH);
        } else {
            instance = HandlerModelContext.getInstance(ModelType.is_NOT_PUBLISH);
        }

        return instance.updateModel(cmdModelDto, cmdModel);
    }

    @Override
    public int deleteModel(Long id) {
        return cmdModelMapper.deleteById(id);
    }


}
