package com.jijia.system.api.factory;

import com.jijia.common.core.domain.R;
import com.jijia.system.api.RemotePostService;
import com.jijia.system.api.RemoteRoleService;
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
public class RemotePostFallbackFactory implements FallbackFactory<RemotePostService>
{
    private static final Logger log = LoggerFactory.getLogger(RemotePostFallbackFactory.class);


    @Override
    public RemotePostService create(Throwable throwable) {
        log.error("岗位服务调用失败:{}", throwable.getMessage());
        return new RemotePostService() {
            @Override
            public R<List<Long>> getPostUser(Long postId, String source) {
                return R.fail("获取岗位失败:" + throwable.getMessage());
            }
        };
    }
}
