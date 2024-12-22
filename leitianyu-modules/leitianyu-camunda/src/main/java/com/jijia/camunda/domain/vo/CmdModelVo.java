package com.jijia.camunda.domain.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jijia.camunda.domain.BaseDO;
import lombok.*;

import java.util.Date;

/**
 * TODO
 *
 * @author Raoqian
 * @company ZhiDao Tech
 * @create 2024/11/12
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CmdModelVo extends BaseDO {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long modelId;

    /** 名称 */
    private String name;

    /** 分类编号 */
    private Long categoryId;

    /** bianma */
    private String code;

    /** 版本 */
    private Long version;

    /** 流程XML */
    private String bpmnXml;

    /** node */
    private String nodeJsonData;

    /** 流程部署id */
    private String deploymentId;

    /** 表单id */
    private Long formId;


    private String formName;
    private String formVersion;

    /** 状态 */
    private String status;

    /** 描述 */
    private String description;

    /** 主键 */
    private Long deployModelId;

    /** 表单id */
    private Long deployFormId;


    private String deployFormName;
    private String deployFormVersion;

    /** 状态 */
    private String deployStatus;

    /** 描述 */
    private String deployDescription;

    /** 版本 */
    private Long deployVersion;

    /** 部署时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deployTime;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

}
