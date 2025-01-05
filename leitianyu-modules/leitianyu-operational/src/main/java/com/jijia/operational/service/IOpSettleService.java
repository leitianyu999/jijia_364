package com.jijia.operational.service;

import com.jijia.operational.domain.info.OpDeskInfo;
import com.jijia.operational.domain.vo.OpDeskVo;
import com.jijia.operational.domain.vo.OpSettleVo;

import java.util.List;

/**
 * 前台台账Service接口
 *
 * @author leitianyu
 * @date 2023-02-04
 */
public interface IOpSettleService
{

    List<OpSettleVo> selectOpDeskList(OpDeskInfo opDesk);

    int updateOpDeskAll(OpSettleVo opDesk, Long[] deskIds);
}

