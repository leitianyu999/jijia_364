package com.jijia.camunda.domain.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jijia.camunda.domain.BaseDO;
import lombok.*;

/**
 * TODO
 *
 * @author Raoqian
 * @company ZhiDao Tech
 * @create 2024/11/7
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CmdFormVo extends BaseDO {

    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long formId;

    /** 表单名 */
    private String name;

    /** 开启状态 */
    private Integer status;

    /** 表单的配置 */
    private String formConfig;

    /** 表单项的数组 */
    private String widgetList;

    /** 版本 */
    private Integer version;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

}
