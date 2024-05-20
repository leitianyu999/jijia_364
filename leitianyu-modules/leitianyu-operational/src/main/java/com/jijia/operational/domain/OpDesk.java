package com.jijia.operational.domain;

import java.time.LocalDate;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jijia.common.core.annotation.Excel;
import com.jijia.common.core.web.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 前台台账对象 op_desk
 *
 * @author leitianyu
 * @date 2023-02-04
 */
public class OpDesk extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 工程id */
    private Long deskId;


    /** 工程类别 */
    @Excel(name = "工程类别")
    private Integer projectType;

    /** 发单日期 */
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    @Excel(name = "发单日期", width = 30, dateFormat = "yyyy/MM/dd")
    private LocalDate issueTime;

    /** 下单日期 */
    @DateTimeFormat(pattern = "yyyy/MM/dd")
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
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    @Excel(name = "委托日期", width = 30, dateFormat = "yyyy/MM/dd")
    private LocalDate entrustmentTime;

    /** 工程id */
    private Long projectId;

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

    /** 删除标志（0代表存在 2代表删除） */
    private String isInterpolation;

    private Integer status;


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
    public void setProjectId(Long projectId)
    {
        this.projectId = projectId;
    }

    public Long getProjectId()
    {
        return projectId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("deskId", getDeskId())
                .append("projectType", getProjectType())
                .append("issueTime", getIssueTime())
                .append("orderTime", getOrderTime())
                .append("publisher", getPublisher())
                .append("businessManager", getBusinessManager())
                .append("entrustmentTime", getEntrustmentTime())
                .append("projectId", getProjectId())
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

