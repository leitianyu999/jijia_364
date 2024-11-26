package com.jijia.operational.domain.info;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jijia.common.core.annotation.Excel;
import com.jijia.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * 前台台账对象 op_desk
 *
 * @author leitianyu
 * @date 2023-02-04
 */
public class OpDeskInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 工程id */
    private Long deskId;

    /** 工程类别 */
    @Excel(name = "工程类别")
    private List<Integer> projectType;

    /** 发单日期 */
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    @Excel(name = "发单日期", width = 30, dateFormat = "yyyy/MM/dd")
    private List<LocalDate> issueTime;

    /** 下单日期 */
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    @Excel(name = "下单日期", width = 30, dateFormat = "yyyy/MM/dd")
    private List<LocalDate> orderTime;

    /** 工程名称 */
    @Excel(name = "工程名称")
    private String projectName;

    /** 下单人 */
    @Excel(name = "下单人")
    private String publisher;

    /** 业务经理 */
    @Excel(name = "业务经理")
    private String businessManager;

    /** 委托日期 */
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    @Excel(name = "委托日期", width = 30, dateFormat = "yyyy/MM/dd")
    private List<LocalDate> entrustmentTime;

    private LocalDate firstTime;

    private LocalDate lastTime;

    private List<Long> deskIds;


    /** 委托单位 */
    @Excel(name = "委托单位")
    private String entrustmentUnit;

    /** 工程id */
    private Long projectId;

    /** 检测项目 */
    @Excel(name = "检测项目")
    private String detectName;


    /** 工程部位 */
    @Excel(name = "工程部位")
    private String detectEngineeringParts;

    /** 数量 */
    @Excel(name = "数量")
    private Double detectAmount;

    /** 参数代码 */
    @Excel(name = "参数代码")
    private String parameterCode;

    /** 项目类型 */
    @Excel(name = "项目类型")
    private Integer programType;

    private Integer entrustStatus;

    /** 样品数量 */
    @Excel(name = "样品数量")
    private Integer sampleAmount;

    /** 报告编号 */
    @Excel(name = "报告编号")
    private String reportSerialNumber;

    /** 委托编号 */
    @Excel(name = "委托编号")
    private String entrustSerialNumber;

    /** 样品/记录编号 */
    @Excel(name = "样品/记录编号")
    private String recordSerialNumber;

    // 是否结算
    @Excel(name = "是否结算")
    private String isSettlement;

    /** 删除标志（0代表存在 2代表删除） */
    private String isInterpolation;

    private List<Integer> status;

    private List<Long> editDeptId;

    private List<Long> visitDeptId;

    public String getIsSettlement() {
        return isSettlement;
    }

    public void setIsSettlement(String isSettlement) {
        this.isSettlement = isSettlement;
    }

    public void setDeskId(Long deskId)
    {
        this.deskId = deskId;
    }

    public Long getDeskId()
    {
        return deskId;
    }
    public void setProjectType(List<Integer> projectType)
    {
        this.projectType = projectType;
    }

    public List<Integer> getProjectType()
    {
        return projectType;
    }

    public List<LocalDate> getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(List<LocalDate> issueTime) {
        this.issueTime = issueTime;
    }

    public List<LocalDate> getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(List<LocalDate> orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getEntrustStatus() {
        return entrustStatus;
    }

    public List<Long> getEditDeptId() {
        return editDeptId;
    }

    public void setEditDeptId(List<Long> editDeptId) {
        this.editDeptId = editDeptId;
    }

    public List<Long> getVisitDeptId() {
        return visitDeptId;
    }

    public void setVisitDeptId(List<Long> visitDeptId) {
        this.visitDeptId = visitDeptId;
    }

    public void setEntrustStatus(Integer entrustStatus) {
        this.entrustStatus = entrustStatus;
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

    public String getPublisher()
    {
        return publisher;
    }
    public void setBusinessManager(String businessManager)
    {
        this.businessManager = businessManager;
    }

    public List<Long> getDeskIds() {
        return deskIds;
    }

    public void setDeskIds(List<Long> deskIds) {
        this.deskIds = deskIds;
    }

    public List<Integer> getStatus() {
        return status;
    }

    public void setStatus(List<Integer> status) {
        this.status = status;
    }

    public String getBusinessManager()
    {
        return businessManager;
    }

    public List<LocalDate> getEntrustmentTime() {
        return entrustmentTime;
    }

    public void setEntrustmentTime(List<LocalDate> entrustmentTime) {
        this.entrustmentTime = entrustmentTime;
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

