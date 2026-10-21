package com.jijia.operational.service.impl;


import com.jijia.common.core.exception.GlobalException;
import com.jijia.operational.domain.info.OpDeskInfo;
import com.jijia.operational.domain.vo.OpSettleVo;
import com.jijia.operational.mapper.OpSettleMapper;
import com.jijia.operational.service.IOpSettleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


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
        if (deskIds == null || deskIds.length == 0) {
            throw new GlobalException("请选择需要修改的结算台账");
        }
        if (opSettleVo == null || (opSettleVo.getIsSettlement() == null
                && opSettleVo.getSettleAmount() == null && opSettleVo.getSettleType() == null
                && opSettleVo.getSettleRemark() == null)) {
            throw new GlobalException("请至少填写一项结算信息");
        }
        if (opSettleVo.getIsSettlement() != null && !"0".equals(opSettleVo.getIsSettlement())
                && !"1".equals(opSettleVo.getIsSettlement())) {
            throw new GlobalException("是否结算只能为是或否");
        }
        if (opSettleVo.getSettleAmount() != null && opSettleVo.getSettleAmount() < 0) {
            throw new GlobalException("结算金额不能小于0");
        }
        if (opSettleVo.getSettleType() != null && opSettleVo.getSettleType() != 0
                && opSettleVo.getSettleType() != 1) {
            throw new GlobalException("结算类型不正确");
        }
        int updated = 0;
        for (Long item : deskIds) {
            if (item == null) {
                throw new GlobalException("结算台账编号不能为空");
            }
            OpSettleVo opSettle = settleMapper.selectOpDeskByDeskId(item);
            if (opSettle == null || opSettle.getDeskId() == null) {
                throw new GlobalException("结算台账不存在：" + item);
            }
            // 仅更新本次传入的结算字段，未填写的字段保持原值。
            OpSettleVo update = new OpSettleVo();
            update.setDeskId(opSettle.getDeskId());
            update.setIsSettlement(opSettleVo.getIsSettlement());
            update.setSettleAmount(opSettleVo.getSettleAmount());
            update.setSettleType(opSettleVo.getSettleType());
            update.setSettleRemark(opSettleVo.getSettleRemark());
            updated += settleMapper.updateOpDesk(update);
        }
        return updated;
    }
}
