package com.jijia.camunda.utils;

import com.alibaba.fastjson.JSONObject;
import com.jijia.camunda.constants.CamundaConfigConstants;
import com.jijia.camunda.constants.CamundaWorkConstants;
import com.jijia.camunda.constants.CommonConstants;
import com.jijia.camunda.domain.dto.Properties;
import com.jijia.common.core.constant.SecurityConstants;
import com.jijia.common.core.domain.R;
import com.jijia.common.core.web.domain.AjaxResult;
import com.jijia.system.api.RemoteConfigService;
import com.jijia.system.api.RemotePostService;
import com.jijia.system.api.RemoteRoleService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.jijia.camunda.constants.CamundaWorkConstants.DEFAULT_NULL_ASSIGNEE;

/**
 * TODO
 *
 * @author leitianyu999
 * @create 2024/12/19
 */
@Component
public class AssigneeTypeUtils {

    @Resource
    private RemoteRoleService remoteRoleService;
    @Resource
    private RemotePostService remotePostService;
    @Resource
    private RemoteConfigService remoteConfigService;


    // 获取指定用户
    public void getAssignUser(Set<Long> assigneeList, Properties props) {
        assigneeList.addAll(props.getAssignedUser());
    }

    @SuppressWarnings("unchecked")
    public void getAssignUserBySelfSelect(Set<Long> assigneeList, DelegateExecution execution, String currentActivityId) {
        List<Long> assigneeUsers = (List<Long>) execution.getVariable(currentActivityId);
        if (assigneeUsers != null) {
            assigneeList.addAll(assigneeUsers);
        }
    }

    public void getAssignUserByRole(Set<Long> assigneeList, Properties props) {
        List<Map<String, Object>> role = props.getRole();
        role.forEach(map -> {
            Long roleId = (Long) map.get("key");
            R<List<Long>> authUser = remoteRoleService.getAuthUser(roleId, SecurityConstants.INNER);
            if (R.isSuccess(authUser)) {
                assigneeList.addAll(authUser.getData());
            } else {
                throw new RuntimeException("获取角色用户失败");
            }
        });
    }

    public void getAssignUserByPost(Set<Long> assigneeList, Properties props) {
        List<Map<String, Object>> post = props.getPost();
        post.forEach(map -> {
            Long postId = (Long) map.get("key");
            R<List<Long>> authUser = remotePostService.getPostUser(postId, SecurityConstants.INNER);
            if (R.isSuccess(authUser)) {
                assigneeList.addAll(authUser.getData());
            } else {
                throw new RuntimeException("获取岗位用户失败");
            }
        });
    }

    public void getAssignUserBySelf(Set<Long> assigneeList, DelegateExecution execution) {

        Long applyUserId = (Long) execution.getVariable(CommonConstants.APPLY_USER_ID);
        assigneeList.add(applyUserId);
    }

    public void getNotAssignUserByPass(Set<Long> assigneeList, DelegateExecution execution, String variable) {
        assigneeList.add(CamundaWorkConstants.DEFAULT_NULL_ASSIGNEE);
        execution.setVariable(variable, assigneeList);
    }

    public void getNotAssignUserByRefuse(Set<Long> assigneeList, DelegateExecution execution, String variable) {
        execution.setVariable("autoRefuse", Boolean.TRUE);
        assigneeList.add(CamundaWorkConstants.DEFAULT_NULL_ASSIGNEE);
        execution.setVariable(variable, assigneeList);
    }

    public void getNotAssignUserByAdmin(Set<Long> assigneeList, DelegateExecution execution, String variable) {
//        assigneeList.add(DEFAULT_ADMIN_ASSIGNEE);
        AjaxResult configKey = remoteConfigService.getConfigKey(CamundaConfigConstants.CAMUNDA_Admin_ASSIGNEE, SecurityConstants.INNER);
        if (!configKey.isSuccess()) {
            throw new RuntimeException("获取管理员配置失败");
        }
        assigneeList.add(Long.parseLong(configKey.get(AjaxResult.MSG_TAG).toString()));
        execution.setVariable(variable, assigneeList);
    }

    @SuppressWarnings("unchecked")
    public void getNotAssignUserByUser(Set<Long> assigneeList, DelegateExecution execution, Map<String, Object> nobody, String variable) {
        Object assignedUserObj = nobody.get("assignedUser");
        if (assignedUserObj != null) {
            List<Long> assignedUser = (List<Long>) assignedUserObj;
            if (!assignedUser.isEmpty()) {
                assigneeList.addAll(assignedUser);
                execution.setVariable(variable, assigneeList);
            } else {
                getNotAssignUserByPass(assigneeList, execution, variable);
            }

        }
    }


}
