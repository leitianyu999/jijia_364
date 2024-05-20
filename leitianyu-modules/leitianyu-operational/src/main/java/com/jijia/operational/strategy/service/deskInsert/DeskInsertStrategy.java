package com.jijia.operational.strategy.service.deskInsert;

import com.jijia.operational.domain.OpProjectMsg;
import com.jijia.operational.domain.vo.OpDeskVo;

/**
 * 台账生成接口
 *
 * @author leitianyu
 */
public interface DeskInsertStrategy {

    /**
     * 根据不同策略生成不同编号
     *
     * @param opDeskVo 处理后的新增数据
     * @return 返回编号生成后数据
     */
    int addDesk(OpDeskVo opDeskVo);

    /**
     * 根据不同策略生成不同编号
     *
     * @param opDeskVo 处理后的新增数据
     * @return 返回编号生成后数据
     */
    OpDeskVo creatNumber(OpDeskVo opDeskVo);

    /**
     * 根据策略修改台账信息
     *
     * @param opDeskVo 修改信息
     * @param oldDesk 旧信息
     * @return 成功信息
     */
    int updateDesk(OpDeskVo opDeskVo,OpDeskVo oldDesk);

    /**
     * 根据Excel修改台账信息
     *
     * @param opDeskVo 修改信息
     * @return 成功信息
     */
    void updateDeskByExcel(OpDeskVo opDeskVo);


    /**
     * 根据id删除台账信息
     *
     * @param deskId 台账id
     * @return 成功信息
     */
    int deleteDesk(Long deskId);

    /**
     * 根据工程类型生成编号
     *
     * @param opProjectMsg 工程信息
     * @return 行数
     */
    int insertOpProjectMsg(OpProjectMsg opProjectMsg);

    /**
     * 修改工程编号
     *
     * @param opProjectMsg 工程信息
     * @return 行数
     */
    int updateOpProjectMsg(OpProjectMsg opProjectMsg);

}
