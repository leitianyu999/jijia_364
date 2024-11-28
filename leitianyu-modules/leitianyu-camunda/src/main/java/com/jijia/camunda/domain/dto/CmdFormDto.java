package com.jijia.camunda.domain.dto;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jijia.camunda.domain.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.alibaba.fastjson.JSONObject;

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
public class CmdFormDto extends BaseDO {

    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long formId;

    /** 表单名 */
    private String name;

    // 标识
    private String code;

    // 解释
    private String remark;

    /** 开启状态 */
    private Integer status;

    /** 表单的配置 */
    private JSONObject formConfig;

    /** 表单项的数组 */
    private List<JSONObject> widgetList;

    /** 版本 */
    private Integer version;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

}
