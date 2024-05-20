package com.jijia.system.service.impl;

import com.jijia.common.core.utils.DateUtils;
import com.jijia.system.api.domain.SysUserInfo;
import com.jijia.system.mapper.SysUserInfoMapper;
import com.jijia.system.service.ISysUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户详细信息Service业务层处理
 *
 * @author leitianyu
 * @date 2023-02-03
 */
@Service
public class SysUserInfoServiceImpl implements ISysUserInfoService
{
    @Autowired
    private SysUserInfoMapper sysUserInfoMapper;

    /**
     * 查询用户详细信息
     *
     * @param userId 用户详细信息主键
     * @return 用户详细信息
     */
    @Override
    public SysUserInfo selectSysUserInfoByUserId(Long userId)
    {
        return sysUserInfoMapper.selectSysUserInfoByUserId(userId);
    }

    /**
     * 查询用户详细信息列表
     *
     * @param sysUserInfo 用户详细信息
     * @return 用户详细信息
     */
    @Override
    public List<SysUserInfo> selectSysUserInfoList(SysUserInfo sysUserInfo)
    {
        return sysUserInfoMapper.selectSysUserInfoList(sysUserInfo);
    }

    /**
     * 新增用户详细信息
     *
     * @param sysUserInfo 用户详细信息
     * @return 结果
     */
    @Override
    public int insertSysUserInfo(SysUserInfo sysUserInfo)
    {
        sysUserInfo.setCreateTime(DateUtils.getNowDate());
        return sysUserInfoMapper.insertSysUserInfo(sysUserInfo);
    }

    /**
     * 修改用户详细信息
     *
     * @param sysUserInfo 用户详细信息
     * @return 结果
     */
    @Override
    public int updateSysUserInfo(SysUserInfo sysUserInfo)
    {
        sysUserInfo.setUpdateTime(DateUtils.getNowDate());
        return sysUserInfoMapper.updateSysUserInfo(sysUserInfo);
    }

    /**
     * 批量删除用户详细信息
     *
     * @param userIds 需要删除的用户详细信息主键
     * @return 结果
     */
    @Override
    public int deleteSysUserInfoByUserIds(Long[] userIds)
    {
        return sysUserInfoMapper.deleteSysUserInfoByUserIds(userIds);
    }

    /**
     * 删除用户详细信息信息
     *
     * @param userId 用户详细信息主键
     * @return 结果
     */
    @Override
    public int deleteSysUserInfoByUserId(Long userId)
    {
        return sysUserInfoMapper.deleteSysUserInfoByUserId(userId);
    }
}
