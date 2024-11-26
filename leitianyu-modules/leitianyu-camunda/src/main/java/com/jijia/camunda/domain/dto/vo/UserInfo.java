package com.jijia.camunda.domain.dto.vo;

import lombok.Data;

/**
 * @Author:LoveMyOrange
 * @Description:
 * @Date:Created in 2022/10/9 16:10
 */
@Data
public class UserInfo {
  private String id;
  private String type;
  private String sex;
  private Boolean selected;
}
