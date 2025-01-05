package com.jijia.operational.mapper;

import com.jijia.operational.domain.OpDesk;
import com.jijia.operational.domain.info.OpDeskInfo;
import com.jijia.operational.domain.vo.OpDeskVo;
import com.jijia.operational.domain.vo.OpSettleVo;

import java.util.List;

/**
 * 前台台账Mapper接口
 *
 * @author leitianyu
 * @date 2023-02-04
 */
public interface OpSettleMapper
{

    /**
     * 查询前台台账列表
     *
     * @param opDesk 前台台账
     * @return 前台台账集合
     */
    public List<OpSettleVo> selectOpDeskList(OpDeskInfo opDesk);


    OpSettleVo selectOpDeskByDeskId(Long item);

    int updateOpDesk(OpSettleVo update);
}

