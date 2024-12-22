package com.jijia.system.api.factory;

import com.jijia.common.core.domain.R;
import com.jijia.system.api.RemoteRoleService;
import com.jijia.system.api.RemoteUserService;
import com.jijia.system.api.domain.SysUser;
import com.jijia.system.api.model.LoginUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户服务降级处理
 * 
 * @author leitianyu
 */
@Component
public class RemoteRoleFallbackFactory implements FallbackFactory<RemoteRoleService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteRoleFallbackFactory.class);


    @Override
    public RemoteRoleService create(Throwable throwable) {
        log.error("角色服务调用失败:{}", throwable.getMessage());
        return new RemoteRoleService() {
            @Override
            public R<List<Long>> getAuthUser(Long roleId, String source) {
                return R.fail("获取角色失败:" + throwable.getMessage());
            }
        };
    }
}
