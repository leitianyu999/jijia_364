package com.jijia.system.api;


import com.jijia.common.core.constant.SecurityConstants;
import com.jijia.common.core.constant.ServiceNameConstants;
import com.jijia.common.core.domain.R;
import com.jijia.system.api.domain.OpDeskDept;
import com.jijia.system.api.domain.SysDept;
import com.jijia.system.api.factory.RemoterDeptFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 * 部门服务
 *
 * @author leitianyu
 */
@FeignClient(contextId = "remoterDeptService", value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoterDeptFallbackFactory.class)
public interface RemoterDeptService {

    /**
     * 查询部门信息
     *
     * @param source 请求来源
     * @return 结果
     */
    @GetMapping("/dept/op/list")
    public R< List<SysDept>> opList(@RequestHeader(SecurityConstants.FROM_SOURCE) String source);


    /**
     * 根据部门编号获取详名称
     *
     * @param source 请求来源
     * @param deptId deptId
     * @return 结果
     */
    @GetMapping("/dept/name")
    public R<List<OpDeskDept>> getInfoName(@RequestBody List<OpDeskDept> deptId , @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
