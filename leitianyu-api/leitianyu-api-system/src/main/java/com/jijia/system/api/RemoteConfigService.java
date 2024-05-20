package com.jijia.system.api;


import com.jijia.common.core.constant.SecurityConstants;
import com.jijia.common.core.constant.ServiceNameConstants;
import com.jijia.common.core.web.domain.AjaxResult;
import com.jijia.system.api.factory.RemoteConfigFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * 配置服务
 *
 * @author leitianyu
 */
@FeignClient(contextId = "remoteConfigService", value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteConfigFallbackFactory.class)
public interface RemoteConfigService
{



    /**
     * 通过用户名查询用户信息
     *
     * @param configKey 健名
     * @param source 请求来源
     * @return 结果
     */
    @GetMapping("/config/configKey/{configKey}")
    public AjaxResult getConfigKey(@PathVariable("configKey") String configKey, @RequestHeader(SecurityConstants.INNER) String source);


}
