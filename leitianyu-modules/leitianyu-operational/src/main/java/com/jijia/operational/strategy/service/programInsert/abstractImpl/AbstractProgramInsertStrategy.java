package com.jijia.operational.strategy.service.programInsert.abstractImpl;

import com.jijia.operational.domain.OpDesk;
import com.jijia.operational.domain.vo.OpDeskVo;
import com.jijia.operational.strategy.service.programInsert.ProgramInsertStrategy;
import org.springframework.stereotype.Component;

/**
 * 台账生成抽象类
 *
 * @author leitianyu
 */
@Component
public abstract class AbstractProgramInsertStrategy implements ProgramInsertStrategy {


    /**
     * 编号生成（默认）
     * @param opDeskVo 处理后的新增数据
     * @param lastDesk 最后一条数据
     */
    @Override
    public void creatNumber(OpDeskVo opDeskVo, OpDesk lastDesk) {
    }
}
