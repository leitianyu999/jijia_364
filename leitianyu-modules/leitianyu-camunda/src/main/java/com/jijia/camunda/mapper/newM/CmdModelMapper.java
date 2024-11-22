package com.jijia.camunda.mapper.newM;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jijia.camunda.domain.CmdCategory;
import com.jijia.camunda.domain.CmdModel;
import com.jijia.camunda.domain.dto.CmdModelDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CmdModelMapper extends BaseMapper<CmdModel> {


    /**
     * 查询列表
     *
     * @param cmdModel 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<CmdModel> selectCmdModelList(CmdModel cmdModel);


}
