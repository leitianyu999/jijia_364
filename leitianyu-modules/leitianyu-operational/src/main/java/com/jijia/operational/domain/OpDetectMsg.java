package com.jijia.operational.domain;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jijia.system.api.domain.OpDeskDept;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jijia.common.core.annotation.Excel;
import com.jijia.common.core.web.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 检测台账对象 op_detect_msg
 *
 * @author leitianyu
 * @date 2023-02-04
 */

public class OpDetectMsg extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 工程识别id */
    private Long deskId;

    /** 出报告日期 */
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    @Excel(name = "出报告日期", width = 30, dateFormat = "yyyy/MM/dd")
    private LocalDate completeReportDate;

    /** 报告日期 */
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    @Excel(name = "报告日期", width = 30, dateFormat = "yyyy/MM/dd")
    private LocalDate reportDate;

    /** 样品信息 */
    @Excel(name = "样品信息")
    private String sampleMsg;

    /** 单位 */
    @Excel(name = "单位")
    private String unit;

    /** 检测人员 */
    @Excel(name = "检测人员")
    private String detectPersonnel;

    /** 校核人员 */
    @Excel(name = "校核人员")
    private String verifyPersonnel;

    /** 编写人员 */
    @Excel(name = "编写人员")
    private String composePersonnel;

    /** 初步报告 */
    @Excel(name = "初步报告")
    private String preliminaryReport;
    @Excel(name = "委托状态")
    private Integer entrustStatus;
    @Excel(name = "委托备注")
    private String entrustNote;

    @Excel(name = "检测备注")
    private String detectRemark;



    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    private String detectTime;

    private String detectName;

    private String detectCode;

    private Double detectAmount;

    private String detectEngineeringParts;

    private String instrument;

    private String chemicals;

    private Long userId;

    private LocalDate authorizationTime;


    private Integer status;

    public String getDetectRemark() {
        return detectRemark;
    }

    public void setDetectRemark(String detectRemark) {
        this.detectRemark = detectRemark;
    }

    public void setDeskId(Long deskId)
    {
        this.deskId = deskId;
    }

    public Long getDeskId()
    {
        return deskId;
    }
    public void setCompleteReportDate(LocalDate completeReportDate)
    {
        this.completeReportDate = completeReportDate;
    }

    public String getEntrustNote() {
        return entrustNote;
    }

    public void setEntrustNote(String entrustNote) {
        this.entrustNote = entrustNote;
    }

    public Integer getEntrustStatus() {
        return entrustStatus;
    }

    public void setEntrustStatus(Integer entrustStatus) {
        this.entrustStatus = entrustStatus;
    }

    public LocalDate getCompleteReportDate()
    {
        return completeReportDate;
    }
    public void setReportDate(LocalDate reportDate)
    {
        this.reportDate = reportDate;
    }


    public LocalDate getAuthorizationTime() {
        return authorizationTime;
    }

    public void setAuthorizationTime(LocalDate authorizationTime) {
        this.authorizationTime = authorizationTime;
    }

    public LocalDate getReportDate()
    {
        return reportDate;
    }
    public void setSampleMsg(String sampleMsg)
    {
        this.sampleMsg = sampleMsg;
    }

    public String getSampleMsg()
    {
        return sampleMsg;
    }
    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    public String getUnit()
    {
        return unit;
    }
    public void setDetectPersonnel(String detectPersonnel)
    {
        this.detectPersonnel = detectPersonnel;
    }

    public String getDetectPersonnel()
    {
        return detectPersonnel;
    }
    public void setVerifyPersonnel(String verifyPersonnel)
    {
        this.verifyPersonnel = verifyPersonnel;
    }

    public String getVerifyPersonnel()
    {
        return verifyPersonnel;
    }
    public void setComposePersonnel(String composePersonnel)
    {
        this.composePersonnel = composePersonnel;
    }

    public String getComposePersonnel()
    {
        return composePersonnel;
    }
    public void setPreliminaryReport(String preliminaryReport)
    {
        this.preliminaryReport = preliminaryReport;
    }

    public String getPreliminaryReport()
    {
        return preliminaryReport;
    }
    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public String getDetectTime() {
        return detectTime;
    }

    public void setDetectTime(String detectTime) {
        this.detectTime = detectTime;
    }

    public String getDetectName() {
        return detectName;
    }

    public void setDetectName(String detectName) {
        this.detectName = detectName;
    }

    public String getDetectCode() {
        return detectCode;
    }

    public void setDetectCode(String detectCode) {
        this.detectCode = detectCode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getDetectAmount() {
        return detectAmount;
    }

    public void setDetectAmount(Double detectAmount) {
        this.detectAmount = detectAmount;
    }

    public String getDetectEngineeringParts() {
        return detectEngineeringParts;
    }

    public void setDetectEngineeringParts(String detectEngineeringParts) {
        this.detectEngineeringParts = detectEngineeringParts;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public String getChemicals() {
        return chemicals;
    }

    public void setChemicals(String chemicals) {
        this.chemicals = chemicals;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("deskId", getDeskId())
                .append("completeReportDate", getCompleteReportDate())
                .append("reportDate", getReportDate())
                .append("sampleMsg", getSampleMsg())
                .append("unit", getUnit())
                .append("detectPersonnel", getDetectPersonnel())
                .append("verifyPersonnel", getVerifyPersonnel())
                .append("composePersonnel", getComposePersonnel())
                .append("preliminaryReport", getPreliminaryReport())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("delFlag", getDelFlag())
                .toString();
    }
}

