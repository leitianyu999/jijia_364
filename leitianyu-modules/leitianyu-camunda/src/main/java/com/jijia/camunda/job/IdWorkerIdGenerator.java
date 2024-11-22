//package com.jijia.camunda.job;
//
//import com.jijia.camunda.utils.IdWorker;
//import com.jijia.camunda.utils.SpringContextHolder;
//import org.camunda.bpm.engine.impl.cfg.IdGenerator;
//import org.springframework.stereotype.Component;
//
///**
// * @Author:LoveMyOrange
// * @Description:
// * @Date:Created in 2022/10/17 13:01
// */
//@Component
//public class IdWorkerIdGenerator implements IdGenerator {
//
//  @Override
//  public String getNextId() {
//    return SpringContextHolder.getBean(IdWorker.class).nextId()+"";
//  }
//}
////