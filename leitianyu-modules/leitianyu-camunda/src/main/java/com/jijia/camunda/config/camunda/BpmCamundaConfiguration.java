package com.jijia.camunda.config.camunda;

import com.jijia.camunda.listenter.GlobalProcessListener;
import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParseListener;
import org.camunda.bpm.engine.impl.cfg.AbstractProcessEnginePlugin;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * camunda配置类
 *
 * @author leitianyu999
 */
@Configuration(proxyBeanMethods = false)
public class BpmCamundaConfiguration {

    @Component
    public class CamundaGlobalListenerPlugin extends AbstractProcessEnginePlugin {
        @Override
        public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
            List<BpmnParseListener> preParseListeners = processEngineConfiguration.getCustomPreBPMNParseListeners();
            if(preParseListeners == null) {
                preParseListeners = new ArrayList<BpmnParseListener>();
                processEngineConfiguration.setCustomPreBPMNParseListeners(preParseListeners);
            }
            preParseListeners.add(new GlobalProcessListener());

//            Map<Object,Object> params= new HashMap<>();
//            BpmTaskAssignRuleService bpmTaskAssignRuleService = SpringUtil.getBean(BpmTaskAssignRuleService.class);
//            params.put("bpmTaskAssignRuleService",bpmTaskAssignRuleService);
//            processEngineConfiguration.setBeans(params);
        }
    }

}
