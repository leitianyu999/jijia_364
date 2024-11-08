package com.jijia.camunda.service.impl;

import com.jijia.camunda.service.SettingService;
import com.jijia.camunda.service.WorkspaceProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author : willian fu
 * @version : 1.0
 */
@Service
public class WorkspaceProcessServiceImpl implements WorkspaceProcessService {

    @Autowired
    private SettingService settingService;

    /**
     * 获取用户可见表单
     *
     * @return
     */
    @Override
    public Object getFormGroups(String name) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
            .getRequestAttributes();
//        String token = CookieUtil.getCookieValue(servletRequestAttributes.getRequest(), "token");

        return null;
    }
}
