package com.jijia.operational.strategy.service.programInsert;

import com.jijia.operational.domain.OpDesk;
import com.jijia.operational.domain.vo.OpDeskVo;

/**
 * 台账生成接口
 *
 * @author leitianyu
 */
public interface ProgramInsertStrategy {

    /**
     * 根据不同策略生成不同编号
     *
     * @param opDeskVo 处理后的新增数据
     * @param lastDesk 最后一条数据
     */
    void creatNumber(OpDeskVo opDeskVo, OpDesk lastDesk);

    /**
     * 根据不同策略生成不同编号
     *
     * @param opDeskVo 处理后的新增数据
     * @param lastDesk 最后一条数据
     */
    void creatNumberBy24(OpDeskVo opDeskVo, OpDesk lastDesk, String code);

    /**
     * 对台账做初始检测
     * @param opDeskVo 台账
     */
    void startBegin(OpDeskVo opDeskVo);
}
