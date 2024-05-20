package com.jijia.system.mapper;


import com.jijia.system.api.domain.SysUserInfo;

import java.util.List;

/**
 * 用户详细信息Mapper接口
 *
 * @author leitianyu
 * @date 2023-02-03
 */
public interface SysUserInfoMapper
{
    /**
     * 查询用户详细信息
     *
     * @param userId 用户详细信息主键
     * @return 用户详细信息
     */
    public SysUserInfo selectSysUserInfoByUserId(Long userId);

    /**
     * 查询用户详细信息列表
     *
     * @param sysUserInfo 用户详细信息
     * @return 用户详细信息集合
     */
    public List<SysUserInfo> selectSysUserInfoList(SysUserInfo sysUserInfo);

    /**
     * 新增用户详细信息
     *
     * @param sysUserInfo 用户详细信息
     * @return 结果
     */
    public int insertSysUserInfo(SysUserInfo sysUserInfo);

    /**
     * 修改用户详细信息
     *
     * @param sysUserInfo 用户详细信息
     * @return 结果
     */
    public int updateSysUserInfo(SysUserInfo sysUserInfo);

    /**
     * 删除用户详细信息
     *
     * @param userId 用户详细信息主键
     * @return 结果
     */
    public int deleteSysUserInfoByUserId(Long userId);

    /**
     * 批量删除用户详细信息
     *
     * @param userIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysUserInfoByUserIds(Long[] userIds);
}
