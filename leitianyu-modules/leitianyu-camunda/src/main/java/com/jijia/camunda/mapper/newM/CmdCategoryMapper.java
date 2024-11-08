package com.jijia.camunda.mapper.newM;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jijia.camunda.domain.CmdCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * TODO
 *
 * @author Raoqian
 * @company ZhiDao Tech
 * @create 2024/11/5
 */
@Mapper
public interface CmdCategoryMapper extends BaseMapper<CmdCategory>  {

    /**
     * 查询BPM 流程分类列表
     *
     * @param cmdCategory BPM 流程分类
     * @return BPM 流程分类集合
     */
    public List<CmdCategory> selectCmdCategoryList(CmdCategory cmdCategory);


}
