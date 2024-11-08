package com.jijia.camunda.service.newS.impl;

import cn.hutool.core.bean.BeanUtil;
import com.jijia.camunda.domain.CmdForm;
import com.jijia.camunda.domain.dto.CmdFormDto;
import com.jijia.camunda.domain.vo.CmdFormVo;
import com.jijia.camunda.mapper.newM.CmdFormMapper;
import com.jijia.camunda.service.newS.CmdFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        CmdForm cmdForm = BeanUtil.toBean(cmdFormDto, CmdForm.class);
        return formMapper.insert(cmdForm);
    }

    @Override
    public int updateForm(CmdFormDto cmdFormDto) {
        CmdForm cmdForm = BeanUtil.toBean(cmdFormDto, CmdForm.class);
        return formMapper.updateById(cmdForm);
    }

    @Override
    public int deleteForm(Long id) {
        return formMapper.deleteById(id);
    }
}
