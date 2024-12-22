package com.jijia.camunda.constants;

/**
 * TODO
 *
 * @author leitianyu999
 * @create 2024/11/21
 */
public interface CamundaWorkConstants {

    String PROCESS_PREFIX="Camunda:";
    String START_EVENT_ID="startEventNode";
    String END_EVENT_ID="endEventNode";
    String EXPRESSION_CLASS="exUtils.";
    String COMMENT_SPLIT="---";
    Long DEFAULT_NULL_ASSIGNEE=99999999L;
    String DEFAULT_ADMIN_ASSIGNEE="381496";
    String AUTO_REFUSE_STR="autoRefuse";
    String MULTI_BODY="#multiInstanceBody";
}
