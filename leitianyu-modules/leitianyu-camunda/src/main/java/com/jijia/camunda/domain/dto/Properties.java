package com.jijia.camunda.domain.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author leitianyu999
 * @create 2024/11/21
 */
@Data
public class Properties {

    private String assignedType;
    private List<Long> assignedUser;
    //发起人自旋  multiple true false
    private Map<String,Object> selfSelect;
    //连续主管 endCondition TOP   LEAVE    endLevel  level
    private Map<String,Object> leaderTop=new HashMap<>();
    //指定主管审批
    private Map<String,Object> leader=new HashMap<>();
    //系统角色
    private List<Map<String,Object>> role;
    //表单人员
    private String formUser;


    //审批人为空的规则  hander 和 assignedUser
    private Map<String,Object> nobody;
    private String mode;

    private Boolean sign;
    //审批超时
    private JSONObject timeLimit;

    private Map<String,Object> refuse;



    //------------------------------------->
    private String groupsType;
    private String expression;
    private List<GroupsInfo> groups;
    //
    private Boolean shouldAdd;
    //
    private String type;
    private Long time;
    private String unit;
    private String dateTime;


}
