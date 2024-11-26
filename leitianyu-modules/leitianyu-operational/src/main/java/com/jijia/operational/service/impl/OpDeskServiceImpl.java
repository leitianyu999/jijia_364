package com.jijia.operational.service.impl;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.jijia.common.core.constant.HttpStatus;
import com.jijia.common.core.constant.SecurityConstants;
import com.jijia.common.core.domain.R;
import com.jijia.common.core.exception.GlobalException;
import com.jijia.common.core.utils.StringUtils;
import com.jijia.common.security.auth.AuthUtil;
import com.jijia.common.security.utils.SecurityUtils;
import com.jijia.operational.domain.*;
import com.jijia.operational.domain.info.OpDeskInfo;
import com.jijia.operational.domain.vo.OpDeskVo;
import com.jijia.operational.mapper.OpDeskDeptMapper;
import com.jijia.operational.mapper.OpDeskMapper;
import com.jijia.operational.service.*;
import com.jijia.operational.strategy.handler.HandlerDeskContext;
import com.jijia.operational.strategy.service.deskInsert.DeskInsertStrategy;
import com.jijia.operational.utils.constants.DeptLevel;
import com.jijia.operational.utils.constants.DeskConstants;
import com.jijia.operational.utils.enums.DeskInsertType;
import com.jijia.system.api.RemoterDeptService;
import com.jijia.system.api.domain.SysDept;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * 前台台账Service业务层处理
 *
 * @author leitianyu
 * @date 2023-02-04
 */
@Service
public class OpDeskServiceImpl implements IOpDeskService
{
    private final OpDeskMapper opDeskMapper;
    private final OpDeskDeptMapper opDeskDeptMapper;
    private final IOpParameterService opParameterService;
    private final IOpProjectMsgService opProjectMsgService;
    private final IOpCalibrationMsgService opCalibrationMsgService;
    private final IOpDetectMsgService opDetectMsgService;
    private final RemoterDeptService remoterDeptService;


    public OpDeskServiceImpl(OpDeskMapper opDeskMapper, OpDeskDeptMapper opDeskDept, IOpParameterService opParameterService, IOpProjectMsgService opProjectMsgService, IOpCalibrationMsgService opCalibrationMsgService, IOpDetectMsgService opDetectMsgService, RemoterDeptService remoterDeptService) {
        this.opDeskMapper = opDeskMapper;
        this.opDeskDeptMapper = opDeskDept;
        this.opParameterService = opParameterService;
        this.opProjectMsgService = opProjectMsgService;
        this.opCalibrationMsgService = opCalibrationMsgService;
        this.opDetectMsgService = opDetectMsgService;
        this.remoterDeptService = remoterDeptService;
    }


    /**
     * 查询前台台账
     *
     * @param deskId 前台台账主键
     * @return 前台台账
     */
    @Override
    public OpDeskVo selectOpDeskByDeskId(Long deskId)
    {
        return opDeskMapper.selectOpDeskByDeskId(deskId);
    }

    /**
     * 查询前台台账列表
     *
     * @param opDesk 前台台账
     * @return 前台台账
     */
    @Override
    public List<OpDeskVo> selectOpDeskList(OpDeskInfo opDesk,Boolean bool)
    {
        if (AuthUtil.hasPermi("op:desk:isSettlement")) {
            opDesk.setIsSettlement("1");
        } else {
            opDesk.setIsSettlement("0");
        }
        return opDeskMapper.selectOpDeskList(opDesk);
    }

    /**
     * 新增前台台账
     *
     * @param opDesk 前台台账
     * @return 结果
     */
    @Override
    public int insertOpDesk(OpDeskVo opDesk)
    {
        AtomicInteger atomicInteger = new AtomicInteger(0);

        //根据策略生成编号
        DeskInsertStrategy deskInsertStrategy = HandlerDeskContext.getInstance(opDesk.getProjectType());
        return deskInsertStrategy.addDesk(opDesk);
    }


    /**
     * 修改前台台账
     *
     * @param opDesk1 前台台账
     * @return 结果
     */
    @Override
    public int updateOpDesk(OpDeskVo opDesk1)
    {
        OpDeskVo opDeskVo = opDeskMapper.selectOpDeskByDeskId(opDesk1.getDeskId());
        DeskInsertStrategy instance = HandlerDeskContext.getInstance(opDeskVo.getProjectType());
        return instance.updateDesk(opDesk1,opDeskVo);
    }

    /**
     * 批量删除前台台账
     *
     * @param deskIds 需要删除的前台台账主键
     * @return 结果
     */
    @Override
    public int deleteOpDeskByDeskIds(Long[] deskIds)
    {
        AtomicInteger atomicInteger = new AtomicInteger();
        for (Long deskId : deskIds) {
            atomicInteger.addAndGet(deleteOpDeskByDeskId(deskId));
        }
        return atomicInteger.get();
    }

    /**
     * 删除前台台账信息
     *
     * @param deskId 前台台账主键
     * @return 结果
     */
    @Override
    public int deleteOpDeskByDeskId(Long deskId)
    {
        OpDeskVo opDeskVo = opDeskMapper.selectOpDeskByDeskId(deskId);
        DeskInsertStrategy instance = HandlerDeskContext.getInstance(opDeskVo.getProjectType());
        return instance.deleteDesk(deskId);
    }

    /**
     * 是否为最后一个流水号
     *
     * @param deskId 台账id
     * @return 是否
     */
    @Override
    public Boolean judgmentIsLast(Long deskId) {
        OpDeskVo opDeskVo = opDeskMapper.selectOpDeskByDeskId(deskId);
        if (opDeskVo.getIsInterpolation()!=null&& DeskConstants.DESK_IS_INTERPOLATION.equals(opDeskVo.getIsInterpolation())) {
            return false;
        }
        LocalDate entrustmentTime = opDeskVo.getEntrustmentTime();
        LocalDate firstTime = entrustmentTime.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastTime = entrustmentTime.with(TemporalAdjusters.lastDayOfMonth());
        opDeskVo.setFirstTime(firstTime);
        opDeskVo.setLastTime(lastTime);
        Integer integer = opDeskMapper.selectSizeWithVo(opDeskVo);
        return !integer.equals(0);
    }

    @Override
    public void saveAll(List<OpDeskVo> opDeskVos) {
        R<List<SysDept>> listR = remoterDeptService.opList(SecurityConstants.INNER);
        if (listR.getCode() != HttpStatus.SUCCESS) {
            throw new GlobalException(listR.getMsg());
        }
        opDeskVos.forEach(item -> save(item,listR.getData()));
    }

    @Override
    public int updateOpDeskAll(OpDeskVo opDesk, Long[] deskIds) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        Arrays.stream(deskIds).forEach(item -> {
            OpDeskVo opDeskVo = opDeskMapper.selectOpDeskByDeskId(item);
            if (opDesk.getVisitDeptId().isEmpty()) {
                opDesk.setVisitDeptId(opDesk.getVisitDeptId());
            }
            if (opDesk.getEditDeptId().isEmpty()) {
                opDesk.setEditDeptId(opDesk.getEditDeptId());
            }
            //复制属性
            BeanUtil.copyProperties(opDesk,opDeskVo, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
            atomicInteger.addAndGet(this.updateOpDesk(opDeskVo));
        });
        return atomicInteger.get();
    }

    @Override
    public int updateOpDeskOne(OpDeskVo opDesk) {
        if (opDesk.getDetectAmount()!=null&&opDesk.getDetectAmount()>0) {
            OpDetectMsg opDetectMsg = new OpDetectMsg();
            opDetectMsg.setDeskId(opDesk.getDeskId());
            opDetectMsg.setDetectAmount(opDesk.getDetectAmount());
            opDetectMsgService.updateOpDetectMsg(opDetectMsg,Boolean.FALSE);
        }
        return opDeskMapper.updateOpDeskVoOne(opDesk);
    }



    public void save(OpDeskVo opDeskVo,List<SysDept> list) {

        if (opDeskVo.getIsImport() == null || !DeskConstants.DESK_IS_INTERPOLATION.equals(opDeskVo.getIsImport())) {
            if (DeskConstants.DESK_IS_UPDATE.equals(opDeskVo.getIsUpdate())) {
                updateOpDeskByImport(opDeskVo);
            }
            return;
        }
        if (opDeskVo.getEntrustmentTime() == null) {
            throw new GlobalException("委托日期未填写");
        }
        LocalDate now = LocalDate.now();
        if (opDeskVo.getIssueTime()!=null&&!now.isAfter(opDeskVo.getIssueTime())&&!now.isEqual(opDeskVo.getIssueTime())) {
            throw new GlobalException("发单日期不能是未来");
        }
        if (opDeskVo.getOrderTime()!=null&&!now.isAfter(opDeskVo.getOrderTime())&&!now.isEqual(opDeskVo.getOrderTime())) {
            throw new GlobalException("下单日期不能是未来");
        }
        if (opDeskVo.getProjectType() == null || DeskInsertType.getEnum(opDeskVo.getProjectType()) == null) {
            throw new GlobalException("工程类别未填写");
        }
        if (opDeskVo.getProgramType() == null) {
            throw new GlobalException("检测类别未填写");
        }
        if (StringUtils.isEmpty(opDeskVo.getParameterCode())) {
            throw new GlobalException("参数代码未填写");
        }
        if (opParameterService.searchCode(opDeskVo)) {
            throw new GlobalException("无此参数代码:"+opDeskVo.getParameterCode());
        }
        if (StringUtils.isEmpty(opDeskVo.getIsInterpolation())) {
            opDeskVo.setIsInterpolation("0");
        }
        opProjectMsgService.getIdByName(opDeskVo);
        opDeskVo.setCreateBy(SecurityUtils.getUsername());
        if (opDeskVo.getProjectId()==null) {
            throw new GlobalException(opDeskVo+"无法找到工程类型");
        }
        if (opDeskVo.getSampleAmount()==null) {
            opDeskVo.setSampleAmount(1);
        }

        if (opDeskVo.getEditDeptName() != null) {
            opDeskVo.setEditDeptId(Arrays.stream(opDeskVo.getEditDeptName().split(",")).map(p -> list.stream()
                    .filter(d -> d.getDeptName().equals(p))
                    .findFirst()
                    .map(SysDept::getDeptId))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList()));
        }

        if (opDeskVo.getVisitDeptName() != null) {
            opDeskVo.setVisitDeptId(Arrays.stream(opDeskVo.getVisitDeptName().split(",")).map(p -> list.stream()
                    .filter(d -> d.getDeptName().equals(p))
                    .findFirst()
                    .map(SysDept::getDeptId))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList()));
        }

        this.insertOpDesk(opDeskVo);
    }

    private void updateOpDeskByImport(OpDeskVo opDeskVo) {
        try {
            if (opDeskVo.getProjectType().equals(DeskConstants.SHUILI_23) || opDeskVo.getProjectType().equals(DeskConstants.SHUILI_24)) {
                opDeskVo.setRecordSerialNumber(opDeskVo.getRecordSerialNumber().split("~")[0]);
            } else if (opDeskVo.getProjectType().equals(DeskConstants.BAOJIAN)){
                opDeskVo.setRecordSerialNumber(opDeskVo.getRecordSerialNumber().substring(0,18));
            } else {
                opDeskVo.setRecordSerialNumber(opDeskVo.getRecordSerialNumber().substring(0,16));
            }
        } catch (Exception e) {
            throw new GlobalException("该报告编号："+opDeskVo.getReportSerialNumber()+"的样品记录编号出现问题");
        }

        OpDeskVo oldDeskVo = opDeskMapper.selectOpDeskVoByNumber(opDeskVo);
        BeanUtils.copyProperties(opDeskVo,oldDeskVo, "deskId");
        DeskInsertStrategy instance = HandlerDeskContext.getInstance(opDeskVo.getProjectType());
        instance.updateDeskByExcel(oldDeskVo);
    }
}
