package com.jijia.job.task;

import com.jijia.common.core.constant.HttpStatus;
import com.jijia.common.core.constant.SecurityConstants;
import com.jijia.common.core.exception.job.TaskException;
import com.jijia.common.core.utils.SpringUtils;
import com.jijia.common.core.web.domain.AjaxResult;
import com.jijia.system.api.RemoteReadyDeskService;
import com.jijia.system.api.factory.RemoteUserFallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.jijia.common.core.utils.StringUtils;

/**
 * 定时任务调度测试
 * 
 * @author leitianyu
 */
@Component("ryTask")
public class RyTask
{
    private static final Logger log = LoggerFactory.getLogger(RemoteUserFallbackFactory.class);

    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i)
    {
        System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void ryParams(String params)
    {
        System.out.println("执行有参方法：" + params);
    }

    public void ryNoParams()
    {
        System.out.println("执行无参方法");
    }

    public void addReadyDeskToDesk() throws TaskException {
        AjaxResult ajaxResult = SpringUtils.getBean(RemoteReadyDeskService.class).addReadyToDesk(SecurityConstants.INNER);
        if (ajaxResult.get(AjaxResult.CODE_TAG).equals(HttpStatus.WARN)) {
            throw new TaskException("今日台账无数据", TaskException.Code.TASK_EXISTS);
        }
        if (ajaxResult.isError()) {
            throw new TaskException("台账导入失败", TaskException.Code.UNKNOWN);
        }
    }
}
