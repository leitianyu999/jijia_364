package com.jijia.camunda.utils;

/**
 * @author LoveMyOrange
 * @create 2022-10-10 17:40
 */
public interface WorkFlowConstants {
    String PROCESS_PREFIX="Camunda";
    String START_EVENT_ID="startEventNode";
    String END_EVENT_ID="endEventNode";
    String EXPRESSION_CLASS="exUtils.";
    String COMMENT_SPLIT="---";
    String DEFAULT_NULL_ASSIGNEE="100000000000";
    String DEFAULT_ADMIN_ASSIGNEE="381496";
    String AUTO_REFUSE_STR="autoRefuse";
    String MULTI_BODY="#multiInstanceBody";
    String MULTI_LIST="AssigneeList";
}
