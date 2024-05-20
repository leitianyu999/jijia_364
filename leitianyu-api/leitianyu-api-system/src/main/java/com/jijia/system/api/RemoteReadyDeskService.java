package com.jijia.system.api;


import com.jijia.common.core.constant.SecurityConstants;
import com.jijia.common.core.constant.ServiceNameConstants;
import com.jijia.common.core.web.domain.AjaxResult;
import com.jijia.system.api.factory.RemoterDeptFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * 台账服务
 *
 * @author leitianyu
 */
@FeignClient(contextId = "remoteReadyDeskService", value = ServiceNameConstants.OP_SERVICE, fallbackFactory = RemoterDeptFallbackFactory.class)
public interface RemoteReadyDeskService {



    /**
     * 定时任务调取未来日期台账添加
     *
     * @param source 请求来源
     * @return 结果
     */
    @PutMapping("/ready/insert")
    public AjaxResult addReadyToDesk(@RequestHeader(SecurityConstants.FROM_SOURCE) String source);


}
