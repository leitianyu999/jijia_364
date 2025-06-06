package com.jijia.camunda.service;

import com.jijia.camunda.domain.CmdCategory;
import com.jijia.camunda.domain.vo.CmdCategoryVo;

import java.util.List;
import java.util.Map;

public interface ICmdCategoryService {

    /**
     * 创建流程分类
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCategory(CmdCategoryVo createReqVO);

    /**
     * 更新流程分类
     *
     * @param updateReqVO 更新信息
     */
    void updateCategory(CmdCategoryVo updateReqVO);

    /**
     * 删除流程分类
     *
     * @param id 编号
     */
    void deleteCategory(Long id);

    /**
     * 获得流程分类
     *
     * @param id 编号
     * @return BPM 流程分类
     */
    CmdCategory getCategory(Long id);

    /**
     * 获得流程分类分页
     *
     * @param pageReqVO 分页查询
     * @return 流程分类分页
     */
    List<CmdCategory> getCategoryPage(CmdCategoryVo pageReqVO);

//    /**
//     * 获得流程分类 Map，基于指定编码
//     *
//     * @param codes 编号数组
//     * @return 流程分类 Map
//     */
//    default Map<String, BpmCategoryDO> getCategoryMap(Collection<String> codes) {
//        return convertMap(getCategoryListByCode(codes), BpmCategoryDO::getCode);
//    }
//
//    /**
//     * 获得流程分类列表，基于指定编码
//     *
//     * @return 流程分类列表
//     */
//    List<BpmCategoryDO> getCategoryListByCode(Collection<String> codes);
//
//    /**
//     * 获得流程分类列表，基于指定状态
//     *
//     * @param status 状态
//     * @return 流程分类列表
//     */
//    List<BpmCategoryDO> getCategoryListByStatus(Integer status);
}
