package com.jijia.operational.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.jijia.common.core.exception.GlobalException;
import com.jijia.common.security.auth.AuthUtil;
import com.jijia.operational.domain.info.OpDeskInfo;
import com.jijia.operational.domain.vo.OpDeskVo;
import com.jijia.operational.domain.vo.OpSettleVo;
import com.jijia.operational.mapper.OpSettleMapper;
import com.jijia.operational.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 前台台账Service业务层处理
 *
 * @author leitianyu
 * @date 2023-02-04
 */
@Service
public class OpSettleServiceImpl implements IOpSettleService {

    @Resource
    private OpSettleMapper settleMapper;


    @Override
    public List<OpSettleVo> selectOpDeskList(OpDeskInfo opDesk) {
            return settleMapper.selectOpDeskList(opDesk);

    }

    @Override
    public int updateOpDeskAll(OpSettleVo opSettleVo, Long[] deskIds) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        Arrays.stream(deskIds).forEach(item -> {
            //复制属性
            OpSettleVo opSettle = settleMapper.selectOpDeskByDeskId(item);
            if (opSettle != null && (opSettleVo.getIsSettlement() != null || opSettleVo.getSettleAmount() != null)) {
                OpSettleVo update = new OpSettleVo();
                update.setDeskId(opSettle.getDeskId());
                if (opSettleVo.getIsSettlement() != null) {
                    update.setIsSettlement(opSettleVo.getIsSettlement());
                }
                if (opSettleVo.getSettleAmount() != null) {
                    update.setSettleAmount(opSettleVo.getSettleAmount());
                }
                atomicInteger.addAndGet(settleMapper.updateOpDesk(update));
            }
        });
        return atomicInteger.get();
    }
}
