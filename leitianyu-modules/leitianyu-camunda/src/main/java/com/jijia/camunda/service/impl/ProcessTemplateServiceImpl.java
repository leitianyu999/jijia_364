package com.jijia.camunda.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jijia.camunda.entity.ProcessTemplates;
import com.jijia.camunda.mapper.ProcessTemplatesMapper;
import com.jijia.camunda.service.ProcessTemplateService;
import org.springframework.stereotype.Service;

/**
 * @author : willian fu
 * @version : 1.0
 */
@Service
public class ProcessTemplateServiceImpl extends ServiceImpl<ProcessTemplatesMapper, ProcessTemplates>  implements
        ProcessTemplateService {

}
