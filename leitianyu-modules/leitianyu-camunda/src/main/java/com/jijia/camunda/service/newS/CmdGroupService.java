package com.jijia.camunda.service.newS;

import com.jijia.camunda.domain.dto.CmdCategoryDto;
import com.jijia.camunda.domain.vo.CmdCategoryVo;

import java.util.List;

public interface CmdGroupService {

    CmdCategoryVo getGroup(Long id);

    List<CmdCategoryVo> getGroupList(CmdCategoryDto categoryDto);

    int addGroup(CmdCategoryDto categoryDto);

    int updateGroup(CmdCategoryDto categoryDto);

    int deleteGroup(Long id);

}
