package com.jijia.operational.strategy.handler;


import com.jijia.common.core.exception.GlobalException;
import com.jijia.operational.annotation.DeskInsertTypeAnnotation;
import com.jijia.operational.strategy.service.deskInsert.DeskInsertStrategy;
import com.jijia.operational.utils.enums.DeskInsertType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
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
public class HandlerDeskContext implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(HandlerDeskContext.class);

    /**
     * 锁, 防止重复创建同一对象
     */
    private static final Object LOCK = new Object();

    /**
     * 创建台账生成策略class集合 <key,value>=<工程类型类型, 创建台账生成策略class>
     * <p>
     * 注：此集合只存放DeskInsertType的子类class，对应的实例交由spring容器来管理
     */
    private static final Map<DeskInsertType, Class<? extends DeskInsertStrategy>> orderStrategyBeanMap = new HashMap<>();

    @Autowired
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        HandlerDeskContext.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }


    /**
     * 获取创建台账生成策略实例
     *
     * @param deskType 工程类型
     */
    public static DeskInsertStrategy getInstance(Integer deskType) {
        if (null == deskType) {
            throw new GlobalException("工程类型不能为空");
        }

        DeskInsertType orderTypeEnum = DeskInsertType.getEnum(deskType);
        if (null == orderTypeEnum) {
            throw new GlobalException("暂时不支持该工程类型orderType=" + deskType);
        }

        // 当集合为空时，则初始化
        if (orderStrategyBeanMap.size() == 0) {
            initStrategy();
        }

        Class<? extends DeskInsertStrategy> clazz = orderStrategyBeanMap.get(orderTypeEnum);
        if (null == clazz) {
            throw new GlobalException("未找到工程类型(" + orderTypeEnum + ")的创建工程类型策略实现类");
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
            Map<String, DeskInsertStrategy> strategyMap = applicationContext.getBeansOfType(DeskInsertStrategy.class);
            if (null == strategyMap || strategyMap.size() == 0) {
                throw new GlobalException("代码配置错误：未获取到DeskInsertStrategy的实现类，请检查代码中是否有将实现类bean注册到spring容器");
            }

            // 加载所有策略类对应的配置
            DeskInsertTypeAnnotation annotation;
            for (Map.Entry strategy : strategyMap.entrySet()) {
                Class strategyClazz = strategy.getValue().getClass();
                // 因为策略bean可能是经过动态代理生成的bean实例（可能是多重动态代理后的代理对象），
                // 故而bean实例的class可能已经不是原来的class了，所以beanClass.getAnnotation(...)获取不到对应的注解元信息
                annotation = (DeskInsertTypeAnnotation) strategyClazz.getAnnotation(DeskInsertTypeAnnotation.class);
                if (null == annotation) {
                    // 当从bean实例的class上获取不到注解元信息时，通过AnnotationUtils工具类递归来获取
                    annotation = AnnotationUtils.findAnnotation(strategyClazz, DeskInsertTypeAnnotation.class);
                    if (null == annotation) {
                        logger.warn("代码配置错误：创建订单策略实现类{}未配置DeskInsertStrategyAnnotation注解", strategyClazz.getName());
                        continue;
                    }
                }
                // 支持多个事件类型
                DeskInsertType typeEnum = annotation.deskInsertType();
                //String key = getKey(typeEnum.getOrderType());
                if (orderStrategyBeanMap.containsKey(typeEnum)) {
                    logger.error("代码配置错误：一个工程类型({})只能对应一个创建工程策略实现{}", typeEnum, strategyClazz.getName());
                    throw new GlobalException("代码配置错误：一个工程类型(" + typeEnum + ")只能对应一个创建工程策略实现bean");
                }
                orderStrategyBeanMap.put(typeEnum, strategyClazz);
            }

            if (orderStrategyBeanMap.size() == 0) {
                logger.warn("初始化创建工程策略集合失败");
            }
        }
    }


}
