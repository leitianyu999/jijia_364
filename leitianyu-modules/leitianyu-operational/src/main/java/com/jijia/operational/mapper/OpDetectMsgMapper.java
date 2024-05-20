package com.jijia.operational.mapper;

import com.jijia.operational.domain.OpDetectMsg;
import com.jijia.operational.domain.info.OpDetectMsgInfo;
import com.jijia.operational.domain.vo.OpDetectMsgVo;
import com.jijia.system.api.domain.SysDept;
import com.jijia.system.api.domain.SysUser;
import com.jijia.system.api.model.LoginUser;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;


/**
 * 检测台账Mapper接口
 *
 * @author leitianyu
 * @date 2023-02-04
 */
public interface OpDetectMsgMapper
{
    /**
     * 查询检测台账
     *
     * @param deskId 检测台账主键
     * @return 检测台账
     */
    public OpDetectMsg selectOpDetectMsgByDeskId(@Param("deskId") Long deskId,@Param("deptId") List<SysDept> deptId,
                                                 @Param("userId") Long userId,@Param("bool") Boolean bool);

    /**
     * 查询检测台账列表
     *
     * @param opDetectMsg 检测台账
     * @return 检测台账集合
     */
    public List<OpDetectMsgVo> selectOpDetectMsgList(@Param("opDetectMsg") OpDetectMsgInfo opDetectMsg, @Param("deptId") List<SysDept> deptId,
                                                     @Param("userId") Long userId,@Param("bool") Boolean bool);

    /**
     * 新增检测台账
     *
     * @param opDetectMsg 检测台账
     * @return 结果
     */
    public int insertOpDetectMsg(OpDetectMsg opDetectMsg);

    /**
     * 修改检测台账
     *
     * @param opDetectMsg 检测台账
     * @return 结果
     */
    public int updateOpDetectMsg(OpDetectMsg opDetectMsg);

    /**
     * 删除检测台账
     *
     * @param deskId 检测台账主键
     * @return 结果
     */
    public int deleteOpDetectMsgByDeskId(Long deskId);

    /**
     * 批量删除检测台账
     *
     * @param deskIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOpDetectMsgByDeskIds(Long[] deskIds);

    public OpDetectMsg selectOne(@Param("opDetectMsg") OpDetectMsgVo opDetectMsg, @Param("deptId") List<SysDept> deptId,
                                 @Param("userId") Long userId , @Param("bool") Boolean bool);

    public int updateMsgUser(@Param("deskIds") Long[] deskIds, @Param("userId") Long userId, @Param("localDate") LocalDate localDate, @Param("deptId") List<SysDept> deptId);

    public OpDetectMsg selectOpDetectMsgByDeskIdAndDept(@Param("deskId") Long deskId,@Param("deptId") List<SysDept> deptId,
                                                 @Param("userId") Long userId,@Param("bool") Boolean bool);

    public int updateMsgUserAll(@Param("deskIds") Long[] deskIds, @Param("userId") Long userId, @Param("localDate") LocalDate localDate);

    public List<OpDetectMsgVo> selectOpDetectMsgListByUserId(@Param("opDetectMsg") OpDetectMsgInfo opDetectMsg,@Param("sysUser") SysUser sysUser);

    public OpDetectMsg selectOneByUserId(@Param("opDetectMsg") OpDetectMsgVo opDetectMsgVO,@Param("sysUser") LoginUser loginUser);
}

