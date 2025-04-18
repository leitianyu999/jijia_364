package com.jijia.camunda.domain.vo;

import com.jijia.camunda.domain.BaseDO;
import com.jijia.system.api.domain.SysDept;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author Raoqian
 * @company ZhiDao Tech
 * @create 2024/11/5
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CmdCategoryVo extends BaseDO {


    private static final long serialVersionUID = 1L;

    /** 分类编号 */
    private Long categoryId;

    /** 菜单ID */
    private Long parentId;

    /** 祖级列表 */
    private String ancestors;

    /** 分类名 */
    private String name;

    /** 分类标志 */
    private String code;

    /** 分类描述 */
    private String description;

    /** 分类状态 */
    private Integer status;

    /** 分类排序 */
    private Long sort;

    /** 模型标识 */
    private String modelCode;

    /** 模型名称 */
    private String modelName;

    /** 模型Id */
    private Long modelId;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 子部门 */
    private List<CmdCategoryVo> children = new ArrayList<CmdCategoryVo>();

}
