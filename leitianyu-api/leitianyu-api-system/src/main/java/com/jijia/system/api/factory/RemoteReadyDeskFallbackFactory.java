package com.jijia.system.api.factory;

import com.jijia.common.core.web.domain.AjaxResult;
import com.jijia.system.api.RemoteReadyDeskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * @author leitianyu
 */
public class RemoteReadyDeskFallbackFactory implements FallbackFactory<RemoteReadyDeskService> {

    private static final Logger log = LoggerFactory.getLogger(RemoteUserFallbackFactory.class);

    @Override
    public RemoteReadyDeskService create(Throwable cause) {
        log.error("台账服务调用失败:{}", cause.getMessage());
        return new RemoteReadyDeskService() {

            @Override
            public AjaxResult addReadyToDesk(String source) {
                return AjaxResult.error("addReadyToDesk调取失败");
            }
        };
    }
}
