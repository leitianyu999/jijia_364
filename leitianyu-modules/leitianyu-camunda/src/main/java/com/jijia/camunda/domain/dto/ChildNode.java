package com.jijia.camunda.domain.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.List;

/**
 * TODO
 *
 * @author leitianyu999
 * @create 2024/11/21
 */
@Data
public class ChildNode {

    private String id;
    private String parentId;
    private String type;
    private String name;
    private String desc;
    private Properties props;
    private ChildNode children;
    private List<ChildNode> branchs;
    private String parallelStr;
    private JSONObject incoming=new JSONObject();
    private Boolean typeElse;
    private JSONObject config;

}
