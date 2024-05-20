package com.jijia.operational.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jijia.common.core.annotation.Excel;
import com.jijia.common.core.web.domain.BaseEntity;

/**
 * 预备表对象 op_ready_desk
 *
 * @author leitianyu
 * @date 2023-08-08
 */
public class OpReadyDesk extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 工程识别id */
    private Long readyId;


    private Long projectId;

    /** 工程类别 */
    @Excel(name = "工程类别")
    private Integer projectType;

    /** 发单日期 */
    @JsonFormat(pattern = "yyyy/MM/dd")
    @Excel(name = "发单日期", width = 30, dateFormat = "yyyy/MM/dd")
    private LocalDate issueTime;

    /** 下单日期 */
    @JsonFormat(pattern = "yyyy/MM/dd")
    @Excel(name = "下单日期", width = 30, dateFormat = "yyyy/MM/dd")
    private LocalDate orderTime;

    /** 下单人 */
    @Excel(name = "下单人")
    private String publisher;

    /** 业务经理 */
    @Excel(name = "业务经理")
    private String businessManager;

    /** 委托日期 */
    @JsonFormat(pattern = "yyyy/MM/dd")
    @Excel(name = "委托日期", width = 30, dateFormat = "yyyy/MM/dd")
    private LocalDate entrustmentTime;

    /** 参数代码 */
    @Excel(name = "参数代码")
    private String parameterCode;

    /** 项目类型 */
    @Excel(name = "项目类型")
    private Integer programType;

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

    /** 是否插号 */
    @Excel(name = "是否插号")
    private String isInterpolation;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** $column.columnComment */
    @Excel(name = "委托状态")
    private Integer entrustStatus;

    /** $column.columnComment */
    @Excel(name = "委托备注")
    private String entrustNote;

    /** 检测项目名称 */
    @Excel(name = "检测项目名称")
    private String detectName;

    /** 工程部位 */
    @Excel(name = "工程部位")
    private String detectEngineeringParts;

    /** 数量 */
    @Excel(name = "数量")
    private Double detectAmount;

    /** 重复数量 */
    @Excel(name = "重复数量")
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

    public Integer getRepeatNumber() {
        return repeatNumber;
    }

    public void setRepeatNumber(Integer repeatNumber) {
        this.repeatNumber = repeatNumber;
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
