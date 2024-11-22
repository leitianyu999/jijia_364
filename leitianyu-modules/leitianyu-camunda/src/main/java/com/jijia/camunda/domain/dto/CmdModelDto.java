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
 * @create 2024/11/12
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CmdModelDto extends BaseDO {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long modelId;

    /** 名称 */
    private String name;

    /** bianma */
    private String key;

    /** 版本 */
    private Long version;

    /** 流程XML */
    private String bpmnXml;

    /** 流程部署id */
    private String deploymentId;

    /** 分类编号 */
    private Long categoryId;

    /** 表单id */
    private Long formId;

    /** 描述 */
    private String description;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    // BpmnModel
    private ChildNode childNode;

    // 修改类型
    private String updateType;

    // version=1的节点id
    private Long OilId;



}
