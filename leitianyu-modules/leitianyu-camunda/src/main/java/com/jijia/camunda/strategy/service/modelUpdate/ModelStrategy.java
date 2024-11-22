package com.jijia.camunda.strategy.service.modelUpdate;


import com.jijia.camunda.domain.CmdModel;
import com.jijia.camunda.domain.dto.CmdModelDto;

/**
 * 台账生成接口
 *
 * @author leitianyu
 */
public interface ModelStrategy {


    public int updateModel(CmdModelDto cmdModelDto, CmdModel cmdModel);


}
