package com.jijia.operational.service.impl;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.jijia.common.core.exception.GlobalException;
import com.jijia.common.core.utils.DateUtils;
import com.jijia.common.core.utils.StringUtils;
import com.jijia.operational.domain.OpProjectMsg;
import com.jijia.operational.domain.vo.InfoVo;
import com.jijia.operational.domain.vo.OpDeskVo;
import com.jijia.operational.domain.vo.OpProjectMsgVo;
import com.jijia.operational.mapper.OpDeskMapper;
import com.jijia.operational.mapper.OpProjectMsgMapper;
import com.jijia.operational.service.IOpProjectMsgService;
import com.jijia.operational.strategy.handler.HandlerDeskContext;
import com.jijia.operational.strategy.service.deskInsert.DeskInsertStrategy;
import com.jijia.operational.utils.constants.DeskConstants;
import com.jijia.operational.utils.enums.DeskInsertType;
import org.springframework.stereotype.Service;

/**
 * 工程类型Service业务层处理
 *
 * @author leitianyu
 * @date 2023-02-05
 */
@Service
public class OpProjectMsgServiceImpl implements IOpProjectMsgService
{
    private final OpProjectMsgMapper opProjectMsgMapper;
    private final OpDeskMapper opDeskMapper;

    public OpProjectMsgServiceImpl(OpProjectMsgMapper opProjectMsgMapper, OpDeskMapper opDeskMapper) {
        this.opProjectMsgMapper = opProjectMsgMapper;
        this.opDeskMapper = opDeskMapper;
    }

    /**
     * 查询工程类型
     *
     * @param projectId 工程类型主键
     * @return 工程类型
     */
    @Override
    public OpProjectMsg selectOpProjectMsgByProjectId(Long projectId)
    {
        return opProjectMsgMapper.selectOpProjectMsgByProjectId(projectId);
    }

    /**
     * 查询工程类型列表
     *
     * @param opProjectMsg 工程类型
     * @return 工程类型
     */
    @Override
    public List<OpProjectMsg> selectOpProjectMsgList(OpProjectMsg opProjectMsg)
    {
        return opProjectMsgMapper.selectOpProjectMsgList(opProjectMsg);
    }

    /**
     * 新增工程类型
     *
     * @param opProjectMsg 工程类型
     * @return 结果
     */
    @Override
    public int insertOpProjectMsg(OpProjectMsg opProjectMsg)
    {
        if (StringUtils.isEmpty(opProjectMsg.getProjectName())||StringUtils.isEmpty(opProjectMsg.getEntrustmentUnit()) || opProjectMsg.getProjectType() == null) {
            throw new GlobalException(opProjectMsg +"信息不完整");
        }
        opProjectMsg.setCreateTime(DateUtils.getNowDate());
        DeskInsertStrategy deskInsertStrategy = HandlerDeskContext.getInstance(opProjectMsg.getProjectType());
        return deskInsertStrategy.insertOpProjectMsg(opProjectMsg);
    }

    /**
     * 修改工程类型
     *
     * @param opProjectMsg 工程类型
     * @return 结果
     */
    @Override
    public int updateOpProjectMsg(OpProjectMsg opProjectMsg)
    {
        opProjectMsg.setUpdateTime(DateUtils.getNowDate());
        DeskInsertStrategy deskInsertStrategy = HandlerDeskContext.getInstance(opProjectMsg.getProjectType());
        return deskInsertStrategy.updateOpProjectMsg(opProjectMsg);
    }

    /**
     * 批量删除工程类型
     *
     * @param projectIds 需要删除的工程类型主键
     * @return 结果
     */
    @Override
    public int deleteOpProjectMsgByProjectIds(Long[] projectIds)
    {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        for (Long projectId : projectIds) {
            atomicInteger.addAndGet(deleteOpProjectMsgByProjectId(projectId));
        }
        return atomicInteger.get();
    }

    /**
     * 删除工程类型信息
     *
     * @param projectId 工程类型主键
     * @return 结果
     */
    @Override
    public int deleteOpProjectMsgByProjectId(Long projectId)
    {
        int deskWithProjectId = opDeskMapper.findDeskWithProjectId(projectId);
        if (deskWithProjectId != 0) {
            throw new GlobalException("该工程编号已使用，不可删除");
        }


        return opProjectMsgMapper.deleteOpProjectMsgByProjectId(projectId);
    }

    @Override
    public List<OpProjectMsgVo> getListAll(InfoVo infoVo) {
        return opProjectMsgMapper.getListAll(infoVo);
    }

    @Override
    public void saveAll(List<OpProjectMsg> opProjectMsgs) {
        opProjectMsgs.forEach(this::save);
    }

    @Override
    public void getIdByName(OpDeskVo opDeskVo) {
        opDeskVo.setProjectId(opProjectMsgMapper.getIdByName(opDeskVo));
    }

    public void save(OpProjectMsg opProjectMsg) {

        if (DeskInsertType.getEnum(opProjectMsg.getProjectType()) == null) {
            return;
        }

        Integer i = opProjectMsgMapper.searchOne(opProjectMsg);
        if (i.equals(0)) {
            insertOpProjectMsg(opProjectMsg);
        }else if (i.equals(1)) {
            throw new GlobalException(opProjectMsg +"出现了重复");
//            insertOpProjectMsg(opProjectMsg);
        }else {
            throw new GlobalException(opProjectMsg +"出现了多条重复");
//            insertOpProjectMsg(opProjectMsg);
        }
    }
}

