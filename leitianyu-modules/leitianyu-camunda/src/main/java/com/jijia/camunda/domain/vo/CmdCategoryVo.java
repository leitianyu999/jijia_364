package com.jijia.camunda.domain.vo;

import lombok.Data;

@Data
public class CmdCategoryVo {

    private Long id;

    private String name;

    private String description;

    private String code;

    private Integer status;

    private Integer sort;

}
