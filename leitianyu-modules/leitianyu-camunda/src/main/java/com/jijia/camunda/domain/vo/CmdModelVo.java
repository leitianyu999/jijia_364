package com.jijia.camunda.domain.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jijia.camunda.domain.BaseDO;
import lombok.*;

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

    /** 流程部署id */
    private String deploymentId;

    /** 表单id */
    private Long formId;


    private String formName;

    /** 状态 */
    private String status;

    /** 描述 */
    private String description;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

}
