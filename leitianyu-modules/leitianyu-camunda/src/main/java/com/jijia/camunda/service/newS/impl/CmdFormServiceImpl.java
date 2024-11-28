package com.jijia.camunda.service.newS.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jijia.camunda.domain.CmdForm;
import com.jijia.camunda.domain.CmdModel;
import com.jijia.camunda.domain.CmdModelForm;
import com.jijia.camunda.domain.dto.CmdFormDto;
import com.jijia.camunda.domain.vo.CmdFormVo;
import com.jijia.camunda.mapper.newM.CmdFormMapper;
import com.jijia.camunda.mapper.newM.CmdModelFormMapper;
import com.jijia.camunda.mapper.newM.CmdModelMapper;
import com.jijia.camunda.service.newS.CmdFormService;
import com.jijia.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * TODO
 *
 * @author Raoqian
 * @company ZhiDao Tech
 * @create 2024/11/7
 */
@Service
public class CmdFormServiceImpl implements CmdFormService {

    @Autowired
    private CmdFormMapper formMapper;
    @Resource
    private CmdModelFormMapper cmdModelFormMapper;
    @Resource
    private CmdModelMapper cmdModelMapper;


    @Override
    public List<CmdFormVo> getFormList(CmdFormDto cmdFormDto) {
        List<CmdForm> cmdForms = formMapper.selectCmdFormList(BeanUtil.toBean(cmdFormDto, CmdForm.class));
        return BeanUtil.copyToList(cmdForms, CmdFormVo.class);
    }

    @Override
    public CmdFormVo getForm(Long id) {
        CmdForm cmdForm = formMapper.selectById(id);
        return BeanUtil.toBean(cmdForm, CmdFormVo.class);
    }

    @Override
    public int addForm(CmdFormDto cmdFormDto) {

        LambdaQueryWrapper<CmdForm> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CmdForm::getCode, cmdFormDto.getCode());

        Integer i = formMapper.selectCount(queryWrapper);
        if (i > 0) {
            throw new RuntimeException("表单编码已存在");
        }

        return add(cmdFormDto);


    }

    public int add(CmdFormDto cmdFormDto) {
        CmdForm cmdForm = BeanUtil.toBean(cmdFormDto, CmdForm.class);
        cmdForm.setStatus(0);
        cmdForm.setCreateBy(SecurityUtils.getUsername());
        cmdForm.setCreateTime(new Date());
        return formMapper.insert(cmdForm);
    }

    @Override
    public int updateForm(CmdFormDto cmdFormDto) {

        if (checkFormWithUse(cmdFormDto)) {

            CmdForm cmdForm = formMapper.selectById(cmdFormDto.getFormId());
            cmdFormDto.setVersion(cmdForm.getVersion() + 1);


            return add(cmdFormDto);
        }


        CmdForm cmdForm = BeanUtil.toBean(cmdFormDto, CmdForm.class);
        cmdForm.setUpdateBy(SecurityUtils.getUsername());
        cmdForm.setUpdateTime(new Date());
        return formMapper.updateById(cmdForm);
    }

    @Override
    public int deleteForm(Long id) {
        return formMapper.deleteById(id);
    }



    public Boolean checkFormWithUse(CmdFormDto cmdFormDto) {

        LambdaQueryWrapper<CmdModelForm> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CmdModelForm::getFormId, cmdFormDto.getFormId());
        List<CmdModelForm> cmdModelForms = cmdModelFormMapper.selectList(queryWrapper);
        for (CmdModelForm cmdModelForm : cmdModelForms) {
            LambdaQueryWrapper<CmdModel> queryWrapperModel = new LambdaQueryWrapper<>();
            queryWrapperModel.eq(CmdModel::getModelId, cmdModelForm.getModelId())
                    .eq(CmdModel::getStatus, 1);
            CmdModel cmdModel = cmdModelMapper.selectOne(queryWrapperModel);
            if (cmdModel != null) {
                return true;
            }

        }
        return false;
    }
}
