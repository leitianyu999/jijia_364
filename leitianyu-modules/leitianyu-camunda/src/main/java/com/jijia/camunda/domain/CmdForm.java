package com.jijia.camunda.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.json.JSONObject;

import javax.json.Json;
import java.util.List;

/**
 * TODO
 *
 * @author Raoqian
 * @company ZhiDao Tech
 * @create 2024/11/7
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "cmd_form")
public class CmdForm extends BaseDO {

    private static final long serialVersionUID = 1L;

    /** 编号 */
    @TableId(type = IdType.AUTO)
    private Long formId;

    /** 表单名 */

    private String name;


    /** 标识 */
    private String code;

    /** 开启状态 */
    private Integer status;

    /** 表单的配置 */
    private String remark;

    /** 表单的配置 */
    private String formConfig;

    /** 表单项的数组 */
    private String widgetList;

    /** 版本 */
    private Integer version;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

}
