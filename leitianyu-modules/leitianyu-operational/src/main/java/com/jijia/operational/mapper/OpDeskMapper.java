package com.jijia.operational.mapper;

import com.jijia.operational.domain.OpDesk;
import com.jijia.operational.domain.info.OpDeskInfo;
import com.jijia.operational.domain.vo.OpDeskVo;

import java.util.List;

/**
 * 前台台账Mapper接口
 *
 * @author leitianyu
 * @date 2023-02-04
 */
public interface OpDeskMapper
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
    public List<OpDeskVo> selectOpDeskList(OpDeskInfo opDesk);

    /**
     * 新增前台台账
     *
     * @param opDesk 前台台账
     * @return 结果
     */
    public int insertOpDesk(OpDesk opDesk);

    /**
     * 修改前台台账
     *
     * @param opDesk 前台台账
     * @return 结果
     */
    public int updateOpDesk(OpDesk opDesk);

    /**
     * 删除前台台账
     *
     * @param deskId 前台台账主键
     * @return 结果
     */
    public int deleteOpDeskByDeskId(Long deskId);

    /**
     * 批量删除前台台账
     *
     * @param deskIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOpDeskByDeskIds(Long[] deskIds);

    public OpDesk selectOpDeskWithLast(OpDeskVo opDesk);

    public OpDesk selectOpDeskWithLastBycha(OpDeskVo opDesk);

    public int insertOpDesk(OpDeskVo opDesk);

    public List<OpDesk> selectOpDeskListWithVo(OpDeskVo opDeskVo);

    public Integer selectSizeWithVo(OpDeskVo opDeskVo);

    public OpDesk selectOpDeskWithLastSpecial(OpDeskVo opDesk);

    public OpDesk selectOpDeskWithLastBychaSpecial(OpDeskVo opDesk);

    public int getProjectAmount(OpDeskVo opDesk);

    public String getThisProjectNum(OpDeskVo opDesk);

    public int updateOpDeskVo(OpDeskVo opDesk);

    public int updateOpDeskVoByCha(OpDeskVo opDesk);

    public int judgmentChangeLast(OpDeskVo opDesk);

    public int judgmentChangefirst(OpDeskVo opDeskVo);

    public int updateOpDeskVoOne(OpDeskVo opDesk);

    /**
     * 找到当月最后一条数据
     * @param opDeskVo 查询数据
     * @return 返回数据
     */
    OpDesk getLastOpDesk(OpDeskVo opDeskVo);

    int findDeskWithProjectId(Long projectId);

    OpDeskVo selectOpDeskVoByNumber(OpDeskVo opDeskVo);

    void updateDeskByExcel(OpDeskVo opDeskVo);

    OpDesk getLastOpDeskByProjectId(OpDeskVo opDeskVo);
}

