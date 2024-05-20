package com.jijia.system.api.factory;


import com.jijia.common.core.web.domain.AjaxResult;
import com.jijia.system.api.RemoteConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 配置服务降级处理
 *
 * @author leitianyu
 */
@Component
public class RemoteConfigFallbackFactory implements FallbackFactory<RemoteConfigService>
{

    private static final Logger log = LoggerFactory.getLogger(RemoteConfigFallbackFactory.class);


    @Override
    public RemoteConfigService create(Throwable throwable) {
        log.error("配置服务调用失败:{}", throwable.getMessage());
        return new RemoteConfigService() {
            @Override
            public AjaxResult getConfigKey(String configKey, String source) {
                return AjaxResult.error("获取配置信息:" + throwable.getMessage());
            }
        };
    }
}
