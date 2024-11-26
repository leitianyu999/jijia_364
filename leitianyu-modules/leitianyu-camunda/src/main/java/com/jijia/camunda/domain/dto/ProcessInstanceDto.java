package com.jijia.camunda.domain.dto;

import com.alibaba.fastjson.JSONObject;
import com.jijia.camunda.domain.dto.vo.UserInfo;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author LoveMyOrange
 * @create 2022-10-14 23:27
 */
@Data
public class ProcessInstanceDto {

    // 流程定义id
    private String processDefinitionId;

    // 表单数据
    private JSONObject formData;

    // 自选信息
    private Map<String, List<UserInfo>> processUsers;
}
