package com.jijia.operational.domain.vo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jijia.common.core.annotation.Excel;
import com.jijia.operational.utils.DetectMsgStatusConverter;
import com.jijia.operational.utils.EntrustStatusConverter;
import com.jijia.operational.utils.LocalDateConverter;
import com.jijia.common.core.web.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.Max;

/**
 * 检测台账对象 op_detect_msg
 *
 * @author leitianyu
 * @date 2023-02-04
 */
@ContentRowHeight(75)
@ExcelIgnoreUnannotated
public class OpDetectMsgVo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 工程识别id */
    private Long deskId;

    /** 下单日期 */
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    @ExcelProperty(value = {"检测台账数据","下单日期"} , converter = LocalDateConverter.class)
    private LocalDate orderTime;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    @ExcelProperty(value = {"检测台账数据","分配日期"}, converter = LocalDateConverter.class)
    private LocalDate authorizationTime;

    @ExcelProperty(value = {"检测台账数据","分配人员"})
    private String userName;

    @ExcelProperty(value = {"检测台账数据","委托状态"}, converter = EntrustStatusConverter.class)
    private Integer entrustStatus;

    @ExcelProperty(value = {"检测台账数据","委托备注"})
    private String entrustNote;

    @ExcelProperty(value = {"检测台账数据","完成状态"}, converter = DetectMsgStatusConverter.class)
    private Integer status;

    /** 出报告日期 */
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    @ExcelProperty(value = {"检测台账数据","出报告日期"},converter = LocalDateConverter.class)
    private LocalDate completeReportDate;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    @ExcelProperty(value = {"检测台账数据","委托日期"} , converter = LocalDateConverter.class)
    private LocalDate entrustmentTime;

    @ExcelProperty(value = {"检测台账数据","检测日期"})
    private String detectTime;

    /** 报告日期 */
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    @ExcelProperty(value = {"检测台账数据","报告日期"},converter = LocalDateConverter.class)
    private LocalDate reportDate;

    /** 委托编号 */
    @ExcelProperty(value = {"检测台账数据","委托编号"})
    private String entrustSerialNumber;

    /** 样品/记录编号 */
    @ExcelProperty(value = {"检测台账数据","样品/记录编号"})
    private String recordSerialNumber;

    /** 报告编号 */
    @ExcelProperty(value = {"检测台账数据","报告编号"})
    private String reportSerialNumber;

    /** 委托单位 */
    @ExcelProperty(value = {"检测台账数据","委托单位"})
    @ColumnWidth(30)
    private String entrustmentUnit;

    /** 工程名称 */
    @ExcelProperty(value = {"检测台账数据","工程名称"})
    @ColumnWidth(30)
    private String projectName;

    @ExcelProperty(value = {"检测台账数据","检测项目名称"})
    private String detectName;

    /** 样品信息 */
    @ExcelProperty(value = {"检测台账数据","样品信息"})
    private String sampleMsg;

    @ExcelProperty(value = {"检测台账数据","检测参数"})
    @ColumnWidth(30)
    private String detectCode;

    @ExcelProperty(value = {"检测台账数据","数量"})
    @Max(value = 20000,message = "数量不能超过20000")
    private Double detectAmount;

    /** 单位 */
    @ExcelProperty(value = {"检测台账数据","单位"})
    private String unit;

    @ExcelProperty(value = {"检测台账数据","工程部位"})
    @ColumnWidth(30)
    private String detectEngineeringParts;

    /** 检测人员 */
    @ExcelProperty(value = {"检测台账数据","检验人员"})
    private String detectPersonnel;

    /** 校核人员 */
    @ExcelProperty(value = {"检测台账数据","校核人员"})
    private String verifyPersonnel;

    /** 编写人员 */
    @ExcelProperty(value = {"检测台账数据","编写人员"})
    private String composePersonnel;

    /** 初步报告 */
    @ExcelProperty(value = {"检测台账数据","初步报告（备注几天）"})
    private String preliminaryReport;

    @ExcelProperty(value = {"检测台账数据","所需设备仪器"})
    @ColumnWidth(30)
    private String instrument;

    @ExcelProperty(value = {"检测台账数据","所需化学用品"})
    @ColumnWidth(30)
    private String chemicals;

    @ExcelProperty(value = {"检测台账数据","检测备注"})
    private String detectRemark;

    private Integer sampleAmount;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    private Long userId;

    private List<Long> deptId;

    private int deptPermissionLevel;


    public String getDetectRemark() {
        return detectRemark;
    }

    public void setDetectRemark(String detectRemark) {
        this.detectRemark = detectRemark;
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
    public void setCompleteReportDate(LocalDate completeReportDate)
    {
        this.completeReportDate = completeReportDate;
    }

    public Integer getSampleAmount() {
        return sampleAmount;
    }

    public void setSampleAmount(Integer sampleAmount) {
        this.sampleAmount = sampleAmount;
    }

    public LocalDate getCompleteReportDate()
    {
        return completeReportDate;
    }
    public void setReportDate(LocalDate reportDate)
    {
        this.reportDate = reportDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDate getReportDate()
    {
        return reportDate;
    }
    public void setSampleMsg(String sampleMsg)
    {
        this.sampleMsg = sampleMsg;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public List<Long> getDeptId() {
        return deptId;
    }

    public void setDeptId(List<Long> deptId) {
        this.deptId = deptId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDate getAuthorizationTime() {
        return authorizationTime;
    }

    public void setAuthorizationTime(LocalDate authorizationTime) {
        this.authorizationTime = authorizationTime;
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

    public String getProjectName() {
        return projectName;
    }

    public LocalDate getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDate orderTime) {
        this.orderTime = orderTime;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getEntrustmentUnit() {
        return entrustmentUnit;
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

    public LocalDate getEntrustmentTime() {
        return entrustmentTime;
    }

    public void setEntrustmentTime(LocalDate entrustmentTime) {
        this.entrustmentTime = entrustmentTime;
    }


    @Override
    public String toString() {
        return "OpDetectMsgVo{" +
                "deskId=" + deskId +
                ", projectName='" + projectName + '\'' +
                ", entrustmentUnit='" + entrustmentUnit + '\'' +
                ", entrustSerialNumber='" + entrustSerialNumber + '\'' +
                ", recordSerialNumber='" + recordSerialNumber + '\'' +
                ", completeReportDate=" + completeReportDate +
                ", reportDate=" + reportDate +
                ", sampleMsg='" + sampleMsg + '\'' +
                ", unit='" + unit + '\'' +
                ", detectPersonnel='" + detectPersonnel + '\'' +
                ", verifyPersonnel='" + verifyPersonnel + '\'' +
                ", composePersonnel='" + composePersonnel + '\'' +
                ", reportSerialNumber='" + reportSerialNumber + '\'' +
                ", preliminaryReport='" + preliminaryReport + '\'' +
                ", delFlag='" + delFlag + '\'' +
                ", detectTime='" + detectTime + '\'' +
                ", detectName='" + detectName + '\'' +
                ", detectCode='" + detectCode + '\'' +
                ", detectAmount=" + detectAmount +
                ", detectEngineeringParts='" + detectEngineeringParts + '\'' +
                ", instrument='" + instrument + '\'' +
                ", chemicals='" + chemicals + '\'' +
                ", entrustmentTime=" + entrustmentTime +
                '}';
    }
}


