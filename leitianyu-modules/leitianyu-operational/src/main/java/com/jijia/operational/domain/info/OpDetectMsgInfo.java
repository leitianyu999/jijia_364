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
 * 检测台账对象 op_detect_msg
 *
 * @author leitianyu
 * @date 2023-02-04
 */
public class OpDetectMsgInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 工程识别id */
    private Long deskId;

    /** 工程名称 */
    @Excel(name = "工程名称")
    private String projectName;

    /** 委托单位 */
    @Excel(name = "委托单位")
    private String entrustmentUnit;

    /** 委托编号 */
    @Excel(name = "委托编号")
    private String entrustSerialNumber;

    /** 样品/记录编号 */
    @Excel(name = "样品/记录编号")
    private String recordSerialNumber;

    /** 出报告日期 */
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    @Excel(name = "出报告日期", width = 30, dateFormat = "yyyy/MM/dd")
    private List<LocalDate> completeReportDate;

    /** 报告日期 */
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    @Excel(name = "报告日期", width = 30, dateFormat = "yyyy/MM/dd")
    private List<LocalDate> reportDate;

    /** 下单日期 */
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    @Excel(name = "下单日期", width = 30, dateFormat = "yyyy/MM/dd")
    private List<LocalDate> orderTime;

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

    /** 报告编号 */
    @Excel(name = "报告编号")
    private String reportSerialNumber;

    /** 初步报告 */
    @Excel(name = "初步报告")
    private String preliminaryReport;

    private Integer entrustStatus;

    private Integer status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    private String detectTime;

    private String detectName;

    private String detectCode;

    private Double detectAmount;

    private String detectEngineeringParts;


    private String instrument;

    private String chemicals;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private List<LocalDate> entrustmentTime;

    private List<Long> deskIds;

    private List<Long> userIds;

    private int deptPermissionLevel;

    private String isUse;

    public String getIsUse() {
        return isUse;
    }

    public void setIsUse(String isUse) {
        this.isUse = isUse;
    }

    public int getDeptPermissionLevel() {
        return deptPermissionLevel;
    }

    public void setDeptPermissionLevel(int deptPermissionLevel) {
        this.deptPermissionLevel = deptPermissionLevel;
    }

    public void setDeskId(Long deskId)
    {
        this.deskId = deskId;
    }

    public Long getDeskId()
    {
        return deskId;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

    public List<Long> getDeskIds() {
        return deskIds;
    }

    public void setDeskIds(List<Long> deskIds) {
        this.deskIds = deskIds;
    }

    public String getComposePersonnel()
    {
        return composePersonnel;
    }
    public void setPreliminaryReport(String preliminaryReport)
    {
        this.preliminaryReport = preliminaryReport;
    }

    public String getProjectName() {
        return projectName;
    }

    public Integer getEntrustStatus() {
        return entrustStatus;
    }

    public void setEntrustStatus(Integer entrustStatus) {
        this.entrustStatus = entrustStatus;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getEntrustmentUnit() {
        return entrustmentUnit;
    }

    public List<LocalDate> getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(List<LocalDate> orderTime) {
        this.orderTime = orderTime;
    }

    public void setEntrustmentUnit(String entrustmentUnit) {
        this.entrustmentUnit = entrustmentUnit;
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

    public void setReportSerialNumber(String reportSerialNumber) {
        this.reportSerialNumber = reportSerialNumber;
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

    public String getReportSerialNumber() {
        return reportSerialNumber;
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

    public List<LocalDate> getCompleteReportDate() {
        return completeReportDate;
    }

    public void setCompleteReportDate(List<LocalDate> completeReportDate) {
        this.completeReportDate = completeReportDate;
    }

    public List<LocalDate> getReportDate() {
        return reportDate;
    }

    public void setReportDate(List<LocalDate> reportDate) {
        this.reportDate = reportDate;
    }

    public List<LocalDate> getEntrustmentTime() {
        return entrustmentTime;
    }

    public void setEntrustmentTime(List<LocalDate> entrustmentTime) {
        this.entrustmentTime = entrustmentTime;
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


