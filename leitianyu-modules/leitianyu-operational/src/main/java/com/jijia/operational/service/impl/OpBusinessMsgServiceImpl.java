package com.jijia.operational.service.impl;

import java.util.Arrays;
import java.util.List;

import com.jijia.common.core.constant.HttpStatus;
import com.jijia.common.core.constant.SecurityConstants;
import com.jijia.common.core.domain.R;
import com.jijia.common.core.exception.GlobalException;
import com.jijia.common.core.utils.DateUtils;
import com.jijia.common.security.utils.SecurityUtils;
import com.jijia.operational.domain.info.OpBusinessInfo;
import com.jijia.operational.domain.info.OpDeskInfo;
import com.jijia.operational.domain.vo.OpBusinessMsgVo;
import com.jijia.system.api.domain.SysDept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jijia.operational.mapper.OpBusinessMsgMapper;
import com.jijia.operational.domain.OpBusinessMsg;
import com.jijia.operational.service.IOpBusinessMsgService;

/**
 * 业务部Service业务层处理
 *
 * @author leitianyu
 * @date 2024-01-15
 */
@Service
public class OpBusinessMsgServiceImpl implements IOpBusinessMsgService
{
    @Autowired
    private OpBusinessMsgMapper opBusinessMsgMapper;

    /**
     * 查询业务部
     *
     * @param businessId 业务部主键
     * @return 业务部
     */
    @Override
    public OpBusinessMsgVo selectOpBusinessMsgByBusinessId(Long businessId)
    {
        return opBusinessMsgMapper.selectOpBusinessMsgByBusinessId(businessId);
    }

    /**
     * 查询业务部列表
     *
     * @param opBusinessMsg 业务部
     * @return 业务部
     */
    @Override
    public List<OpBusinessMsgVo> selectOpBusinessMsgList(OpBusinessInfo opBusinessMsg)
    {
        return opBusinessMsgMapper.selectOpBusinessMsgList(opBusinessMsg);
    }

    /**
     * 新增业务部
     *
     * @param opBusinessMsg 业务部
     * @return 结果
     */
    @Override
    public int insertOpBusinessMsg(OpBusinessMsgVo opBusinessMsg)
    {
        if (opBusinessMsg.getReportUnit() != null) {
            opBusinessMsg.setCreateBy(SecurityUtils.getUsername());
            opBusinessMsg.setCreateTime(DateUtils.getNowDate());
            return opBusinessMsgMapper.insertOpBusinessMsg(opBusinessMsg);
        }
       return 0;
    }

    /**
     * 修改业务部
     *
     * @param opBusinessMsg 业务部
     * @return 结果
     */
    @Override
    public int updateOpBusinessMsg(OpBusinessMsgVo opBusinessMsg)
    {
        opBusinessMsg.setUpdateTime(DateUtils.getNowDate());
        return opBusinessMsgMapper.updateOpBusinessMsg(opBusinessMsg);
    }

    /**
     * 批量删除业务部
     *
     * @param businessIds 需要删除的业务部主键
     * @return 结果
     */
    @Override
    public int deleteOpBusinessMsgByBusinessIds(Long[] businessIds)
    {
        return opBusinessMsgMapper.deleteOpBusinessMsgByBusinessIds(businessIds);
    }

    /**
     * 删除业务部信息
     *
     * @param businessId 业务部主键
     * @return 结果
     */
    @Override
    public int deleteOpBusinessMsgByBusinessId(Long businessId)
    {
        return opBusinessMsgMapper.deleteOpBusinessMsgByBusinessId(businessId);
    }

    @Override
    public void saveAll(List<OpBusinessMsgVo> opDeskVos) {
        opDeskVos.forEach(o -> {
            if (o.getIsImport() != null) {
                insertOpBusinessMsg(o);
            }
        });
    }

    @Override
    public int updateOpDeskAll(OpBusinessMsgVo opBusinessMsgVo, Long[] businessIds) {
        long count = Arrays.stream(businessIds).map(o -> {
            opBusinessMsgVo.setBusinessId(o);
            return this.updateOpBusinessMsg(opBusinessMsgVo);
        }).count();
        return (int) count;
    }
}
