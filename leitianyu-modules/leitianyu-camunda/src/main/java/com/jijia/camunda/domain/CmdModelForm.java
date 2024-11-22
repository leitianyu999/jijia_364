package com.jijia.camunda.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * TODO
 *
 * @author Raoqian
 * @company ZhiDao Tech
 * @create 2024/11/12
 */
@Data
@TableName(value = "cmd_model_form")
public class CmdModelForm {

    @TableId
    private Long modelId;

    private Long formId;

}
