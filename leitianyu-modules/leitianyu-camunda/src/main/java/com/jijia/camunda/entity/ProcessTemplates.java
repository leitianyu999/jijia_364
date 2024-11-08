package com.jijia.camunda.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (ProcessTemplates)实体类
 *
 * @author makejava
 * @since 2020-09-21 22:50:29
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "process_templates")
@Builder
public class ProcessTemplates implements Serializable {
    private static final long serialVersionUID = -95829441258242072L;

    @TableId
    /**
    * 审批摸板ID
    */
    private String templateId;
    @TableField(exist = false)
    private String formId;
    @TableField(exist = false)
    private String formName;
    /**
    * 摸板名称
    */
    private String templateName;

    private Integer groupId;
    /**
    * 摸板表单
    */
    private String formItems;

    private String settings;

    private String process;

    private String remark;
    /**
    * 是否已停用
    */
    private Boolean isStop;
    /**
    * 创建时间
    */
    private Date created;
    /**
    * 更新时间
    */
    private Date updated;
    @TableField(exist = false)
    private String logo;
    @TableField(exist = false)
    private String processDefinitionId;
}
