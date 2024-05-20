package com.jijia.operational.service;

import com.jijia.operational.domain.info.OpDeskInfo;
import com.jijia.operational.domain.vo.OpDeskVo;

import java.util.List;

/**
 * 前台台账Service接口
 *
 * @author leitianyu
 * @date 2023-02-04
 */
public interface IOpDeskService
{
    /**
     * 查询前台台账
     *
     * @param deskId 前台台账主键
     * @return 前台台账
     */
    public OpDeskVo selectOpDeskByDeskId(Long deskId);

    /**
     * 查询前台台账列表
     *
     * @param opDesk 前台台账
     * @return 前台台账集合
     */
    public List<OpDeskVo> selectOpDeskList(OpDeskInfo opDesk,Boolean bool);

    /**
     * 新增前台台账
     *
     * @param opDesk 前台台账
     * @return 结果
     */
    public int insertOpDesk(OpDeskVo opDesk);

    /**
     * 修改前台台账
     *
     * @param opDesk 前台台账
     * @return 结果
     */
    public int updateOpDesk(OpDeskVo opDesk);

    /**
     * 批量删除前台台账
     *
     * @param deskIds 需要删除的前台台账主键集合
     * @return 结果
     */
    public int deleteOpDeskByDeskIds(Long[] deskIds);

    /**
     * 删除前台台账信息
     *
     * @param deskId 前台台账主键
     * @return 结果
     */
    public int deleteOpDeskByDeskId(Long deskId);

    Boolean judgmentIsLast(Long deskId);

    public void saveAll(List<OpDeskVo> opDeskVos);

    public int updateOpDeskAll(OpDeskVo opDesk, Long[] deskIds);

    public int updateOpDeskOne(OpDeskVo opDesk);
}

