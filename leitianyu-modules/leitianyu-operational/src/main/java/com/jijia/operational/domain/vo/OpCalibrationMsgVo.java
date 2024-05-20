package com.jijia.operational.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jijia.common.core.annotation.Excel;
import com.jijia.common.core.web.domain.BaseEntity;
import com.jijia.operational.utils.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
 * 综合部台账对象 op_calibration_msg
 *
 * @author leitianyu
 * @date 2023-02-04
 */
@ExcelIgnoreUnannotated
public class OpCalibrationMsgVo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long deskId;

    @ExcelProperty(value = {"综合部台账数据","工程类型"},converter = ProjectTypeConverter.class)
    private Integer projectType;

    /** 发单日期 */
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    @ExcelProperty(value = {"综合部台账数据","发单日期"},converter = LocalDateConverter.class)
    private LocalDate issueTime;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    @ExcelProperty(value = {"综合部台账数据","下单日期"},converter = LocalDateConverter.class)
    private LocalDate orderTime;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    @ExcelProperty(value = {"综合部台账数据","出报告日期"},converter = LocalDateConverter.class)
    private LocalDate completeReportDate;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    @ExcelProperty(value = {"综合部台账数据","委托日期"} , converter = LocalDateConverter.class)
    private LocalDate entrustmentTime;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    @ExcelProperty(value = {"综合部台账数据","报告日期"},converter = LocalDateConverter.class)
    private LocalDate reportDate;

    /** 归档编号 */
    @ExcelProperty(value = {"综合部台账数据","归档编号"})
    private String pigeonholeSerialNumber;

    @ExcelProperty(value = {"综合部台账数据","委托编号"})
    private String entrustSerialNumber;

    @ExcelProperty(value = {"综合部台账数据","样品/记录编号"})
    private String recordSerialNumber;

    @ExcelProperty(value = {"综合部台账数据","报告编号"})
    private String reportSerialNumber;

    @ExcelProperty(value = {"综合部台账数据","下单人"})
    private String publisher;

    @ExcelProperty(value = {"综合部台账数据","业务经理"})
    private String businessManager;

    @ExcelProperty(value = {"综合部台账数据","委托单位"})
    private String entrustmentUnit;

    @ExcelProperty(value = {"综合部台账数据","工程名称"})
    private String projectName;

    @ExcelProperty(value = {"综合部台账数据","检测项目名称"})
    private String detectName;

    @ExcelProperty(value = {"综合部台账数据","样品信息"})
    private String sampleMsg;

    @ExcelProperty(value = {"综合部台账数据","检测参数"})
    private String detectCode;

    @ExcelProperty(value = {"综合部台账数据","检测数量"})
    private Double detectAmount;

    @ExcelProperty(value = {"综合部台账数据","单位"})
    private String unit;

    @ExcelProperty(value = {"综合部台账数据","检测人员"})
    private String detectPersonnel;

    @ExcelProperty(value = {"综合部台账数据","校核人员"})
    private String verifyPersonnel;

    @ExcelProperty(value = {"综合部台账数据","编写人员"})
    private String composePersonnel;

    @ExcelProperty(value = {"综合部台账数据","工程部位"})
    private String detectEngineeringParts;

    /** 记录表上交情况 */
    @ExcelProperty(value = {"综合部台账数据","记录表上交情况"})
    private String submitType;

    /** 单价 */
    @ExcelProperty(value = {"综合部台账数据","单价"})
    private BigDecimal price;

    /** 总价 */
    @ExcelProperty(value = {"综合部台账数据","总价"})
    private BigDecimal total;

    /** 是否开票 */
    @ExcelProperty(value = {"综合部台账数据","是否开票"}, converter = StringConverter.class)
    private String isInvoice;

    /** 是否收款 */
    @ExcelProperty(value = {"综合部台账数据","是否收款"}, converter = StringConverter.class)
    private String isCollection;

    /** 报告发放人 */
    @ExcelProperty(value = {"综合部台账数据","报告发放人"})
    private String presenter;

    /** 邮寄方式 */
    @ExcelProperty(value = {"综合部台账数据","邮寄方式"})
    private String courierType;

    /** 快递单号 */
    @ExcelProperty(value = {"综合部台账数据","快递单号"})
    private String courierNumber;

    /** 收件地址 */
    @ExcelProperty(value = {"综合部台账数据","收件地址"})
    private String address;

    /** 收件人 */
    @ExcelProperty(value = {"综合部台账数据","收件人及电话"})
    private String addressee;

    @ExcelProperty(value = {"综合部台账数据","邮寄费"})
    private BigDecimal courierCoust;

    /** 报告签收人 */
    @ExcelProperty(value = {"综合部台账数据","快递状态"})
    private String courierStatus;

    /** 报告签收人 */
    @ExcelProperty(value = {"综合部台账数据","报告签收人"})
    private String reportAddressee;

    /** 签收时间 */
    @ExcelProperty(value = {"综合部台账数据","报告签收日期"})
    private String receiptTime;

    /** 初步结果（备注几天） */
    @ExcelProperty(value = {"综合部台账数据","初步报告（备注几天）"})
    private String remarkTime;

    @ExcelProperty(value = {"综合部台账数据","备注"})
    private String remark;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    @ExcelProperty(value = {"综合部台账数据","完成状态"}, converter = CalibrationMsgStatusConverter.class)
    private Integer status;

    @ExcelProperty(value = {"综合部台账数据","状态"},converter = StatusConverter.class)
    private Integer deskStatus;

    @ExcelProperty(value = {"综合部台账数据","委托状态"}, converter = EntrustStatusConverter.class)
    private Integer entrustStatus;

    @ExcelProperty(value = {"综合部台账数据","委托备注"})
    private String entrustNote;

    @ExcelProperty(value = {"综合部台账数据","是否修改"}, converter = StringConverter.class)
    private String isUpdate;

    @ExcelProperty(value = {"综合部台账数据","修改备注"})
    private String updateRemark;

    @ExcelProperty(value = {"综合部台账数据","检测备注"})
    private String detectRemark;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    @ExcelProperty(value = {"综合部台账数据","修改日期"}, converter = LocalDateConverter.class)
    private LocalDate isUpdateTime;

    @ExcelProperty(value = {"综合部台账数据","修改报告是否收款"}, converter = StringConverter.class)
    private String isUpdateCollection;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    @ExcelProperty(value = {"综合部台账数据","修改报告收款日期"}, converter = LocalDateConverter.class)
    private LocalDate updateCollectionTime;

    private Integer sampleAmount;


    public String getDetectRemark() {
        return detectRemark;
    }

    public void setDetectRemark(String detectRemark) {
        this.detectRemark = detectRemark;
    }

    public LocalDate getIsUpdateTime() {
        return isUpdateTime;
    }

    public void setIsUpdateTime(LocalDate isUpdateTime) {
        this.isUpdateTime = isUpdateTime;
    }

    public String getIsUpdateCollection() {
        return isUpdateCollection;
    }

    public void setIsUpdateCollection(String isUpdateCollection) {
        this.isUpdateCollection = isUpdateCollection;
    }

    public LocalDate getUpdateCollectionTime() {
        return updateCollectionTime;
    }

    public void setUpdateCollectionTime(LocalDate updateCollectionTime) {
        this.updateCollectionTime = updateCollectionTime;
    }

    public void setDeskId(Long deskId)
    {
        this.deskId = deskId;
    }

    public Long getDeskId()
    {
        return deskId;
    }
    public void setPigeonholeSerialNumber(String pigeonholeSerialNumber)
    {
        this.pigeonholeSerialNumber = pigeonholeSerialNumber;
    }

    public String getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(String isUpdate) {
        this.isUpdate = isUpdate;
    }

    public String getUpdateRemark() {
        return updateRemark;
    }

    public void setUpdateRemark(String updateRemark) {
        this.updateRemark = updateRemark;
    }

    public Integer getSampleAmount() {
        return sampleAmount;
    }

    public void setSampleAmount(Integer sampleAmount) {
        this.sampleAmount = sampleAmount;
    }

    public String getPigeonholeSerialNumber()
    {
        return pigeonholeSerialNumber;
    }
    public void setSubmitType(String submitType)
    {
        this.submitType = submitType;
    }

    public String getSubmitType()
    {
        return submitType;
    }
    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    public BigDecimal getPrice()
    {
        return price;
    }
    public void setTotal(BigDecimal total)
    {
        this.total = total;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDate getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(LocalDate issueTime) {
        this.issueTime = issueTime;
    }

    public BigDecimal getTotal()
    {
        return total;
    }
    public void setIsInvoice(String isInvoice)
    {
        this.isInvoice = isInvoice;
    }

    public String getCourierStatus() {
        return courierStatus;
    }

    public void setCourierStatus(String courierStatus) {
        this.courierStatus = courierStatus;
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

    public String getIsInvoice()
    {
        return isInvoice;
    }
    public void setIsCollection(String isCollection)
    {
        this.isCollection = isCollection;
    }

    public LocalDate getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDate orderTime) {
        this.orderTime = orderTime;
    }

    public String getIsCollection()
    {
        return isCollection;
    }
    public void setPresenter(String presenter)
    {
        this.presenter = presenter;
    }

    public Integer getDeskStatus() {
        return deskStatus;
    }

    public void setDeskStatus(Integer deskStatus) {
        this.deskStatus = deskStatus;
    }

    public String getPresenter()
    {
        return presenter;
    }
    public void setCourierType(String courierType)
    {
        this.courierType = courierType;
    }

    public String getCourierType()
    {
        return courierType;
    }
    public void setCourierNumber(String courierNumber)
    {
        this.courierNumber = courierNumber;
    }

    public String getCourierNumber()
    {
        return courierNumber;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAddress()
    {
        return address;
    }
    public void setAddressee(String addressee)
    {
        this.addressee = addressee;
    }

    public String getAddressee()
    {
        return addressee;
    }
    public void setReportAddressee(String reportAddressee)
    {
        this.reportAddressee = reportAddressee;
    }

    public String getReportAddressee()
    {
        return reportAddressee;
    }
    public void setReceiptTime(String receiptTime)
    {
        this.receiptTime = receiptTime;
    }

    public String getReceiptTime()
    {
        return receiptTime;
    }
    public void setRemarkTime(String remarkTime)
    {
        this.remarkTime = remarkTime;
    }

    public String getRemarkTime()
    {
        return remarkTime;
    }
    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public BigDecimal getCourierCoust() {
        return courierCoust;
    }

    public void setCourierCoust(BigDecimal courierCoust) {
        this.courierCoust = courierCoust;
    }

    public Integer getProjectType() {
        return projectType;
    }

    public void setProjectType(Integer projectType) {
        this.projectType = projectType;
    }

    public LocalDate getCompleteReportDate() {
        return completeReportDate;
    }

    public void setCompleteReportDate(LocalDate completeReportDate) {
        this.completeReportDate = completeReportDate;
    }

    public LocalDate getEntrustmentTime() {
        return entrustmentTime;
    }

    public void setEntrustmentTime(LocalDate entrustmentTime) {
        this.entrustmentTime = entrustmentTime;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public String getEntrustSerialNumber() {
        return entrustSerialNumber;
    }

    public void setEntrustSerialNumber(String entrustSerialNumber) {
        this.entrustSerialNumber = entrustSerialNumber;
    }

    public String getReportSerialNumber() {
        return reportSerialNumber;
    }

    public void setReportSerialNumber(String reportSerialNumber) {
        this.reportSerialNumber = reportSerialNumber;
    }

    public String getRecordSerialNumber() {
        return recordSerialNumber;
    }

    public void setRecordSerialNumber(String recordSerialNumber) {
        this.recordSerialNumber = recordSerialNumber;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getBusinessManager() {
        return businessManager;
    }

    public void setBusinessManager(String businessManager) {
        this.businessManager = businessManager;
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

    public String getDetectName() {
        return detectName;
    }

    public void setDetectName(String detectName) {
        this.detectName = detectName;
    }

    public String getSampleMsg() {
        return sampleMsg;
    }

    public void setSampleMsg(String sampleMsg) {
        this.sampleMsg = sampleMsg;
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

    public String getUnit() {
        return unit;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDetectPersonnel() {
        return detectPersonnel;
    }

    public void setDetectPersonnel(String detectPersonnel) {
        this.detectPersonnel = detectPersonnel;
    }

    public String getVerifyPersonnel() {
        return verifyPersonnel;
    }

    public void setVerifyPersonnel(String verifyPersonnel) {
        this.verifyPersonnel = verifyPersonnel;
    }

    public String getComposePersonnel() {
        return composePersonnel;
    }

    public void setComposePersonnel(String composePersonnel) {
        this.composePersonnel = composePersonnel;
    }

    public String getDetectEngineeringParts() {
        return detectEngineeringParts;
    }

    public void setDetectEngineeringParts(String detectEngineeringParts) {
        this.detectEngineeringParts = detectEngineeringParts;
    }

    @Override
    public String toString() {
        return "OpCalibrationMsgVo{" +
                "deskId=" + deskId +
                ", pigeonholeSerialNumber='" + pigeonholeSerialNumber + '\'' +
                ", submitType='" + submitType + '\'' +
                ", price=" + price +
                ", total=" + total +
                ", isInvoice='" + isInvoice + '\'' +
                ", isCollection='" + isCollection + '\'' +
                ", presenter='" + presenter + '\'' +
                ", courierType='" + courierType + '\'' +
                ", courierNumber='" + courierNumber + '\'' +
                ", address='" + address + '\'' +
                ", addressee='" + addressee + '\'' +
                ", reportAddressee='" + reportAddressee + '\'' +
                ", receiptTime='" + receiptTime + '\'' +
                ", remarkTime='" + remarkTime + '\'' +
                ", remark='" + remark + '\'' +
                ", delFlag='" + delFlag + '\'' +
                ", courierCoust=" + courierCoust +
                ", projectType='" + projectType + '\'' +
                ", completeReportDate=" + completeReportDate +
                ", entrustmentTime=" + entrustmentTime +
                ", reportDate=" + reportDate +
                ", entrustSerialNumber='" + entrustSerialNumber + '\'' +
                ", reportSerialNumber='" + reportSerialNumber + '\'' +
                ", recordSerialNumber='" + recordSerialNumber + '\'' +
                ", publisher='" + publisher + '\'' +
                ", businessManager='" + businessManager + '\'' +
                ", entrustmentUnit='" + entrustmentUnit + '\'' +
                ", projectName='" + projectName + '\'' +
                ", detectName='" + detectName + '\'' +
                ", sampleMsg='" + sampleMsg + '\'' +
                ", detectCode='" + detectCode + '\'' +
                ", detectAmount=" + detectAmount +
                ", unit='" + unit + '\'' +
                ", detectPersonnel='" + detectPersonnel + '\'' +
                ", verifyPersonnel='" + verifyPersonnel + '\'' +
                ", composePersonnel='" + composePersonnel + '\'' +
                ", detectEngineeringParts='" + detectEngineeringParts + '\'' +
                '}';
    }
}

