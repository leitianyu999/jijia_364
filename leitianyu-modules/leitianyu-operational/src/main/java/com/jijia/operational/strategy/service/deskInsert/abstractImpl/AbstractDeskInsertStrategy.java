package com.jijia.operational.strategy.service.deskInsert.abstractImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.jijia.common.core.constant.SecurityConstants;
import com.jijia.common.core.exception.GlobalException;
import com.jijia.common.core.utils.DateUtils;
import com.jijia.common.core.web.domain.AjaxResult;
import com.jijia.common.security.utils.SecurityUtils;
import com.jijia.operational.domain.OpCalibrationMsg;
import com.jijia.operational.domain.OpDesk;
import com.jijia.operational.domain.OpDetectMsg;
import com.jijia.operational.domain.OpProjectMsg;
import com.jijia.operational.domain.vo.OpDeskVo;
import com.jijia.operational.mapper.OpDeskDeptMapper;
import com.jijia.operational.mapper.OpDeskMapper;
import com.jijia.operational.mapper.OpProjectMsgMapper;
import com.jijia.operational.service.*;
import com.jijia.operational.strategy.handler.HandlerDeskContext;
import com.jijia.operational.strategy.service.deskInsert.DeskInsertStrategy;
import com.jijia.operational.utils.constants.ConfigConstants;
import com.jijia.operational.utils.constants.DeptLevel;
import com.jijia.operational.utils.constants.DeskConstants;
import com.jijia.operational.utils.enums.IntType;
import com.jijia.system.api.RemoteConfigService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author leitianyu
 */
@Component
public abstract class AbstractDeskInsertStrategy implements DeskInsertStrategy {

    private static final String REPORT_CODE = "B-";


    @Resource
    private RemoteConfigService remoteConfigService;
    @Resource
    private OpDeskMapper opDeskMapper;
    @Resource
    private IOpDetectMsgService opDetectMsgService;
    @Resource
    private IOpProjectMsgService opProjectMsgService;
    @Resource
    private IOpCalibrationMsgService opCalibrationMsgService;
    @Resource
    private IOpDeskDeptService opDeskDeptService;
    @Resource
    private IOpParameterService opParameterService;
    @Resource
    private OpDeskDeptMapper opDeskDeptMapper;
    @Resource
    private OpProjectMsgMapper opProjectMsgMapper;



    /**
     * 判断是否超出额度
     *
     * @param opDeskVo 查询对象
     * @param lastDesk 最后一条数据
     * @return 是否为插号
     */
    public abstract String checkExceedNumber(OpDeskVo opDeskVo, OpDesk lastDesk);


    /**
     * 根据工程类型生成编号
     *
     * @param opProjectMsg 工程信息
     * @return 行数
     */
    @Override
    public int insertOpProjectMsg(OpProjectMsg opProjectMsg) {
        return opProjectMsgMapper.insertOpProjectMsg(opProjectMsg);
    }

    /**
     * 修改工程编号
     *
     * @param opProjectMsg 工程信息
     * @return 行数
     */
    @Override
    public int updateOpProjectMsg(OpProjectMsg opProjectMsg) {
        return opProjectMsgMapper.updateOpProjectMsg(opProjectMsg);
    }

    /**
     * 编号生成（默认）
     * @param opDeskVo 处理后的新增数据
     * @return 默认数据
     */
    @Override
    public int addDesk(OpDeskVo opDeskVo) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        //台账添加
        atomicInteger.addAndGet(deskAdd(opDeskVo));
        //判断是否有重复编号现象
        if (!opDeskVo.getProjectType().equals(DeskConstants.BAOJIAN) && opDeskVo.getRepeatNumber() != null && opDeskVo.getRepeatNumber() != 1) {
            for (int integer = 1; integer < opDeskVo.getRepeatNumber(); integer++) {
                opDeskVo.setReportSerialNumber(StringNumberAdd(opDeskVo.getReportSerialNumber(),1));
                if (opDeskVo.getProgramType().equals(DeskConstants.YUANCAI)) {
                    opDeskVo.setRecordSerialNumber(StringNumberAdd(opDeskVo.getRecordSerialNumber(),opDeskVo.getSampleAmount()));
                } else {
                    opDeskVo.setRecordSerialNumber(StringNumberAdd(opDeskVo.getRecordSerialNumber(),1));
                }
                //台账添加
                atomicInteger.addAndGet(deskAdd(opDeskVo));
            }
        }
        return atomicInteger.get();
    }





    @Override
    public OpDeskVo creatNumber(OpDeskVo opDeskVo) {
        return opDeskVo;
    }

    /**
     * 台账添加
     *
     * @param opDesk 台账
     * @return 行数
     */
    private int deskAdd (OpDeskVo opDesk) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        opDesk.setDeskId(null);
        opDesk.setCreateTime(DateUtils.getNowDate());
        //前台台账添加
        atomicInteger.addAndGet(opDeskMapper.insertOpDesk(opDesk));
        //部门权限添加
        if (opDesk.getEditDeptId()!=null && opDesk.getEditDeptId().size() > 0) {
            atomicInteger.addAndGet(opDeskDeptMapper.insertOpDeskDept(opDesk.getEditDeptId(),opDesk.getDeskId(), DeptLevel.EDIT_DEPT));
        }
        if (opDesk.getVisitDeptId()!=null && opDesk.getVisitDeptId().size() > 0) {
            atomicInteger.addAndGet(opDeskDeptMapper.insertOpDeskDept(opDesk.getVisitDeptId(),opDesk.getDeskId(),DeptLevel.VISIT_DEPT));
        }
        // 创建检测台账数据
        OpDetectMsg opDetectMsg = new OpDetectMsg();
        opDetectMsg.setDeskId(opDesk.getDeskId());
        if (opDesk.getEntrustStatus()!=null) {
            opDetectMsg.setEntrustStatus(opDesk.getEntrustStatus());
        }
        if (opDesk.getEntrustNote()!=null) {
            opDetectMsg.setEntrustNote(opDesk.getEntrustNote());
        }
        if (opDesk.getDetectName()!=null) {
            opDetectMsg.setDetectName(opDesk.getDetectName());
        }
        if (opDesk.getDetectEngineeringParts()!=null) {
            opDetectMsg.setDetectEngineeringParts(opDesk.getDetectEngineeringParts());
        }
        if (opDesk.getDetectAmount()!=null) {
            opDetectMsg.setDetectAmount(opDesk.getDetectAmount());
        }
        opDetectMsgService.insertOpDetectMsg(opDetectMsg);
        // 创建综合部台账数据
        OpCalibrationMsg opCalibrationMsg = new OpCalibrationMsg();
        opCalibrationMsg.setDeskId(opDesk.getDeskId());
        opCalibrationMsgService.insertOpCalibrationMsg(opCalibrationMsg);

        return atomicInteger.get();
    }

    /**
     * 重复编号添加
     *
     * @param stringNumber 编号
     * @param number 数量
     * @return 编号
     */
    private String StringNumberAdd(String stringNumber,int number) {
        int len = stringNumber.length();
        int num = Integer.parseInt(stringNumber.substring(len -4)) + number;
        return stringNumber.substring(0, len - 4) + String.format("%04d",num);
    }

    /**
     * 检查插号状态
     *
     * @param opDeskVo 新增数据
     */
    public void checkInterpolation(OpDeskVo opDeskVo) {

        //插号直接返回
        if (opDeskVo.getIsInterpolation().equals(DeskConstants.DESK_IS_INTERPOLATION)) {
            return;
        }

        //判断是否上传
        AjaxResult configKey = remoteConfigService.getConfigKey(ConfigConstants.CONFIG_PAST_TIME, SecurityConstants.INNER);
        if (configKey.isError()) {
            throw new GlobalException("配置日期信息错误，请立刻联系管理员！");
        }
        LocalDate past = LocalDate.parse(configKey.get(AjaxResult.MSG_TAG).toString());
        if (!opDeskVo.getEntrustmentTime().isAfter(past)) {
            opDeskVo.setIsInterpolation(DeskConstants.DESK_IS_INTERPOLATION);
            return;
        }

        //寻找最后一条数据
        OpDesk lastDesk = findLast(opDeskVo);
        if (lastDesk != null && lastDesk.getEntrustmentTime().isAfter(opDeskVo.getEntrustmentTime())) {
            opDeskVo.setIsInterpolation(DeskConstants.DESK_IS_INTERPOLATION);
            return;
        }
        //判断数量限制
        opDeskVo.setIsInterpolation(checkExceedNumber(opDeskVo,lastDesk));
    }


    /**
     * 寻找最后一条数据
     *
     * @param opDeskVo 新增数据
     * @return 最后一条的数据
     */
    public OpDesk findLast(OpDeskVo opDeskVo) {
        //添加月份条件
        opDeskVo.setFirstTime(opDeskVo.getEntrustmentTime().with(TemporalAdjusters.firstDayOfMonth()));
        opDeskVo.setLastTime(opDeskVo.getEntrustmentTime().with(TemporalAdjusters.lastDayOfMonth()));
        //返回数据
        return opDeskMapper.getLastOpDesk(opDeskVo);
    }


    /**
     * 生成共同报告编号
     * @param opDeskVo 新增对象
     * @param last 最后一条数据
     */
    public void createReportNumberAndEntrustNumber(OpDeskVo opDeskVo, OpDesk last) {
        createReportNumber(opDeskVo,last);
        creatEntrustNumber(opDeskVo,last);
    }

    public void createReportNumber(OpDeskVo opDeskVo, OpDesk last) {
        //创造计数器
        AtomicInteger atomicInteger = new AtomicInteger(0);
        //获取委托日期
        String entrustment = String.valueOf(opDeskVo.getEntrustmentTime());
        //查询编号台账
        OpProjectMsg opProjectMsg = opProjectMsgService.selectOpProjectMsgByProjectId(opDeskVo.getProjectId());

        StringBuilder reportBuilder = new StringBuilder(opDeskVo.getReportSerialNumber());
        //判断插号情况
        if (opDeskVo.getIsInterpolation().equals(DeskConstants.DESK_IS_INTERPOLATION)) {
            //插号
            reportBuilder.append(opProjectMsg.getProjectChahaoCode());
        } else {
            //不插号
            reportBuilder.append(opProjectMsg.getProjectCode());
        }
        //获取报告编号尾数
        atomicInteger.set(getReportSerialNumberLast(opDeskVo));
        //拼接字符
        reportBuilder.append(REPORT_CODE)
                .append(opDeskVo.getParameterCode())
                .append(entrustment, 2, 4)
                .append(entrustment, 5, 7)
                .append(String.format("%04d",atomicInteger.incrementAndGet()));
        //报告编号输入
        opDeskVo.setReportSerialNumber(reportBuilder.toString());
    }

    public void creatEntrustNumber(OpDeskVo opDeskVo, OpDesk last) {
        //创造计数器
        AtomicInteger atomicInteger = new AtomicInteger(0);
        //获取委托日期
        String entrustment = String.valueOf(opDeskVo.getEntrustmentTime());
        //获取委托编号尾数
        if (last != null) {
            atomicInteger.set(Integer.parseInt(last.getEntrustSerialNumber().substring(last.getEntrustSerialNumber().length() - 4)));
        } else if (opDeskVo.getIsInterpolation().equals(DeskConstants.DESK_IS_INTERPOLATION)) {
            atomicInteger.set(opParameterService.selectMaxNumber(opDeskVo.getParameterCode()));
        }

        String entrustBuilder = opDeskVo.getEntrustSerialNumber() + opDeskVo.getParameterCode() +
                entrustment.substring(2, 4) +
                entrustment.substring(5, 7) +
                String.format("%04d", atomicInteger.incrementAndGet());
        opDeskVo.setEntrustSerialNumber(entrustBuilder);
    }


    /**
     * 删除台账
     *
     * @param deskId 台账id
     * @return 行数
     */
    @Override
    public int deleteDesk(Long deskId) {
        //获取数据
        OpDeskVo opDeskVo = opDeskMapper.selectOpDeskByDeskId(deskId);
        //创建计数器
        AtomicInteger atomicInteger = new AtomicInteger(0);
        //是否为插号
        if (opDeskVo.getIsInterpolation().equals(DeskConstants.DESK_IS_INTERPOLATION)) {
            atomicInteger.addAndGet(opDeskMapper.deleteOpDeskByDeskId(deskId));
            atomicInteger.addAndGet(opCalibrationMsgService.deleteOpCalibrationMsgByDeskId(deskId));
            atomicInteger.addAndGet(opDetectMsgService.deleteOpDetectMsgByDeskId(deskId));
            atomicInteger.addAndGet(opDeskDeptService.deleteDeptByDeskId(deskId));
            return atomicInteger.get();
        }

        List<OpDesk> afterDeskList = getAfterDeskList(opDeskVo);
        if (afterDeskList.size() > 0) {
            Integer sampleAmount = opDeskVo.getSampleAmount();
            int reportCast = opDeskVo.getReportSerialNumber().length() - 4;
            int recordCast = opDeskVo.getRecordSerialNumber().length() - 4;
            for (OpDesk deskVo : afterDeskList) {
                StringBuilder stringBuilder = new StringBuilder();
                AtomicInteger atomicInteger1 = new AtomicInteger();
                String reportSerialNumber = deskVo.getReportSerialNumber();
                atomicInteger1.set(Integer.parseInt(reportSerialNumber.substring(reportCast)));
                Integer i1 = atomicInteger1.decrementAndGet();
                stringBuilder.append(reportSerialNumber, 0, reportCast).append(String.format("%04d",!i1.equals(0) ? i1 : 1));
                deskVo.setReportSerialNumber(stringBuilder.toString());
                stringBuilder.setLength(0);

                String recordSerialNumber = deskVo.getRecordSerialNumber();
                int i2 = Integer.parseInt(recordSerialNumber.substring(recordCast)) - sampleAmount;
                stringBuilder.append(recordSerialNumber, 0, recordCast).append(String.format("%04d",i2));
                deskVo.setRecordSerialNumber(stringBuilder.toString());
                stringBuilder.setLength(0);

                String entrustSerialNumber = deskVo.getEntrustSerialNumber();
                atomicInteger1.set(Integer.parseInt(entrustSerialNumber.substring(recordCast)));
                int i3 = atomicInteger1.decrementAndGet();
                stringBuilder.append(entrustSerialNumber, 0, recordCast).append(String.format("%04d",i3));
                deskVo.setEntrustSerialNumber(stringBuilder.toString());
                atomicInteger.addAndGet(opDeskMapper.updateOpDesk(deskVo));
            }
        }

        atomicInteger.addAndGet(opDeskMapper.deleteOpDeskByDeskId(deskId));
        atomicInteger.addAndGet(opCalibrationMsgService.deleteOpCalibrationMsgByDeskId(deskId));
        atomicInteger.addAndGet(opDetectMsgService.deleteOpDetectMsgByDeskId(deskId));
        atomicInteger.addAndGet(opDeskDeptService.deleteDeptByDeskId(deskId));

        return atomicInteger.get();
    }



    /**
     * 根据策略修改台账信息
     *
     * @param opDeskVo 修改信息
     * @param oldDesk 旧信息
     */
    @Override
    public int updateDesk(OpDeskVo opDeskVo,OpDeskVo oldDesk) {
        opDeskVo.setUpdateTime(DateUtils.getNowDate());
        //查询是否更改编号相关信息
        if (checkChangeImp(opDeskVo,oldDesk)) {
            if (oldDesk.getIsInterpolation().equals(DeskConstants.DESK_IS_INTERPOLATION)) {
                //插号流程
                chahaoDeskCheck(opDeskVo,oldDesk);
            }else {
                if (judgmentIsLast(opDeskVo.getDeskId())) {
                    return deleteDesk(opDeskVo,oldDesk);
                }
                //不插号的非最后一个流水号流程
                deskCheck(opDeskVo,oldDesk);
            }
        }
        AtomicInteger atomicInteger = new AtomicInteger(0);
        //查询是否更改分配部门
        atomicInteger.addAndGet(checkDeptId(opDeskVo));
        //更新检测台账内容
        atomicInteger.addAndGet(updateDetectMsg(opDeskVo));
        //更新前台台账内容
        atomicInteger.addAndGet(opDeskMapper.updateOpDeskVo(opDeskVo));
        return atomicInteger.get();
    }


    @Override
    public void updateDeskByExcel(OpDeskVo opDeskVo) {
        opDeskVo.setUpdateTime(DateUtils.getNowDate());
        opDeskMapper.updateDeskByExcel(opDeskVo);
        //更新检测台账内容
        updateDetectMsg(opDeskVo);
    }

    /**
     * 插号修改检查
     */
    public void chahaoDeskCheck (OpDeskVo opDeskVo,OpDeskVo oldDesk) {
        if (DeskConstants.YUANCAI.equals(opDeskVo.getProgramType()) && !oldDesk.getDetectAmount().equals(opDeskVo.getDetectAmount())) {
                throw new GlobalException("插号的原材不参与修改数量");
        }
        if (!opDeskVo.getParameterCode().equals(oldDesk.getParameterCode())) {
            throw new GlobalException("插号不参与修改参数代码");
        }
        if (!opDeskVo.getIsInterpolation().equals(oldDesk.getIsInterpolation())) {
            throw new GlobalException("插号不参与修改插号属性");
        }
        if (!opDeskVo.getProjectId().equals(oldDesk.getProjectId())) {
            throw new GlobalException("插号不参与修改工程名称与委托单位");
        }
        if (!opDeskVo.getSampleAmount().equals(oldDesk.getSampleAmount())) {
            throw new GlobalException("插号不参与修改样品数量");
        }
        if (!opDeskVo.getEntrustmentTime().getMonth().equals(oldDesk.getEntrustmentTime().getMonth()) ||
                opDeskVo.getEntrustmentTime().getYear()!= oldDesk.getEntrustmentTime().getYear()) {
            throw new GlobalException("插号不参与月份或年份");
        }
    }

    /**
     * 不插号修改流程检查
     */
    public void deskCheck (OpDeskVo opDeskVo,OpDeskVo oldDesk) {
            if (!opDeskVo.getParameterCode().equals(oldDesk.getParameterCode())) {
                throw new GlobalException("非最后一个流水号不参与修改参数代码");
            }
            if (!opDeskVo.getIsInterpolation().equals(oldDesk.getIsInterpolation())) {
                throw new GlobalException("非最后一个流水号不参与修改插号属性");
            }
            if (!opDeskVo.getProjectId().equals(oldDesk.getProjectId())) {
                throw new GlobalException("非最后一个流水号不参与修改工程名称与委托单位");
            }
            if (!opDeskVo.getSampleAmount().equals(oldDesk.getSampleAmount())) {
                throw new GlobalException("非最后一个流水号不参与修改数量");
            }
            if (!opDeskVo.getEntrustmentTime().getMonth().equals(oldDesk.getEntrustmentTime().getMonth()) ||
                    opDeskVo.getEntrustmentTime().getYear()!= oldDesk.getEntrustmentTime().getYear()) {
                throw new GlobalException("非最后一个流水号不参与月份或年份");
            }
            //查询更改日期是否影响编号
            int a;
            if (opDeskVo.getEntrustmentTime().isAfter(oldDesk.getEntrustmentTime())) {
                opDeskVo.setFirstTime(oldDesk.getEntrustmentTime());
                opDeskVo.setLastTime(opDeskVo.getEntrustmentTime().plusDays(-1));
                a = opDeskMapper.judgmentChangeLast(opDeskVo);
            } else {
                opDeskVo.setFirstTime(opDeskVo.getEntrustmentTime().plusDays(1));
                opDeskVo.setLastTime(oldDesk.getEntrustmentTime());
                a = opDeskMapper.judgmentChangefirst(opDeskVo);
            }
            if (a != 0) {
                throw new GlobalException("当前修改会改变多个数据编号");
            }
    }

    /**
     * 删除并重新排号
     */
    public int deleteDesk(OpDeskVo opDeskVo,OpDeskVo oldDesk) {
        //删除原有数据
        opDeskMapper.deleteOpDeskByDeskId(opDeskVo.getDeskId());
        opCalibrationMsgService.deleteOpCalibrationMsgByDeskId(opDeskVo.getDeskId());
        opDetectMsgService.deleteOpDetectMsgByDeskId(opDeskVo.getDeskId());
        opDeskDeptService.deleteDeptByDeskId(opDeskVo.getDeskId());
        //复制属性
        BeanUtil.copyProperties(opDeskVo,oldDesk, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        //属性清空
        oldDesk.setDeskId(null);
        oldDesk.setCreateTime(DateUtils.getNowDate());
        oldDesk.setCreateBy(SecurityUtils.getUsername());

        //新增前台数据
        AtomicInteger atomicInteger = new AtomicInteger();
        DeskInsertStrategy instance = HandlerDeskContext.getInstance(oldDesk.getProjectType());
        instance.creatNumber(oldDesk);
        atomicInteger.addAndGet(opDeskMapper.insertOpDesk(oldDesk));
        //新增检测台账
        OpDetectMsg opDetectMsg = new OpDetectMsg();
        addMsg(oldDesk,opDetectMsg);
        opDetectMsg.setDeskId(oldDesk.getDeskId());
        atomicInteger.addAndGet(opDetectMsgService.insertOpDetectMsg(opDetectMsg));
        //新增综合台账
        OpCalibrationMsg opCalibrationMsg = new OpCalibrationMsg();
        opCalibrationMsg.setDeskId(oldDesk.getDeskId());
        atomicInteger.addAndGet(opCalibrationMsgService.insertOpCalibrationMsg(opCalibrationMsg));
        //更新检测台账内容
        atomicInteger.addAndGet(updateDetectMsg(oldDesk));
        return atomicInteger.get();
    }

    //检查是否更改编号相关
    public Boolean checkChangeImp(OpDeskVo opDeskVo,OpDeskVo oldDesk) {

        if (!opDeskVo.getProjectId().equals(oldDesk.getProjectId())) {
            return true;
        }
        if (!opDeskVo.getSampleAmount().equals(oldDesk.getSampleAmount())) {
            return true;
        }
        if (!opDeskVo.getEntrustmentTime().equals(oldDesk.getEntrustmentTime())) {
            return true;
        }
        if (!opDeskVo.getParameterCode().equals(oldDesk.getParameterCode())) {
            return true;
        }
        if (!opDeskVo.getProgramType().equals(oldDesk.getProgramType())) {
            return true;
        }
        return !opDeskVo.getIsInterpolation().equals(oldDesk.getIsInterpolation());
    }


    /**
     * 查询是否为最后一个流水号
     * @param deskId 台账id
     * @return bool函数
     */
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


    /**
     * 是否更改分配部门
     * @param opDeskVo 修改信息
     * @return 行数
     */
    public int checkDeptId (OpDeskVo opDeskVo) {
            return opDeskDeptService.updateDeptByOp(opDeskVo.getDeskId(),opDeskVo.getEditDeptId(),opDeskVo.getVisitDeptId());
    }

    /**
     * 更改检测台账信息
     * @param opDeskVo 修改数据
     * @return 行数
     */
    public int updateDetectMsg(OpDeskVo opDeskVo) {

        OpDetectMsg opDetectMsg = new OpDetectMsg();
        addMsg(opDeskVo,opDetectMsg);
        return opDetectMsgService.updateOpDetectMsg(opDetectMsg,Boolean.FALSE);

    }

    public void addMsg(OpDeskVo opDeskVo,OpDetectMsg opDetectMsg) {
        opDetectMsg.setDeskId(opDeskVo.getDeskId());
        if (opDeskVo.getEntrustStatus()!=null) {
            opDetectMsg.setEntrustStatus(opDeskVo.getEntrustStatus());
        }
        if (opDeskVo.getEntrustNote()!=null) {
            opDetectMsg.setEntrustNote(opDeskVo.getEntrustNote());
        }
        if (opDeskVo.getDetectName()!=null) {
            opDetectMsg.setDetectName(opDeskVo.getDetectName());
        }
        if (opDeskVo.getDetectEngineeringParts()!=null) {
            opDetectMsg.setDetectEngineeringParts(opDeskVo.getDetectEngineeringParts());
        }
        if (opDeskVo.getDetectAmount()!=null) {
            opDetectMsg.setDetectAmount(opDeskVo.getDetectAmount());
        }

    }


    public List<OpDesk> getAfterDeskList(OpDeskVo opDeskVo) {
        LocalDate entrustmentTime = opDeskVo.getEntrustmentTime();
        LocalDate firstTime = entrustmentTime.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastTime = entrustmentTime.with(TemporalAdjusters.lastDayOfMonth());
        opDeskVo.setFirstTime(firstTime);
        opDeskVo.setLastTime(lastTime);
        return opDeskMapper.selectOpDeskListWithVo(opDeskVo);
    }

    public void checkTime(AjaxResult configKey,LocalDate localDate) {
        if (configKey.isError()) {
            throw new GlobalException("配置日期信息错误，请立刻联系管理员！");
        }
        LocalDate past = LocalDate.parse(configKey.get(AjaxResult.MSG_TAG).toString());
        if (!localDate.isAfter(past)) {
            throw new GlobalException("委托日期不能晚于"+configKey.get(AjaxResult.MSG_TAG).toString());
        }
    }

    public Integer getReportSerialNumberLast (OpDeskVo opDesk) {
        String thisProjectNum = opDeskMapper.getThisProjectNum(opDesk);
        return thisProjectNum!=null?Integer.parseInt(thisProjectNum.substring(thisProjectNum.length()-4)):0;
    }

    /**
     * 生成工程编号
     */
    public void creatProjectCode(OpProjectMsg opProjectMsg,OpProjectMsg last) {
        AtomicInteger atomicInteger = new AtomicInteger();
        String projectCode = last.getProjectCode();
        String projectChahaoCode = last.getProjectChahaoCode();
        Integer integer = Integer.valueOf(projectCode.substring(1,4));
        Integer i = integer.compareTo(IntType.LAST.getCode());
        //判断编号是不是999
        if (i.equals(IntType.ZERO.getCode())) {
            char code = projectCode.charAt(IntType.ZERO.getCode());
            char chahaoCode = projectChahaoCode.charAt(IntType.ZERO.getCode());
            atomicInteger.set(code);
            char codeNest = (char) atomicInteger.incrementAndGet();
            atomicInteger.set(chahaoCode);
            char chahaoCodeNest = (char) atomicInteger.incrementAndGet();
            String projectCodeNext = codeNest + DeskConstants.NUMBER_001;
            String projectChahaoCodeNext = chahaoCodeNest + DeskConstants.NUMBER_001;
            opProjectMsg.setProjectCode(projectCodeNext);
            opProjectMsg.setProjectChahaoCode(projectChahaoCodeNext);
        } else {
            atomicInteger.set(integer);
            int increment = atomicInteger.incrementAndGet();
            String codeStr = String.format(DeskConstants.DESK_FORMAT_THREE,increment);
            char code = projectCode.charAt(IntType.ZERO.getCode());
            char chahaoCode = projectChahaoCode.charAt(IntType.ZERO.getCode());
            String projectCodeNext = code + codeStr;
            String projectChahaoCodeNext = chahaoCode + codeStr;
            opProjectMsg.setProjectCode(projectCodeNext);
            opProjectMsg.setProjectChahaoCode(projectChahaoCodeNext);
        }
    }

}
