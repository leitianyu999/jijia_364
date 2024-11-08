package com.jijia.camunda.service.newS;

import com.jijia.camunda.domain.dto.CmdCategoryDto;
import com.jijia.camunda.domain.dto.CmdFormDto;
import com.jijia.camunda.domain.vo.CmdCategoryVo;
import com.jijia.camunda.domain.vo.CmdFormVo;

import java.util.List;

public interface CmdFormService {

    CmdFormVo getForm(Long id);

    List<CmdFormVo> getFormList(CmdFormDto cmdFormDto);

    int addForm(CmdFormDto cmdFormDto);

    int updateForm(CmdFormDto cmdFormDto);

    int deleteForm(Long id);

}
