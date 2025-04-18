package com.jijia.camunda.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * TODO
 *
 * @author Raoqian
 * @company ZhiDao Tech
 * @create 2024/11/5
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "cmd_category")
public class CmdCategory extends BaseDO {


    private static final long serialVersionUID = 1L;

    /** 分类编号 */
    @TableId(type = IdType.AUTO)
    private Long categoryId;

    /** 菜单ID */
    private Long menuId;

    /** 父菜单ID */
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

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;
}
