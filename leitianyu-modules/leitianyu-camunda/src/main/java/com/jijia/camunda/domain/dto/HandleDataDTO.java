package com.jijia.camunda.domain.dto;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author LoveMyOrange
 * @create 2022-10-15 16:27
 */
@Data
@ApiModel("各个按钮 处理数据需要传递的参数")
public class HandleDataDTO {
    @ApiModelProperty("任务id")
    private String taskId;
    @ApiModelProperty("流程实例id")
    private String processInstanceId;
    @ApiModelProperty("表单数据")
    private JSONObject formData;
//    @ApiModelProperty("附件")
//    private List attachments;
    @ApiModelProperty("意见")
    private String comments;
    @ApiModelProperty("签名信息")
    private String signInfo;
    @ApiModelProperty("转办用户信息")
    private Long transferUserId;
    @ApiModelProperty("加签用户信息")
    private Long multiAddUserId;
    @ApiModelProperty("退回节点id")
    private String rollbackId;
    @ApiModelProperty("委派的人")
    private Long delegateUserId;

    private String handleType;

    // 减签id
    private List<String> executionIds;
}
