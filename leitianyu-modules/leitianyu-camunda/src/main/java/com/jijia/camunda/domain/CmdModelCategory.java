package com.jijia.camunda.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO
 *
 * @author Raoqian
 * @company ZhiDao Tech
 * @create 2024/11/12
 */
@Data
@TableName(value = "cmd_model_category")
public class CmdModelCategory {

    private Long modelId;

    private Long categoryId;

}
