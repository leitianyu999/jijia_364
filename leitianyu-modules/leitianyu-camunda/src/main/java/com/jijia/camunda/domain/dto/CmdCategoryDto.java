package com.jijia.camunda.domain.dto;

import com.jijia.camunda.domain.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
