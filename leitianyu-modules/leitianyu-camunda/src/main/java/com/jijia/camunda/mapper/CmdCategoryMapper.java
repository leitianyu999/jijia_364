package com.jijia.camunda.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jijia.camunda.domain.CmdCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CmdCategoryMapper extends BaseMapper<CmdCategory> {


    /**
     * 查询BPM 流程分类
     *
     * @param categoryId BPM 流程分类主键
     * @return BPM 流程分类
     */
    public CmdCategory selectCmdCategoryByCategoryId(Long categoryId);

    /**
     * 查询BPM 流程分类列表
     *
     * @param cmdCategory BPM 流程分类
     * @return BPM 流程分类集合
     */
    public List<CmdCategory> selectCmdCategoryList(CmdCategory cmdCategory);

    /**
     * 新增BPM 流程分类
     *
     * @param cmdCategory BPM 流程分类
     * @return 结果
     */
    public int insertCmdCategory(CmdCategory cmdCategory);

    /**
     * 修改BPM 流程分类
     *
     * @param cmdCategory BPM 流程分类
     * @return 结果
     */
    public int updateCmdCategory(CmdCategory cmdCategory);

    /**
     * 删除BPM 流程分类
     *
     * @param categoryId BPM 流程分类主键
     * @return 结果
     */
    public int deleteCmdCategoryByCategoryId(Long categoryId);

    /**
     * 批量删除BPM 流程分类
     *
     * @param categoryIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCmdCategoryByCategoryIds(Long[] categoryIds);

}
