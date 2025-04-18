package com.jijia.camunda.service.newS;

import com.jijia.camunda.domain.dto.CmdFormDto;
import com.jijia.camunda.domain.dto.CmdModelDto;
import com.jijia.camunda.domain.vo.CmdCategoryVo;
import com.jijia.camunda.domain.vo.CmdFormVo;
import com.jijia.camunda.domain.vo.CmdModelVo;

import java.util.List;

public interface CmdModelService {

    CmdModelVo getModel(Long id);

    List<CmdModelVo> getModelList(CmdModelDto cmdModelDto);

    int addModel(CmdModelDto cmdModelDto);

    int updateModel(CmdModelDto cmdModelDto);

    int deleteModel(Long id);

    int reployModel(Long modelId);

    CmdModelVo getModelByCode(String code);
}
