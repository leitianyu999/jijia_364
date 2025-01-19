package com.jijia.operational.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jijia.common.core.web.domain.BaseEntity;
import com.jijia.operational.utils.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import java.time.LocalDate;
import java.util.List;

/**
 * 前台台账对象 op_desk
 *
 * @author leitianyu
 * @date 2023-02-04
 */
@ExcelIgnoreUnannotated
public class OpSettleVo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 工程id */
    private Long deskId;

    /** 工程类别 */
    @ExcelProperty(value = {"前台台账数据","工程类别"},converter = ProjectTypeConverter.class)
    private Integer projectType;

    /** 发单日期 */
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    @ExcelProperty(value = {"前台台账数据","发单日期"},converter = LocalDateConverter.class)
    private LocalDate issueTime;

    /** 下单日期 */
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    @ExcelProperty(value = {"前台台账数据","下单日期"},converter = LocalDateConverter.class)
    private LocalDate orderTime;

    /** 下单人 */
    @ExcelProperty(value = {"前台台账数据","下单人"})
    private String publisher;

    /** 业务经理 */
    @ExcelProperty(value = {"前台台账数据","业务经理"})
    private String businessManager;

    /** 委托日期 */
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    @ExcelProperty(value = {"前台台账数据","委托日期"},converter = LocalDateConverter.class)
    private LocalDate entrustmentTime;

    private LocalDate firstTime;

    private LocalDate lastTime;


    /** 委托单位 */
    @ExcelProperty(value = {"前台台账数据","委托单位"})
    private String entrustmentUnit;

    /** 工程名称 */
    @ExcelProperty(value = {"前台台账数据","工程名称"})
    private String projectName;

    /** 工程id */
    private Long projectId;

    /** 检测项目 */
    @ExcelProperty(value = {"前台台账数据","检测项目"})
    private String detectName;


    /** 工程部位 */
    @ExcelProperty(value = {"前台台账数据","工程部位（原材需填）"})
    private String detectEngineeringParts;

    /** 数量 */
    @ExcelProperty(value = {"前台台账数据","数量"})
    @Max(value = 20000,message = "数量不能超过20000")
    private Double detectAmount;

    /** 参数代码 */
    @ExcelProperty(value = {"前台台账数据","参数代码"})
    private String parameterCode;

    /** 项目类型 */
    @ExcelProperty(value = {"前台台账数据","检测类别"},converter = ProgramTypeConverter.class)
    private Integer programType;

    /** 样品数量 */
    @ExcelProperty(value = {"前台台账数据","样品数量"})
    @Max(value = 20000,message = "数量不能超过20000")
    private Integer sampleAmount;

    /** 报告编号 */
    @ExcelProperty(value = {"前台台账数据","报告编号"})
    private String reportSerialNumber;

    /** 委托编号 */
    @ExcelProperty(value = {"前台台账数据","委托编号"})
    private String entrustSerialNumber;

    /** 样品/记录编号 */
    @ExcelProperty(value = {"前台台账数据","样品/记录编号"})
    private String recordSerialNumber;

    @ExcelProperty(value = {"前台台账数据","备注"})
    /** 备注 */
    private String remark;

    @ExcelProperty(value = {"前台台账数据","综合判定是否插号"} , converter = StringConverter.class)
    private String isInterpolation;

    @ExcelProperty(value = {"前台台账数据","状态"},converter = StatusConverter.class)
    private Integer status;

    @ExcelProperty(value = {"前台台账数据","分配部门"})
    private String editDeptName;

    private List<Long> editDeptId;

    @ExcelProperty(value = {"前台台账数据","查看部门"})
    private String visitDeptName;

    private List<Long> visitDeptId;

    @ExcelProperty(value = {"前台台账数据","委托状态"},converter = EntrustStatusConverter.class)
    private Integer entrustStatus;

    @ExcelProperty(value = {"前台台账数据","委托备注"})
    private String entrustNote;

    @ExcelProperty(value = {"前台台账数据","是否结算"}, converter = StringConverter.class)
    private String isSettlement;

    @ExcelProperty(value = {"前台台账数据","结算金额"})
    private Integer settleAmount;

    @ExcelProperty(value = {"前台台账数据","结算类型"},converter = SettleConverter.class)
    private Integer settleType;

    @ExcelProperty(value = {"前台台账数据","结算备注"})
    /** 备注 */
    private String settleRemark;


    @ExcelProperty(value = {"前台台账数据","是否导入"} , converter = StringConverter.class)
    private String isImport;

    @ExcelProperty(value = {"前台台账数据","是否修改"} , converter = StringConverter.class)
    private String isUpdate;

    @ExcelProperty(value = {"前台台账数据","重复数量"})
    private Integer repeatNumber;


    public String getSettleRemark() {
        return settleRemark;
    }

    public void setSettleRemark(String settleRemark) {
        this.settleRemark = settleRemark;
    }

    public Integer getSettleType() {
        return settleType;
    }

    public void setSettleType(Integer settleType) {
        this.settleType = settleType;
    }

    public Integer getSettleAmount() {
        return settleAmount;
    }

    public void setSettleAmount(Integer settleAmount) {
        this.settleAmount = settleAmount;
    }

    public String getIsSettlement() {
        return isSettlement;
    }

    public void setIsSettlement(String isSettlement) {
        this.isSettlement = isSettlement;
    }

    public Integer getRepeatNumber() {
        return repeatNumber;
    }

    public void setRepeatNumber(Integer repeatNumber) {
        this.repeatNumber = repeatNumber;
    }

    public void setDeskId(Long deskId)
    {
        this.deskId = deskId;
    }

    public String getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(String isUpdate) {
        this.isUpdate = isUpdate;
    }

    public Long getDeskId()
    {
        return deskId;
    }
    public void setProjectType(Integer projectType)
    {
        this.projectType = projectType;
    }

    public String getIsImport() {
        return isImport;
    }

    public void setIsImport(String isImport) {
        this.isImport = isImport;
    }

    public Integer getProjectType()
    {
        return projectType;
    }
    public void setIssueTime(LocalDate issueTime)
    {
        this.issueTime = issueTime;
    }

    public LocalDate getIssueTime()
    {
        return issueTime;
    }
    public void setOrderTime(LocalDate orderTime)
    {
        this.orderTime = orderTime;
    }

    public LocalDate getOrderTime()
    {
        return orderTime;
    }
    public void setProjectName(String projectName)
    {
        this.projectName = projectName;
    }

    public String getProjectName()
    {
        return projectName;
    }
    public void setPublisher(String publisher)
    {
        this.publisher = publisher;
    }


    public Integer getEntrustStatus() {
        return entrustStatus;
    }

    public void setEntrustStatus(Integer entrustStatus) {
        this.entrustStatus = entrustStatus;
    }

    public String getEntrustNote() {
        return entrustNote;
    }

    public void setEntrustNote(String entrustNote) {
        this.entrustNote = entrustNote;
    }

    public String getEditDeptName() {
        return editDeptName;
    }

    public void setEditDeptName(String editDeptName) {
        this.editDeptName = editDeptName;
    }

    public String getPublisher()
    {
        return publisher;
    }
    public void setBusinessManager(String businessManager)
    {
        this.businessManager = businessManager;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Long> getEditDeptId() {
        return editDeptId;
    }

    public String getDetectEngineeringParts() {
        return detectEngineeringParts;
    }

    public void setDetectEngineeringParts(String detectEngineeringParts) {
        this.detectEngineeringParts = detectEngineeringParts;
    }

    public String getVisitDeptName() {
        return visitDeptName;
    }

    public void setVisitDeptName(String visitDeptName) {
        this.visitDeptName = visitDeptName;
    }

    public List<Long> getVisitDeptId() {
        return visitDeptId;
    }

    public void setVisitDeptId(List<Long> visitDeptId) {
        this.visitDeptId = visitDeptId;
    }

    public Double getDetectAmount() {
        return detectAmount;
    }

    public void setDetectAmount(Double detectAmount) {
        this.detectAmount = detectAmount;
    }

    public void setEditDeptId(List<Long> editDeptId) {
        this.editDeptId = editDeptId;
    }

    public String getBusinessManager()
    {
        return businessManager;
    }
    public void setEntrustmentTime(LocalDate entrustmentTime)
    {
        this.entrustmentTime = entrustmentTime;
    }

    public LocalDate getEntrustmentTime()
    {
        return entrustmentTime;
    }
    public void setEntrustmentUnit(String entrustmentUnit)
    {
        this.entrustmentUnit = entrustmentUnit;
    }

    public String getEntrustmentUnit()
    {
        return entrustmentUnit;
    }
    public void setProjectId(Long projectId)
    {
        this.projectId = projectId;
    }

    public Long getProjectId()
    {
        return projectId;
    }
    public void setDetectName(String detectName)
    {
        this.detectName = detectName;
    }

    public String getDetectName()
    {
        return detectName;
    }
    public void setParameterCode(String parameterCode)
    {
        this.parameterCode = parameterCode;
    }

    public String getParameterCode()
    {
        return parameterCode;
    }
    public void setProgramType(Integer programType)
    {
        this.programType = programType;
    }

    public Integer getProgramType()
    {
        return programType;
    }
    public void setSampleAmount(Integer sampleAmount)
    {
        this.sampleAmount = sampleAmount;
    }

    public Integer getSampleAmount()
    {
        return sampleAmount;
    }
    public void setReportSerialNumber(String reportSerialNumber)
    {
        this.reportSerialNumber = reportSerialNumber;
    }

    public String getReportSerialNumber()
    {
        return reportSerialNumber;
    }
    public void setEntrustSerialNumber(String entrustSerialNumber)
    {
        this.entrustSerialNumber = entrustSerialNumber;
    }

    public String getEntrustSerialNumber()
    {
        return entrustSerialNumber;
    }
    public void setRecordSerialNumber(String recordSerialNumber)
    {
        this.recordSerialNumber = recordSerialNumber;
    }

    public String getRecordSerialNumber()
    {
        return recordSerialNumber;
    }

    public String getIsInterpolation() {
        return isInterpolation;
    }

    public void setIsInterpolation(String isInterpolation) {
        this.isInterpolation = isInterpolation;
    }

    public LocalDate getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(LocalDate firstTime) {
        this.firstTime = firstTime;
    }

    public LocalDate getLastTime() {
        return lastTime;
    }

    public void setLastTime(LocalDate lastTime) {
        this.lastTime = lastTime;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("deskId", getDeskId())
                .append("projectType", getProjectType())
                .append("issueTime", getIssueTime())
                .append("orderTime", getOrderTime())
                .append("projectName", getProjectName())
                .append("publisher", getPublisher())
                .append("businessManager", getBusinessManager())
                .append("entrustmentTime", getEntrustmentTime())
                .append("entrustmentUnit", getEntrustmentUnit())
                .append("projectId", getProjectId())
                .append("detectName", getDetectName())
                .append("parameterCode", getParameterCode())
                .append("programType", getProgramType())
                .append("sampleAmount", getSampleAmount())
                .append("reportSerialNumber", getReportSerialNumber())
                .append("entrustSerialNumber", getEntrustSerialNumber())
                .append("recordSerialNumber", getRecordSerialNumber())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .toString();
    }


}

