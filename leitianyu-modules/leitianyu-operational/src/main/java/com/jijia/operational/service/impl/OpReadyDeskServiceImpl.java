package com.jijia.operational.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.jijia.common.core.constant.SecurityConstants;
import com.jijia.common.core.exception.GlobalException;
import com.jijia.common.core.utils.DateUtils;
import com.jijia.common.core.web.domain.AjaxResult;
import com.jijia.operational.domain.info.OpReadyInfo;
import com.jijia.operational.domain.vo.OpDeskVo;
import com.jijia.operational.domain.vo.OpReadyDeskVo;
import com.jijia.operational.mapper.OpReadyDeptMapper;
import com.jijia.operational.service.IOpDeskService;
import com.jijia.operational.service.IOpReadyDeptService;
import com.jijia.operational.utils.constants.ConfigConstants;
import com.jijia.operational.utils.constants.DeptLevel;
import com.jijia.system.api.RemoteConfigService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.jijia.operational.mapper.OpReadyDeskMapper;
import com.jijia.operational.service.IOpReadyDeskService;

/**
 * 预备表Service业务层处理
 *
 * @author leitianyu
 * @date 2023-08-08
 */
@Service
public class OpReadyDeskServiceImpl implements IOpReadyDeskService
{
    private final OpReadyDeskMapper opReadyDeskMapper;
    private final OpReadyDeptMapper opReadyDeptMapper;
    private final IOpReadyDeptService opReadyDeptService;
    private final IOpDeskService opDeskService;
    private final RemoteConfigService remoteConfigService;

    public OpReadyDeskServiceImpl(OpReadyDeskMapper opReadyDeskMapper, OpReadyDeptMapper opReadyDeptMapper, IOpReadyDeptService opReadyDeptService, IOpDeskService opDeskService, RemoteConfigService remoteConfigService) {
        this.opReadyDeskMapper = opReadyDeskMapper;
        this.opReadyDeptMapper = opReadyDeptMapper;
        this.opReadyDeptService = opReadyDeptService;
        this.opDeskService = opDeskService;
        this.remoteConfigService = remoteConfigService;
    }

    /**
     * 查询预备表
     *
     * @param readyId 预备表主键
     * @return 预备表
     */
    @Override
    public OpReadyDeskVo selectOpReadyDeskByReadyId(Long readyId)
    {
        return opReadyDeskMapper.selectOpReadyDeskByReadyId(readyId);
    }

    /**
     * 查询预备表列表
     *
     * @param opReadyInfo 预备表
     * @return 预备表
     */
    @Override
    public List<OpReadyDeskVo> selectOpReadyDeskList(OpReadyInfo opReadyInfo)
    {
        return opReadyDeskMapper.selectOpReadyDeskList(opReadyInfo);
    }

    /**
     * 新增预备表
     *
     * @param opReadyDesk 预备表
     * @return 结果
     */
    @Override
    public int insertOpReadyDesk(OpReadyDeskVo opReadyDesk)
    {
        opReadyDesk.setCreateTime(DateUtils.getNowDate());
        AtomicInteger atomicInteger = new AtomicInteger(0);
        atomicInteger.addAndGet(opReadyDeskMapper.insertOpReadyDesk(opReadyDesk));
        //部门权限添加
        if (opReadyDesk.getEditDeptId()!=null && opReadyDesk.getEditDeptId().size() > 0) {
            atomicInteger.addAndGet(opReadyDeptMapper.insertOpReadyDept(opReadyDesk.getEditDeptId(),opReadyDesk.getReadyId(), DeptLevel.EDIT_DEPT));
        }
        if (opReadyDesk.getVisitDeptId()!=null && opReadyDesk.getVisitDeptId().size() > 0) {
            atomicInteger.addAndGet(opReadyDeptMapper.insertOpReadyDept(opReadyDesk.getVisitDeptId(),opReadyDesk.getReadyId(),DeptLevel.VISIT_DEPT));
        }
        return atomicInteger.get();
    }

    /**
     * 修改预备表
     *
     * @param opReadyDesk 预备表
     * @return 结果
     */
    @Override
    public int updateOpReadyDesk(OpReadyDeskVo opReadyDesk)
    {
        opReadyDesk.setUpdateTime(DateUtils.getNowDate());
        AtomicInteger atomicInteger = new AtomicInteger(0);
        atomicInteger.addAndGet(opReadyDeptService.updateDeptByOp(opReadyDesk.getReadyId(),opReadyDesk.getEditDeptId(),opReadyDesk.getVisitDeptId()));
        atomicInteger.addAndGet(opReadyDeskMapper.updateOpReadyDesk(opReadyDesk));
        return atomicInteger.get();
    }

    /**
     * 批量删除预备表
     *
     * @param readyIds 需要删除的预备表主键
     * @return 结果
     */
    @Override
    public int deleteOpReadyDeskByReadyIds(Long[] readyIds)
    {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        for (Long readyId : readyIds) {
            atomicInteger.addAndGet(deleteOpReadyDeskByReadyId(readyId));
        }
        return atomicInteger.get();
    }

    /**
     * 删除预备表信息
     *
     * @param readyId 预备表主键
     * @return 结果
     */
    @Override
    public int deleteOpReadyDeskByReadyId(Long readyId)
    {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        atomicInteger.addAndGet(opReadyDeptService.deleteDeptByReadyId(readyId));
        atomicInteger.addAndGet(opReadyDeskMapper.deleteOpReadyDeskByReadyId(readyId));
        return atomicInteger.get();
    }

    /**
     * 添加未来台账
     *
     * @param opDeskVo 台账信息
     * @return 行数
     */
    @Override
    public int addOpReadyDeskByDeskVo(OpDeskVo opDeskVo) {
        OpReadyDeskVo opReadyDeskVo = new OpReadyDeskVo();
        BeanUtils.copyProperties(opDeskVo,opReadyDeskVo);
        return insertOpReadyDesk(opReadyDeskVo);
    }

    @Override
    public AjaxResult addReadyToDesk() {
        OpReadyInfo opReadyInfo = new OpReadyInfo();
        List<LocalDate> list = new ArrayList<>();
        list.add(LocalDate.now());
        opReadyInfo.setEntrustmentTime(list);
        AjaxResult configKey = remoteConfigService.getConfigKey(ConfigConstants.DESK_READY_LIMIT, SecurityConstants.INNER);
        if (configKey.isError()) {
            throw new GlobalException("配置日期信息错误，请立刻联系管理员！");
        }
        String s = configKey.get(AjaxResult.MSG_TAG).toString();
        int limitTime = Integer.parseInt(configKey.get(AjaxResult.MSG_TAG).toString());
        opReadyInfo.setLimitNumber(limitTime);
        List<OpReadyDeskVo> opReadyDeskVos = opReadyDeskMapper.selectOpReadyDeskListByJob(opReadyInfo);
        if (opReadyDeskVos == null || opReadyDeskVos.size() == 0) {
            return AjaxResult.warn("今日无需导入");
        }
        StringBuilder stringBuilder = new StringBuilder("[");
        for (OpReadyDeskVo opReadyDeskVo : opReadyDeskVos) {
            OpDeskVo opDeskVo = new OpDeskVo();
            BeanUtils.copyProperties(opReadyDeskVo,opDeskVo);
            opDeskService.insertOpDesk(opDeskVo);
            stringBuilder.append(opDeskVo.getDeskId()).append(",");
            deleteOpReadyDeskByReadyId(opReadyDeskVo.getReadyId());
        }
        stringBuilder.append("]");
        return AjaxResult.success(stringBuilder);
    }
}
