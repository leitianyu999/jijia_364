package com.jijia.camunda.dto;

import com.jijia.camunda.dto.json.UserInfo;
import com.jijia.system.api.model.LoginUser;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author LoveMyOrange
 * @create 2022-10-14 23:47
 */
@Data
@ApiModel("待办 需要返回给前端的VO")
public class TaskDTO extends PageDTO {
    private Long currentUserInfo;
}
