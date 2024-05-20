package com.jijia.operational.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jijia.common.core.annotation.Excel;
import com.jijia.common.core.web.domain.BaseEntity;
import com.jijia.operational.utils.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * 预备表对象 op_ready_desk
 *
 * @author leitianyu
 * @date 2023-08-08
 */
@ExcelIgnoreUnannotated
public class OpReadyDeskVo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 工程识别id */
    private Long readyId;

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

    @ExcelProperty(value = {"前台台账数据","重复数量"})
    private Integer repeatNumber;

    public String getReportSerialNumber() {
        return reportSerialNumber;
    }

    public void setReportSerialNumber(String reportSerialNumber) {
        this.reportSerialNumber = reportSerialNumber;
    }

    public String getEntrustSerialNumber() {
        return entrustSerialNumber;
    }

    public void setEntrustSerialNumber(String entrustSerialNumber) {
        this.entrustSerialNumber = entrustSerialNumber;
    }

    public String getRecordSerialNumber() {
        return recordSerialNumber;
    }

    public void setRecordSerialNumber(String recordSerialNumber) {
        this.recordSerialNumber = recordSerialNumber;
    }

    public void setReadyId(Long readyId)
    {
        this.readyId = readyId;
    }

    public Long getReadyId()
    {
        return readyId;
    }
    public void setProjectId(Long projectId)
    {
        this.projectId = projectId;
    }

    public Long getProjectId()
    {
        return projectId;
    }
    public void setProjectType(Integer projectType)
    {
        this.projectType = projectType;
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
    public void setPublisher(String publisher)
    {
        this.publisher = publisher;
    }

    public String getPublisher()
    {
        return publisher;
    }
    public void setBusinessManager(String businessManager)
    {
        this.businessManager = businessManager;
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
    public void setIsInterpolation(String isInterpolation)
    {
        this.isInterpolation = isInterpolation;
    }

    public String getIsInterpolation()
    {
        return isInterpolation;
    }
    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Integer getStatus()
    {
        return status;
    }
    public void setEntrustStatus(Integer entrustStatus)
    {
        this.entrustStatus = entrustStatus;
    }

    public Integer getEntrustStatus()
    {
        return entrustStatus;
    }
    public void setEntrustNote(String entrustNote)
    {
        this.entrustNote = entrustNote;
    }

    public String getEntrustNote()
    {
        return entrustNote;
    }
    public void setDetectName(String detectName)
    {
        this.detectName = detectName;
    }

    public String getDetectName()
    {
        return detectName;
    }
    public void setDetectEngineeringParts(String detectEngineeringParts)
    {
        this.detectEngineeringParts = detectEngineeringParts;
    }

    public String getDetectEngineeringParts()
    {
        return detectEngineeringParts;
    }
    public void setDetectAmount(Double detectAmount)
    {
        this.detectAmount = detectAmount;
    }

    public Double getDetectAmount()
    {
        return detectAmount;
    }

    public String getEntrustmentUnit() {
        return entrustmentUnit;
    }

    public void setEntrustmentUnit(String entrustmentUnit) {
        this.entrustmentUnit = entrustmentUnit;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEditDeptName() {
        return editDeptName;
    }

    public void setEditDeptName(String editDeptName) {
        this.editDeptName = editDeptName;
    }

    public List<Long> getEditDeptId() {
        return editDeptId;
    }

    public void setEditDeptId(List<Long> editDeptId) {
        this.editDeptId = editDeptId;
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

    public Integer getRepeatNumber() {
        return repeatNumber;
    }

    public void setRepeatNumber(Integer repeatNumber) {
        this.repeatNumber = repeatNumber;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("readyId", getReadyId())
                .append("projectId", getProjectId())
                .append("projectType", getProjectType())
                .append("issueTime", getIssueTime())
                .append("orderTime", getOrderTime())
                .append("publisher", getPublisher())
                .append("businessManager", getBusinessManager())
                .append("entrustmentTime", getEntrustmentTime())
                .append("parameterCode", getParameterCode())
                .append("programType", getProgramType())
                .append("sampleAmount", getSampleAmount())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("isInterpolation", getIsInterpolation())
                .append("status", getStatus())
                .append("entrustStatus", getEntrustStatus())
                .append("entrustNote", getEntrustNote())
                .append("detectName", getDetectName())
                .append("detectEngineeringParts", getDetectEngineeringParts())
                .append("detectAmount", getDetectAmount())
                .toString();
    }
}
