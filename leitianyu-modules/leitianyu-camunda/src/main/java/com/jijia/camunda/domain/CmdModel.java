package com.jijia.camunda.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "cmd_model")
public class CmdModel extends BaseDO{

    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(type = IdType.AUTO)
    private Long modelId;

    /** 名称 */
    private String name;

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

    /** 状态 */
    private String status;

    /** 描述 */
    private String description;

    /** 部署时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deployTime;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

}
