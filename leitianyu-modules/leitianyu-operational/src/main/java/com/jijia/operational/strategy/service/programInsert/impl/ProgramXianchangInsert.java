package com.jijia.operational.strategy.service.programInsert.impl;

import com.jijia.operational.annotation.ProgramInsertTypeAnnotation;
import com.jijia.operational.domain.OpDesk;
import com.jijia.operational.domain.vo.OpDeskVo;
import com.jijia.operational.service.IOpParameterService;
import com.jijia.operational.strategy.service.programInsert.abstractImpl.AbstractProgramInsertStrategy;
import com.jijia.operational.utils.constants.DeskConstants;
import com.jijia.operational.utils.enums.ProgramType;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 现场台账生成
 *
 * @author leitianyu
 */
@Component
@ProgramInsertTypeAnnotation(programType = ProgramType.XIAN_CHANG)
public class ProgramXianchangInsert extends AbstractProgramInsertStrategy {

    @Resource
    private IOpParameterService opParameterService;

    private static final String XIAN_CHANG = "J-";
    private static final String XIAN_CHANG_24 = "J";


    @Override
    public void creatNumber(OpDeskVo opDeskVo, OpDesk lastDesk) {

        //获取委托日期
        String entrustment = String.valueOf(opDeskVo.getEntrustmentTime());

        //创建计数器
        AtomicInteger atomicInteger = new AtomicInteger(0);
        if (lastDesk != null) {
            atomicInteger.addAndGet(Integer.parseInt(lastDesk.getRecordSerialNumber().substring(lastDesk.getRecordSerialNumber().length() - 4)));
        } else if (opDeskVo.getIsInterpolation().equals(DeskConstants.DESK_IS_INTERPOLATION)) {
            atomicInteger.set(opParameterService.selectMaxNumber(opDeskVo.getParameterCode()));
        }

        //拼接字符串
        String stringBuilder = opDeskVo.getRecordSerialNumber() + XIAN_CHANG +
                opDeskVo.getParameterCode() +
                entrustment.substring(2, 4) +
                entrustment.substring(5, 7) +
                String.format("%04d", atomicInteger.incrementAndGet());
        opDeskVo.setRecordSerialNumber(stringBuilder);
    }

    @Override
    public void creatNumberBy24(OpDeskVo opDeskVo, OpDesk lastDesk, String code) {

        //获取委托日期
        String entrustment = String.valueOf(opDeskVo.getEntrustmentTime());

        //创建计数器
        AtomicInteger atomicInteger = new AtomicInteger(0);
        if (lastDesk != null) {
            atomicInteger.addAndGet(Integer.parseInt(lastDesk.getRecordSerialNumber().substring(lastDesk.getRecordSerialNumber().length() - 4)));
        } else if (opDeskVo.getIsInterpolation().equals(DeskConstants.DESK_IS_INTERPOLATION)) {
            atomicInteger.set(opParameterService.selectMaxNumber(opDeskVo.getParameterCode()));
        }

        //拼接字符串
        String stringBuilder = opDeskVo.getRecordSerialNumber() + XIAN_CHANG_24 + code +
                opDeskVo.getParameterCode() +
                entrustment.substring(2, 4) +
                entrustment.substring(5, 7) +
                String.format("%04d", atomicInteger.incrementAndGet());
        opDeskVo.setRecordSerialNumber(stringBuilder);

    }

    @Override
    public void startBegin(OpDeskVo opDeskVo) {
        opDeskVo.setSampleAmount(1);
    }

}
