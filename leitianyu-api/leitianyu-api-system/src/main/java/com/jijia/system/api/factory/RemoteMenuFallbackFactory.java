package com.jijia.system.api.factory;

import com.jijia.common.core.domain.R;
import com.jijia.common.core.web.domain.AjaxResult;
import com.jijia.system.api.RemoteFileService;
import com.jijia.system.api.RemoteMenuService;
import com.jijia.system.api.domain.SysFile;
import com.jijia.system.api.domain.SysMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件服务降级处理
 * 
 * @author leitianyu
 */
@Component
public class RemoteMenuFallbackFactory implements FallbackFactory<RemoteMenuService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteMenuFallbackFactory.class);

    @Override
    public RemoteMenuService create(Throwable throwable)
    {
        log.error("菜单服务调用失败:{}", throwable.getMessage());
        return new RemoteMenuService() {
            @Override
            public AjaxResult add(SysMenu menu) {
                return AjaxResult.error("add调取失败");
            }
        };
    }
}
