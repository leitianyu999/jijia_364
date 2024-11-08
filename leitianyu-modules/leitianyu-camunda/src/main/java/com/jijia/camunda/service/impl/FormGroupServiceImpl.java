package com.jijia.camunda.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jijia.camunda.entity.FormGroups;
import com.jijia.camunda.mapper.FormGroupsMapper;
import com.jijia.camunda.service.FormGroupService;
import org.springframework.stereotype.Service;

/**
 * @author : willian fu
 * @version : 1.0
 */
@Service
public class FormGroupServiceImpl extends ServiceImpl<FormGroupsMapper, FormGroups>  implements
        FormGroupService {


}
