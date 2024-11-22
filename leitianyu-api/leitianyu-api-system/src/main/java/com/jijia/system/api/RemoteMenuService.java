package com.jijia.system.api;

import com.jijia.common.core.constant.ServiceNameConstants;
import com.jijia.common.core.domain.R;
import com.jijia.common.core.web.domain.AjaxResult;
import com.jijia.system.api.domain.SysFile;
import com.jijia.system.api.domain.SysMenu;
import com.jijia.system.api.factory.RemoteFileFallbackFactory;
import com.jijia.system.api.factory.RemoteMenuFallbackFactory;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * 菜单服务
 * 
 * @author leitianyu
 */
@FeignClient(contextId = "remoteMenuService", value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteMenuFallbackFactory.class, configuration = EncoderConfiguration.class)
public interface RemoteMenuService
{
    /**
     * 新增菜单
     *
     * @param menu 菜单信息
     * @return 结果
     */
    @PostMapping(value = "/menu")
    public AjaxResult add(@RequestBody SysMenu menu);



}

class EncoderConfiguration {
    @Bean
    Encoder feignFormEncoder(ObjectFactory<HttpMessageConverters> converters) {
        return new SpringFormEncoder(new SpringEncoder(converters));
    }
}
