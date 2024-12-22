package com.jijia.system.api;

import com.jijia.common.core.constant.SecurityConstants;
import com.jijia.common.core.constant.ServiceNameConstants;
import com.jijia.common.core.domain.R;
import com.jijia.system.api.factory.RemoteRoleFallbackFactory;
import com.jijia.system.api.model.LoginUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 * 用户服务
 *
 * @author leitianyu
 */
@FeignClient(contextId = "remoteRoleService", value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteRoleFallbackFactory.class)
public interface RemoteRoleService {
    /**
     * 通过角色id查询用户信息
     *
     * @param source   请求来源
     * @return 结果
     */
    @GetMapping("/role/authUser/getAuthUser/{roleId}")
    public R<List<Long>> getAuthUser(@PathVariable("roleId") Long roleId, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);


}
