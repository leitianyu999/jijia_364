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
 * 综合部台账对象 op_calibration_msg
 *
 * @author leitianyu
 * @date 2023-02-04
 */
public class OpCalibrationMsgInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long deskId;

    /** 归档编号 */
    @Excel(name = "归档编号")
    private String pigeonholeSerialNumber;

    /** 记录表上交情况 */
    @Excel(name = "记录表上交情况")
    private String submitType;

    /** 单价 */
    @Excel(name = "单价")
    private BigDecimal price;

    /** 总价 */
    @Excel(name = "总价")
    private Long total;

    /** 是否开票 */
    @Excel(name = "是否开票")
    private String isInvoice;

    /** 是否收款 */
    @Excel(name = "是否收款")
    private String isCollection;

    /** 报告发放人 */
    @Excel(name = "报告发放人")
    private String presenter;

    /** 邮寄方式 */
    @Excel(name = "邮寄方式")
    private String courierType;

    /** 快递单号 */
    @Excel(name = "快递单号")
    private String courierNumber;

    /** 收件地址 */
    @Excel(name = "收件地址")
    private String address;

    /** 收件人 */
    @Excel(name = "收件人")
    private String addressee;

    /** 报告签收人 */
    @Excel(name = "报告签收人")
    private String reportAddressee;

    /** 签收时间 */
    @JsonFormat(pattern = "yyyy/MM/dd")
    @Excel(name = "签收时间", width = 30, dateFormat = "yyyy/MM/dd")
    private String receiptTime;

    /** 初步结果（备注几天） */
    @Excel(name = "初步结果")
    private String remarkTime;

    @Excel(name = "是否修改")
    private String isUpdate;

    @Excel(name = "修改备注")
    private String updateRemark;

    @Excel(name = "修改日期")
    private LocalDate isUpdateTime;

    @Excel(name = "修改报告是否收款")
    private String isUpdateCollection;

    @Excel(name = "修改报告收款日期")
    private LocalDate updateCollectionTime;

    private List<Integer> status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    private BigDecimal courierCoust;

    private List<Integer> projectType;


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

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private List<LocalDate> completeReportDate;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private List<LocalDate> entrustmentTime;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private List<LocalDate> reportDate;

    private String entrustSerialNumber;
    private String reportSerialNumber;
    private String recordSerialNumber;
    private String publisher;
    private String businessManager;

    private String entrustmentUnit;
    private String projectName;
    private String detectName;
    private String sampleMsg;
    private String detectCode;
    private Double detectAmount;
    private String unit;
    private String detectPersonnel;
    private String verifyPersonnel;
    private String composePersonnel;
    private String detectEngineeringParts;
    private List<Integer> deskStatus;

    private Integer courierStatus;


    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private  List<LocalDate>  orderTime;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private  List<LocalDate>  issueTime;

    private List<Long> deskIds;

    private Integer entrustStatus;

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

    public List<Long> getDeskIds() {
        return deskIds;
    }

    public void setDeskIds(List<Long> deskIds) {
        this.deskIds = deskIds;
    }

    public Integer getEntrustStatus() {
        return entrustStatus;
    }

    public void setEntrustStatus(Integer entrustStatus) {
        this.entrustStatus = entrustStatus;
    }

    public List<Integer> getStatus() {
        return status;
    }

    public void setStatus(List<Integer> status) {
        this.status = status;
    }

    public String getPigeonholeSerialNumber()
    {
        return pigeonholeSerialNumber;
    }
    public void setSubmitType(String submitType)
    {
        this.submitType = submitType;
    }

    public  List<LocalDate>  getOrderTime() {
        return orderTime;
    }

    public void setOrderTime( List<LocalDate>  orderTime) {
        this.orderTime = orderTime;
    }

    public String getSubmitType()
    {
        return submitType;
    }
    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    public List<Integer> getDeskStatus() {
        return deskStatus;
    }

    public void setDeskStatus(List<Integer> deskStatus) {
        this.deskStatus = deskStatus;
    }

    public List<LocalDate> getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(List<LocalDate> issueTime) {
        this.issueTime = issueTime;
    }

    public BigDecimal getPrice()
    {
        return price;
    }
    public void setTotal(Long total)
    {
        this.total = total;
    }

    public Long getTotal()
    {
        return total;
    }
    public void setIsInvoice(String isInvoice)
    {
        this.isInvoice = isInvoice;
    }

    public String getIsInvoice()
    {
        return isInvoice;
    }
    public void setIsCollection(String isCollection)
    {
        this.isCollection = isCollection;
    }

    public String getIsCollection()
    {
        return isCollection;
    }
    public void setPresenter(String presenter)
    {
        this.presenter = presenter;
    }

    public Integer getCourierStatus() {
        return courierStatus;
    }

    public void setCourierStatus(Integer courierStatus) {
        this.courierStatus = courierStatus;
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

    public String getReceiptTime() {
        return receiptTime;
    }

    public void setReceiptTime(String receiptTime) {
        this.receiptTime = receiptTime;
    }

    public List<LocalDate> getCompleteReportDate() {
        return completeReportDate;
    }

    public void setCompleteReportDate(List<LocalDate> completeReportDate) {
        this.completeReportDate = completeReportDate;
    }

    public List<LocalDate> getEntrustmentTime() {
        return entrustmentTime;
    }

    public void setEntrustmentTime(List<LocalDate> entrustmentTime) {
        this.entrustmentTime = entrustmentTime;
    }

    public List<LocalDate> getReportDate() {
        return reportDate;
    }

    public void setReportDate(List<LocalDate> reportDate) {
        this.reportDate = reportDate;
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

    public List<Integer> getProjectType() {
        return projectType;
    }

    public void setProjectType(List<Integer> projectType) {
        this.projectType = projectType;
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
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("deskId", getDeskId())
                .append("pigeonholeSerialNumber", getPigeonholeSerialNumber())
                .append("submitType", getSubmitType())
                .append("price", getPrice())
                .append("total", getTotal())
                .append("isInvoice", getIsInvoice())
                .append("isCollection", getIsCollection())
                .append("presenter", getPresenter())
                .append("courierType", getCourierType())
                .append("courierNumber", getCourierNumber())
                .append("address", getAddress())
                .append("addressee", getAddressee())
                .append("reportAddressee", getReportAddressee())
                .append("receiptTime", getReceiptTime())
                .append("remarkTime", getRemarkTime())
                .append("remark", getRemark())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("delFlag", getDelFlag())
                .toString();
    }
}

