package com.jijia.operational.service;

import java.util.List;

import com.jijia.common.core.web.domain.AjaxResult;
import com.jijia.operational.domain.OpReadyDesk;
import com.jijia.operational.domain.info.OpReadyInfo;
import com.jijia.operational.domain.vo.OpDeskVo;
import com.jijia.operational.domain.vo.OpReadyDeskVo;

/**
 * 预备表Service接口
 *
 * @author leitianyu
 * @date 2023-08-08
 */
public interface IOpReadyDeskService
{
    /**
     * 查询预备表
     *
     * @param readyId 预备表主键
     * @return 预备表
     */
    public OpReadyDeskVo selectOpReadyDeskByReadyId(Long readyId);

    /**
     * 查询预备表列表
     *
     * @param opReadyInfo 预备表
     * @return 预备表集合
     */
    public List<OpReadyDeskVo> selectOpReadyDeskList(OpReadyInfo opReadyInfo);

    /**
     * 新增预备表
     *
     * @param opReadyDesk 预备表
     * @return 结果
     */
    public int insertOpReadyDesk(OpReadyDeskVo opReadyDesk);

    /**
     * 修改预备表
     *
     * @param opReadyDesk 预备表
     * @return 结果
     */
    public int updateOpReadyDesk(OpReadyDeskVo opReadyDesk);

    /**
     * 批量删除预备表
     *
     * @param readyIds 需要删除的预备表主键集合
     * @return 结果
     */
    public int deleteOpReadyDeskByReadyIds(Long[] readyIds);

    /**
     * 删除预备表信息
     *
     * @param readyId 预备表主键
     * @return 结果
     */
    public int deleteOpReadyDeskByReadyId(Long readyId);

    /**
     * 添加未来台账
     *
     * @param opDeskVo 台账信息
     * @return 行数
     */
    public int addOpReadyDeskByDeskVo(OpDeskVo opDeskVo);

    /**
     * 将未来的台账添加
     *
     * @return 行数
     */
    public AjaxResult addReadyToDesk();
}
