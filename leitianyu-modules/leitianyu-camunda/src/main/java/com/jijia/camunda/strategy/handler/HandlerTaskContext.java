package com.jijia.camunda.strategy.handler;


import com.jijia.camunda.annotation.TaskAnnotation;
import com.jijia.camunda.domain.enums.TaskApplyType;
import com.jijia.camunda.strategy.service.taskApply.TaskApplyStrategy;
import com.jijia.common.core.exception.GlobalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author leitianyu
 */
@Component
public class HandlerTaskContext implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(HandlerTaskContext.class);

    /**
     * 锁, 防止重复创建同一对象
     */
    private static final Object LOCK = new Object();

    /**
     * 创建台账生成策略class集合 <key,value>=<模型部署情况, 创建模型修改策略class>
     * <p>
     * 注：此集合只存放orderTypeEnum的子类class，对应的实例交由spring容器来管理
     */
    private static final Map<TaskApplyType, Class<? extends TaskApplyStrategy>> orderStrategyBeanMap = new HashMap<>();

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        HandlerTaskContext.applicationContext = applicationContext;
    }


    /**
     * 获取修改策略
     *
     * @param taskApplyType 工程类型
     */
    public static TaskApplyStrategy getInstance(TaskApplyType taskApplyType) {
        
        // 非空
        if (null == taskApplyType) {
            throw new GlobalException("创建订单策略实现类时，传入的模型类型为空");
        }

        // 当集合为空时，则初始化
        if (orderStrategyBeanMap.isEmpty()) {
            initStrategy();
        }

        Class<? extends TaskApplyStrategy> clazz = orderStrategyBeanMap.get(taskApplyType);
        if (null == clazz) {
            throw new GlobalException("未找到模型类型(" + taskApplyType + ")的修改策略实现类");
        }
        // 从spring容器中获取bean
        return applicationContext.getBean(clazz);

    }

    /**
     * 初始化
     */
    private static void initStrategy() {
        synchronized (LOCK) {
            // 获取接口下所有实例bean
            Map<String, TaskApplyStrategy> strategyMap = applicationContext.getBeansOfType(TaskApplyStrategy.class);
            if (strategyMap.isEmpty()) {
                throw new GlobalException("代码配置错误：未获取到ModelUpdateStrategy的实现类，请检查代码中是否有将实现类bean注册到spring容器");
            }

            // 加载所有策略类对应的配置
            TaskAnnotation annotation;
            for (Map.Entry strategy : strategyMap.entrySet()) {
                Class strategyClazz = strategy.getValue().getClass();
                // 因为策略bean可能是经过动态代理生成的bean实例（可能是多重动态代理后的代理对象），
                // 故而bean实例的class可能已经不是原来的class了，所以beanClass.getAnnotation(...)获取不到对应的注解元信息
                annotation = (TaskAnnotation) strategyClazz.getAnnotation(TaskAnnotation.class);
                if (null == annotation) {
                    // 当从bean实例的class上获取不到注解元信息时，通过AnnotationUtils工具类递归来获取
                    annotation = AnnotationUtils.findAnnotation(strategyClazz, TaskAnnotation.class);
                    if (null == annotation) {
                        logger.warn("代码配置错误：创建订单策略实现类{}未配置ModelUpdateAnnotation注解", strategyClazz.getName());
                        continue;
                    }
                }
                // 支持多个事件类型
                TaskApplyType[] typeEnum = annotation.TaskCompleteType();
                //String key = getKey(typeEnum.getOrderType());
                if (orderStrategyBeanMap.containsKey(typeEnum)) {
                    logger.error("代码配置错误：一个修改类型({})只能对应一个修改策略实现{}", typeEnum, strategyClazz.getName());
                    throw new GlobalException("代码配置错误：一个模型类型(" + typeEnum + ")只能对应一个修改实现bean");
                }
                for (TaskApplyType taskApplyType : typeEnum) {
                    orderStrategyBeanMap.put(taskApplyType, strategyClazz);
                }

            }

            if (orderStrategyBeanMap.isEmpty()) {
                logger.warn("初始化创建修改集合失败");
            }
        }
    }


}
