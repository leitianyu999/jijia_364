package com.jijia.operational.mapper;

import com.jijia.operational.domain.OpProjectMsg;
import com.jijia.operational.domain.vo.InfoVo;
import com.jijia.operational.domain.vo.OpDeskVo;
import com.jijia.operational.domain.vo.OpProjectMsgVo;

import java.util.List;

/**
 * 工程类型Mapper接口
 *
 * @author leitianyu
 * @date 2023-02-05
 */
public interface OpProjectMsgMapper
{
    /**
     * 查询工程类型
     *
     * @param projectId 工程类型主键
     * @return 工程类型
     */
    public OpProjectMsg selectOpProjectMsgByProjectId(Long projectId);

    /**
     * 查询工程类型列表
     *
     * @param opProjectMsg 工程类型
     * @return 工程类型集合
     */
    public List<OpProjectMsg> selectOpProjectMsgList(OpProjectMsg opProjectMsg);

    /**
     * 新增工程类型
     *
     * @param opProjectMsg 工程类型
     * @return 结果
     */
    public int insertOpProjectMsg(OpProjectMsg opProjectMsg);

    /**
     * 修改工程类型
     *
     * @param opProjectMsg 工程类型
     * @return 结果
     */
    public int updateOpProjectMsg(OpProjectMsg opProjectMsg);

    /**
     * 删除工程类型
     *
     * @param projectId 工程类型主键
     * @return 结果
     */
    public int deleteOpProjectMsgByProjectId(Long projectId);

    /**
     * 批量删除工程类型
     *
     * @param projectIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOpProjectMsgByProjectIds(Long[] projectIds);


    public OpProjectMsg selectOpProjectMsgBylast(Integer projectType);

    public OpProjectMsg selectOpProjectMsgBylastAndType(OpProjectMsg opProjectMsg);

    public List<OpProjectMsgVo> getListAll(InfoVo infoVo);

    public int searchOne(OpProjectMsg opProjectMsg);

    public Long getIdByName(OpDeskVo opDeskVo);

    /**
     * 修改工程类型
     *
     * @param opProjectMsg 工程类型
     * @return 结果
     */
    public int updateOpProjectMsgByUser(OpProjectMsg opProjectMsg);
}

