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
 * 原材台账生成
 *
 * @author leitianyu
 */
@Component
@ProgramInsertTypeAnnotation(programType = ProgramType.YUAN_CAI)
public class ProgramYuancaiInsert extends AbstractProgramInsertStrategy {

    @Resource
    private IOpParameterService opParameterService;

    private static final String YUAN_CAI = "Y-";
    private static final String YUAN_CAI_24 = "Y";

    @Override
    public void creatNumber(OpDeskVo opDeskVo, OpDesk lastDesk) {
        //获取委托日期
        String entrustment = String.valueOf(opDeskVo.getEntrustmentTime());

        //创建计数器
        AtomicInteger atomicInteger = new AtomicInteger(0);

        if (lastDesk!=null) {
            atomicInteger.addAndGet(Integer.parseInt(lastDesk.getRecordSerialNumber().substring(lastDesk.getRecordSerialNumber().length() - 4)));
            atomicInteger.addAndGet(lastDesk.getSampleAmount());
        } else if (opDeskVo.getIsInterpolation().equals(DeskConstants.DESK_IS_INTERPOLATION)) {
            atomicInteger.set(opParameterService.selectMaxNumber(opDeskVo.getParameterCode()));
            atomicInteger.incrementAndGet();
        } else {
            atomicInteger.incrementAndGet();
        }


        //拼接字符串
        String stringBuilder = opDeskVo.getRecordSerialNumber() + YUAN_CAI +
                opDeskVo.getParameterCode() +
                entrustment.substring(2, 4) +
                entrustment.substring(5, 7) +
                String.format("%04d", atomicInteger.get());
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
            atomicInteger.addAndGet(lastDesk.getSampleAmount());
        } else if (opDeskVo.getIsInterpolation().equals(DeskConstants.DESK_IS_INTERPOLATION)) {
            atomicInteger.set(opParameterService.selectMaxNumber(opDeskVo.getParameterCode()));
            atomicInteger.incrementAndGet();
        } else {
            atomicInteger.incrementAndGet();
        }

        //拼接字符串
        String stringBuilder = opDeskVo.getRecordSerialNumber() + YUAN_CAI_24 + code +
                opDeskVo.getParameterCode() +
                entrustment.substring(2, 4) +
                entrustment.substring(5, 7) +
                String.format("%04d", atomicInteger.get());
        opDeskVo.setRecordSerialNumber(stringBuilder);
    }

    @Override
    public void startBegin(OpDeskVo opDeskVo) {

    }


}
