package com.jijia.operational.service.impl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import com.jijia.common.core.constant.HttpStatus;
import com.jijia.common.core.constant.SecurityConstants;
import com.jijia.common.core.domain.R;
import com.jijia.common.core.exception.GlobalException;
import com.jijia.common.core.utils.DateUtils;
import com.jijia.common.core.utils.StringUtils;
import com.jijia.common.security.utils.SecurityUtils;
import com.jijia.operational.domain.OpCalibrationMsg;
import com.jijia.operational.domain.OpDetectMsg;
import com.jijia.operational.domain.info.OpDetectMsgInfo;
import com.jijia.operational.domain.vo.OpDetectMsgVo;
import com.jijia.operational.mapper.OpDetectMsgMapper;
import com.jijia.operational.service.IOpCalibrationMsgService;
import com.jijia.operational.service.IOpDeskDeptService;
import com.jijia.operational.service.IOpDetectMsgService;
import com.jijia.operational.utils.constants.DeskConstants;
import com.jijia.system.api.RemoteUserService;
import com.jijia.system.api.RemoterDeptService;
import com.jijia.system.api.domain.SysDept;
import com.jijia.system.api.model.LoginUser;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * 检测台账Service业务层处理
 *
 * @author leitianyu
 * @date 2023-02-04
 */
@Service
public class OpDetectMsgServiceImpl implements IOpDetectMsgService
{
    private final OpDetectMsgMapper opDetectMsgMapper;
    private final IOpDeskDeptService opDeskDeptService;
    private final IOpCalibrationMsgService opCalibrationMsgService;
    private final RemoterDeptService remoterDeptService;
    private final RemoteUserService remoteUserService;

    public OpDetectMsgServiceImpl(OpDetectMsgMapper opDetectMsgMapper, IOpDeskDeptService opDeskDeptMapper, IOpCalibrationMsgService opCalibrationMsgService, RemoterDeptService remoterDeptService, RemoteUserService remoteUserService) {
        this.opDetectMsgMapper = opDetectMsgMapper;
        this.opDeskDeptService = opDeskDeptMapper;
        this.opCalibrationMsgService = opCalibrationMsgService;
        this.remoterDeptService = remoterDeptService;
        this.remoteUserService = remoteUserService;
    }

    /**
     * 查询检测台账
     *
     * @param deskId 检测台账主键
     * @return 检测台账
     */
    @Override
    public OpDetectMsg selectOpDetectMsgByDeskId(Long deskId,Boolean bool)
    {
        R<List<SysDept>> listR = remoterDeptService.opList(SecurityConstants.INNER);
        if (listR.getCode() != HttpStatus.SUCCESS) {
            throw new GlobalException(listR.getMsg());
        }
        return opDetectMsgMapper.selectOpDetectMsgByDeskId(deskId,listR.getData(), SecurityUtils.getUserId(), bool);
    }

    /**
     * 查询检测台账列表
     *
     * @param opDetectMsg 检测台账
     * @return 检测台账
     */
    @Override
    public List<OpDetectMsgVo> selectOpDetectMsgList(OpDetectMsgInfo opDetectMsg, Boolean bool, Boolean boo)
    {
        List<OpDetectMsgVo> opDetectMsgslist;
        if (bool) {
            R<List<SysDept>> listR = remoterDeptService.opList(SecurityConstants.INNER);
            if (listR.getCode() != HttpStatus.SUCCESS || listR.getData() == null) {
                throw new GlobalException(listR.getMsg());
            }
            if (listR.getData().size() > 0) {
                opDetectMsgslist = opDetectMsgMapper.selectOpDetectMsgList(opDetectMsg,listR.getData(), SecurityUtils.getUserId(),Boolean.TRUE);
            } else {
                opDetectMsgslist = opDetectMsgMapper.selectOpDetectMsgListByUserId(opDetectMsg,SecurityUtils.getLoginUser().getSysUser());
            }
        } else {
            opDetectMsgslist = opDetectMsgMapper.selectOpDetectMsgList(opDetectMsg,null, SecurityUtils.getUserId(),Boolean.FALSE);
        }
        if (boo) {
            opDetectMsgslist.forEach(item -> {
                if (item.getUserId()!=null) {
                    if (item.getUserId().equals(SecurityUtils.getUserId())) {
                        item.setUserName(SecurityUtils.getUsername());
                    } else {
                        R<String> infoName = remoteUserService.getInfoName(item.getUserId(), SecurityConstants.INNER);
                        if (infoName.getCode() == HttpStatus.SUCCESS ) {
                            item.setUserName(infoName.getData());
                        }
                    }

                }
            });
        }
        return opDetectMsgslist;
    }

    /**
     * 新增检测台账
     *
     * @param opDetectMsg 检测台账
     * @return 结果
     */
    @Override
    public int insertOpDetectMsg(OpDetectMsg opDetectMsg)
    {
        opDetectMsg.setCreateTime(DateUtils.getNowDate());
        return opDetectMsgMapper.insertOpDetectMsg(opDetectMsg);
    }

    /**
     * 修改检测台账
     *
     * @param opDetectMsg 检测台账
     * @return 结果
     */
    @Override
    public int updateOpDetectMsg(OpDetectMsg opDetectMsg, Boolean bool)
    {
        opDetectMsg.setUpdateTime(DateUtils.getNowDate());
        OpDetectMsg opDetectMsg1;
        if (bool) {
            R<List<SysDept>> listR = remoterDeptService.opList(SecurityConstants.INNER);
            if (listR.getCode() != HttpStatus.SUCCESS && listR.getData()!=null && listR.getData().isEmpty()) {
                throw new GlobalException(listR.getMsg());
            }
            opDetectMsg1 = opDetectMsgMapper.selectOpDetectMsgByDeskIdAndDept(opDetectMsg.getDeskId(), listR.getData(), SecurityUtils.getUserId(), Boolean.TRUE);
            if (opDetectMsg1 == null) {
                throw new GlobalException("无权限");
            }
        }else {
            opDetectMsg1 = opDetectMsgMapper.selectOpDetectMsgByDeskId(opDetectMsg.getDeskId(), null, null, Boolean.FALSE);
        }
        opDetectMsg.setStatus(opDetectMsg1.getStatus());
        if (opDetectMsg.getStatus().equals(0) && StringUtils.isNotEmpty(opDetectMsg.getDetectPersonnel())) {
            opDetectMsg.setStatus(1);
            OpCalibrationMsg calibrationMsg = new OpCalibrationMsg();
            calibrationMsg.setDeskId(opDetectMsg.getDeskId());
            opCalibrationMsgService.updateStatusToOne(calibrationMsg);
        }
        return opDetectMsgMapper.updateOpDetectMsg(opDetectMsg);
    }

    @Override
    public int updateOpDetectMsg(OpDetectMsg opDetectMsg, Long[] deskIds, Boolean aTrue) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        if (Objects.equals(opDetectMsg.getDetectTime(), "null")) {
            opDetectMsg.setDetectTime(null);
        }
        Arrays.stream(deskIds).forEach(item -> {
            opDetectMsg.setDeskId(item);
            atomicInteger.addAndGet(updateOpDetectMsg(opDetectMsg,aTrue));
        });
        return atomicInteger.get();
    }

    @Override
    public int updateOpDetectMsgNote(OpDetectMsg opDetectMsg) {
        return opDetectMsgMapper.updateOpDetectMsg(opDetectMsg);
    }

    /**
     * 批量删除检测台账
     *
     * @param deskIds 需要删除的检测台账主键
     * @return 结果
     */
    @Override
    public int deleteOpDetectMsgByDeskIds(Long[] deskIds)
    {
        return opDetectMsgMapper.deleteOpDetectMsgByDeskIds(deskIds);
    }

    /**
     * 删除检测台账信息
     *
     * @param deskId 检测台账主键
     * @return 结果
     */
    @Override
    public int deleteOpDetectMsgByDeskId(Long deskId)
    {
        return opDetectMsgMapper.deleteOpDetectMsgByDeskId(deskId);
    }

    @Override
    public void saveAll(List<OpDetectMsgVo> opDetectMsg,Boolean bool) {
        if (bool) {
            R<List<SysDept>> listR = remoterDeptService.opList(SecurityConstants.INNER);
            if (listR.getCode() != HttpStatus.SUCCESS && listR.getData()!=null && listR.getData().isEmpty()) {
                throw new GlobalException(listR.getMsg());
            }
            for (OpDetectMsgVo item : opDetectMsg) {
                save(item, listR.getData(), Boolean.TRUE);
            }
        } else {
            for (OpDetectMsgVo item : opDetectMsg) {
                save(item, null, Boolean.FALSE);
            }
        }
    }

    @Override
    public int updateDeptId(Long[] deskIds, List<Long> editDept, List<Long> visitDept) {
        if (deskIds!=null && deskIds.length!=0) {
            AtomicInteger atomicInteger = new AtomicInteger(0);
            for (Long deskId : deskIds) {
               atomicInteger.addAndGet( opDeskDeptService.updateDeptByOp(deskId,editDept,visitDept));
            }
            return atomicInteger.get();
        }
        throw new GlobalException("参数缺失");
    }

    @Override
    public int updateMsgUser(Long[] deskIds, Long userId) {
        R<List<SysDept>> listR = remoterDeptService.opList(SecurityConstants.INNER);
        if (listR.getCode() != HttpStatus.SUCCESS && listR.getData()!=null && listR.getData().isEmpty()) {
            throw new GlobalException(listR.getMsg());
        }
        if (deskIds!=null&&deskIds.length!=0&&userId!=null) {
            return opDetectMsgMapper.updateMsgUser(deskIds,userId, LocalDate.now(),listR.getData());
        }
        throw new GlobalException("参数缺失");
    }

    @Override
    public int updateMsgUserAll(Long[] deskIds, Long userId) {
        if (deskIds!=null&&deskIds.length!=0&&userId!=null) {
            return opDetectMsgMapper.updateMsgUserAll(deskIds,userId, LocalDate.now());
        }
        throw new GlobalException("参数缺失");
    }


    public void save(OpDetectMsgVo opDetectMsgVO,List<SysDept> deptId,Boolean bool) {


            if (opDetectMsgVO.getEntrustmentTime()!=null) {
                LocalDate now = LocalDate.now();
                if (opDetectMsgVO.getCompleteReportDate()!=null&&!now.isAfter(opDetectMsgVO.getCompleteReportDate())&&!now.isEqual(opDetectMsgVO.getCompleteReportDate())) {
                    throw new GlobalException("出报告日期不能是未来");
                }
                if (opDetectMsgVO.getReportDate()!=null&&!now.isAfter(opDetectMsgVO.getReportDate())&&!now.isEqual(opDetectMsgVO.getReportDate())) {
                    throw new GlobalException("报告日期不能是未来");
                }
                OpDetectMsg opDetectMsg;
                try {
                    if (opDetectMsgVO.getRecordSerialNumber().startsWith(DeskConstants.DESK_NUMBER_SL)) {
                        opDetectMsgVO.setRecordSerialNumber(opDetectMsgVO.getRecordSerialNumber().split("~")[0]);
                    } else if (opDetectMsgVO.getRecordSerialNumber().startsWith(DeskConstants.DESK_NUMBER_SLJ)){
                        opDetectMsgVO.setRecordSerialNumber(opDetectMsgVO.getRecordSerialNumber().substring(0,18));
                    } else {
                        opDetectMsgVO.setRecordSerialNumber(opDetectMsgVO.getRecordSerialNumber().substring(0,16));
                    }
                } catch (Exception e) {
                    throw new GlobalException("该报告编号："+opDetectMsgVO.getReportSerialNumber()+"的样品记录编号出现问题");
                }
                LoginUser loginUser = SecurityUtils.getLoginUser();
                try {
                    if (bool && deptId.size() < 1) {
                        opDetectMsg = opDetectMsgMapper.selectOneByUserId(opDetectMsgVO,SecurityUtils.getLoginUser());
                    } else {
                        opDetectMsg = opDetectMsgMapper.selectOne(opDetectMsgVO,deptId,SecurityUtils.getUserId(),bool);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                    throw new GlobalException("该报告编号："+opDetectMsgVO.getReportSerialNumber()+"出现问题，请检查");
                }

                if (opDetectMsg!=null) {
                    BeanUtils.copyProperties(opDetectMsgVO,opDetectMsg, "deskId");
                    this.updateOpDetectMsg(opDetectMsg, bool);
                } else {
                    throw new GlobalException("该报告编号："+opDetectMsgVO.getReportSerialNumber()+"未查询到相关数据");
                }
            }


    }
}

