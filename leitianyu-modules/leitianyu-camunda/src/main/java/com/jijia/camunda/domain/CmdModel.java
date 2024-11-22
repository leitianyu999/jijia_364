package com.jijia.camunda.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "cmd_model")
public class CmdModel extends BaseDO{

    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId
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

    /** 状态 */
    private String status;

    /** 描述 */
    private String description;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

}
