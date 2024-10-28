package com.jijia.camunda.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jijia.common.core.annotation.Excel;
import com.jijia.common.core.web.domain.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@TableName("cmd_category")
public class CmdCategory extends BaseMpEntity {

    private static final long serialVersionUID = 1L;

    /** 分类编号 */
    @TableId(value = "category_id", type = IdType.AUTO)
    private Long categoryId;

    /** 分类名 */
    @Excel(name = "分类名")
    private String name;

    /** 分类标志 */
    @Excel(name = "分类标志")
    private String code;

    /** 分类描述 */
    @Excel(name = "分类描述")
    private String description;

    /** 分类状态 */
    @Excel(name = "分类状态")
    private Integer status;

    /** 分类排序 */
    @Excel(name = "分类排序")
    private Long sort;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

}
