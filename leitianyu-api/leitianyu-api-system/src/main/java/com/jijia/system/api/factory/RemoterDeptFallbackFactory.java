package com.jijia.system.api.factory;


import com.jijia.common.core.domain.R;
import com.jijia.system.api.RemoterDeptService;
import com.jijia.system.api.domain.OpDeskDept;
import com.jijia.system.api.domain.SysDept;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 部门服务降级处理
 *
 * @author leitianyu
 */
@Component
public class RemoterDeptFallbackFactory implements FallbackFactory<RemoterDeptService> {
    private static final Logger log = LoggerFactory.getLogger(RemoterDeptFallbackFactory.class);


    @Override
    public RemoterDeptService create(Throwable throwable) {
        log.error("部门服务调用失败:{}", throwable.getMessage());
        return new RemoterDeptService() {
            @Override
            public R< List<SysDept>> opList(String source) {
                return R.fail("获取部门列表失败:" + throwable.getMessage());
            }

            @Override
            public R<List<OpDeskDept>> getInfoName(List<OpDeskDept> deptId, String source) {
                return R.fail("获取部门名称失败:" + throwable.getMessage());
            }
        };

    }
}
