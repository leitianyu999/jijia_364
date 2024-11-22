package com.jijia.camunda.domain.dto;

import com.jijia.camunda.domain.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class CmdCategoryDto extends BaseDO {


    private static final long serialVersionUID = 1L;

    /** 分类编号 */
    private Long categoryId;

    /** 父菜单ID */
    private Long parentId;

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

    /** 组件路径 */
    private String component;

    /** 路由参数 */
    private String query;

    /** 是否为外链（0是 1否） */
    private String isFrame;

    /** 是否缓存（0缓存 1不缓存） */
    private String isCache;

    /** 类型（M目录 C菜单 F按钮） */
    private String menuType;

    /** 显示状态（0显示 1隐藏） */
    private String visible;

    /** 菜单状态（0正常 1停用） */
    private String MenuStatus;

    /** 权限字符串 */
    private String perms;

    /** 菜单图标 */
    private String icon;


    private Long ModelId;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

}
