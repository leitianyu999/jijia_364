/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 */

package com.jijia.camunda.config;


import org.camunda.bpm.engine.impl.cfg.AbstractProcessEnginePlugin;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author
 * @date
 * @desc
 */
@Configuration
public class ActivitiConfig {
	@Component
	public class CamundaGlobalListenerPlugin extends AbstractProcessEnginePlugin {
		@Override
		public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {



		}
	}
}
