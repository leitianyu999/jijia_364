package com.jijia.operational.domain.demo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jijia.common.core.annotation.Excel;
import com.jijia.common.core.web.domain.BaseEntity;
import com.jijia.operational.utils.EntrustStatusConverter;
import com.jijia.operational.utils.LocalDateConverter;
import com.jijia.operational.utils.StatusConverter;
import com.jijia.operational.utils.StringConverter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
 * 前台台账对象 op_desk
 *
 * @author leitianyu
 * @date 2023-02-04
 */
@ExcelIgnoreUnannotated
public class OpDeskVoDemo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 工程id */
    private Long deskId;

    /** 工程类别 */
    @ExcelProperty(value = {"前台台账数据","工程类别"})
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

    /** 检测项目 */
    @ExcelProperty(value = {"前台台账数据","检测项目"})
    private String detectName;


    /** 工程部位 */
    @ExcelProperty(value = {"前台台账数据","工程部位（原材需填）"})
    private String detectEngineeringParts;

    /** 数量 */
    @ExcelProperty(value = {"前台台账数据","数量"})
    private Double detectAmount;

    /** 参数代码 */
    @ExcelProperty(value = {"前台台账数据","参数代码"})
    private String parameterCode;

    /** 项目类型 */
    @ExcelProperty(value = {"前台台账数据","检测类别"})
    private Integer programType;

    /** 样品数量 */
    @ExcelProperty(value = {"前台台账数据","样品数量"})
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

    /** 备注 */
    @ExcelProperty(value = {"前台台账数据","备注"})
    private String remark;

    @ExcelProperty(value = {"前台台账数据","综合判定是否插号"} , converter = StringConverter.class)
    private String isInterpolation;

    @ExcelProperty(value = {"前台台账数据","状态"},converter = StatusConverter.class)
    private Integer status;

    @ExcelProperty(value = {"前台台账数据","分配部门"})
    private String editDeptName;

    @ExcelProperty(value = {"前台台账数据","查看部门"})
    private String visitDeptName;

    @ExcelProperty(value = {"前台台账数据","委托状态"},converter = EntrustStatusConverter.class)
    private Integer entrustStatus;

    @ExcelProperty(value = {"前台台账数据","委托备注"})
    private String entrustNote;

    @ExcelProperty(value = {"前台台账数据","是否导入"} , converter = StringConverter.class)
    private String isImport;

    @ExcelProperty(value = {"前台台账数据","重复数量"})
    private Integer repeatNumber;




    public void setDeskId(Long deskId)
    {
        this.deskId = deskId;
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

    public String getEditDeptName() {
        return editDeptName;
    }

    public void setEditDeptName(String editDeptName) {
        this.editDeptName = editDeptName;
    }

    public String getVisitDeptName() {
        return visitDeptName;
    }

    public void setVisitDeptName(String visitDeptName) {
        this.visitDeptName = visitDeptName;
    }

    public Integer getRepeatNumber() {
        return repeatNumber;
    }

    public void setRepeatNumber(Integer repeatNumber) {
        this.repeatNumber = repeatNumber;
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

    public String getBusinessManager()
    {
        return businessManager;
    }
    public void setEntrustmentTime(LocalDate entrustmentTime)
    {
        this.entrustmentTime = entrustmentTime;
    }

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

    public String getDetectEngineeringParts() {
        return detectEngineeringParts;
    }

    public void setDetectEngineeringParts(String detectEngineeringParts) {
        this.detectEngineeringParts = detectEngineeringParts;
    }

    public Double getDetectAmount() {
        return detectAmount;
    }

    public void setDetectAmount(Double detectAmount) {
        this.detectAmount = detectAmount;
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

    public String getIsInterpolation() {
        return isInterpolation;
    }

    public void setIsInterpolation(String isInterpolation) {
        this.isInterpolation = isInterpolation;
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
                .append("detectName", getDetectName())
                .append("parameterCode", getParameterCode())
                .append("programType", getProgramType())
                .append("sampleAmount", getSampleAmount())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}

