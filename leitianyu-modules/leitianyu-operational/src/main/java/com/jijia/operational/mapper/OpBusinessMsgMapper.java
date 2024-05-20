package com.jijia.operational.mapper;

import java.util.List;
import com.jijia.operational.domain.OpBusinessMsg;
import com.jijia.operational.domain.info.OpBusinessInfo;
import com.jijia.operational.domain.info.OpDeskInfo;
import com.jijia.operational.domain.vo.OpBusinessMsgVo;

/**
 * 业务部Mapper接口
 *
 * @author leitianyu
 * @date 2024-01-15
 */
public interface OpBusinessMsgMapper
{
    /**
     * 查询业务部
     *
     * @param businessId 业务部主键
     * @return 业务部
     */
    public OpBusinessMsgVo selectOpBusinessMsgByBusinessId(Long businessId);

    /**
     * 查询业务部列表
     *
     * @param opBusinessMsg 业务部
     * @return 业务部集合
     */
    public List<OpBusinessMsgVo> selectOpBusinessMsgList(OpBusinessInfo opBusinessMsg);

    /**
     * 新增业务部
     *
     * @param opBusinessMsg 业务部
     * @return 结果
     */
    public int insertOpBusinessMsg(OpBusinessMsgVo opBusinessMsg);

    /**
     * 修改业务部
     *
     * @param opBusinessMsg 业务部
     * @return 结果
     */
    public int updateOpBusinessMsg(OpBusinessMsgVo opBusinessMsg);

    /**
     * 删除业务部
     *
     * @param businessId 业务部主键
     * @return 结果
     */
    public int deleteOpBusinessMsgByBusinessId(Long businessId);

    /**
     * 批量删除业务部
     *
     * @param businessIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOpBusinessMsgByBusinessIds(Long[] businessIds);
}

