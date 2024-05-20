package com.jijia.operational.domain.demo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jijia.common.core.annotation.Excel;
import com.jijia.common.core.web.domain.BaseEntity;
import com.jijia.operational.utils.StringConverter;
import org.springframework.format.annotation.DateTimeFormat;

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
public class OpCalibrationMsgVoDemo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long deskId;

    /** 归档编号 */
    @ExcelProperty(value = {"综合部台账数据","归档编号"})
    private String pigeonholeSerialNumber;

    @ExcelProperty(value = {"综合部台账数据","委托编号"})
    private String entrustSerialNumber;

    @ExcelProperty(value = {"综合部台账数据","样品/记录编号"})
    private String recordSerialNumber;

    @ExcelProperty(value = {"综合部台账数据","报告编号"})
    private String reportSerialNumber;

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
    @ExcelProperty(value = {"综合部台账数据","是否开票"})
    private String isInvoice;

    /** 是否收款 */
    @ExcelProperty(value = {"综合部台账数据","是否收款"})
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

    public BigDecimal getPrice()
    {
        return price;
    }
    public void setTotal(BigDecimal total)
    {
        this.total = total;
    }


    public BigDecimal getTotal()
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
                ", entrustSerialNumber='" + entrustSerialNumber + '\'' +
                ", reportSerialNumber='" + reportSerialNumber + '\'' +
                ", recordSerialNumber='" + recordSerialNumber + '\'' +
                '}';
    }
}

