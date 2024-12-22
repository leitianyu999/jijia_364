package com.jijia.system.api;

import com.jijia.common.core.constant.SecurityConstants;
import com.jijia.common.core.constant.ServiceNameConstants;
import com.jijia.common.core.domain.R;
import com.jijia.system.api.factory.RemotePostFallbackFactory;
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
@FeignClient(contextId = "remotePostService", value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemotePostFallbackFactory.class)
public interface RemotePostService {
    /**
     * 通过岗位id查询用户信息
     *
     * @param source   请求来源
     * @return 结果
     */
    @GetMapping("/post/getPostUser/{postId}")
    public R<List<Long>> getPostUser(@PathVariable("postId") Long postId, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);


}
