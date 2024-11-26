package com.jijia.operational.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jijia.common.core.annotation.Excel;
import com.jijia.common.core.web.domain.BaseEntity;
import com.jijia.operational.utils.StringConverter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 综合部台账对象 op_calibration_msg
 *
 * @author leitianyu
 * @date 2023-02-04
 */
public class OpCalibrationMsg extends BaseEntity
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
    @Excel(name = "签收时间")
    private String receiptTime;

    /** 初步结果（备注几天） */
    @Excel(name = "初步结果")
    private String remarkTime;

    @Excel(name = "是否修改")
    private String isUpdate;

    @Excel(name = "修改备注")
    private String updateRemark;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    @Excel(name = "修改日期", width = 30, dateFormat = "yyyy/MM/dd")
    private LocalDate isUpdateTime;

    @Excel(name = "修改报告是否收款")
    private String isUpdateCollection;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    @Excel(name = "修改报告收款日期", width = 30, dateFormat = "yyyy/MM/dd")
    private LocalDate updateCollectionTime;

    @ExcelProperty(value = {"前台台账数据","是否结算"}, converter = StringConverter.class)
    private String isSettlement;

    private Integer status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    private BigDecimal courierCoust;

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
    public void setPigeonholeSerialNumber(String pigeonholeSerialNumber)
    {
        this.pigeonholeSerialNumber = pigeonholeSerialNumber;
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

    public BigDecimal getPrice()
    {
        return price;
    }
    public void setTotal(Long total)
    {
        this.total = total;
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

    public String getPresenter()
    {
        return presenter;
    }
    public void setCourierType(String courierType)
    {
        this.courierType = courierType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

