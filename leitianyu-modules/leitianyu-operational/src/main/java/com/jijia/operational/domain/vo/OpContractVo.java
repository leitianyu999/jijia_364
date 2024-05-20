package com.jijia.operational.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jijia.common.core.annotation.Excel;
import com.jijia.common.core.web.domain.BaseEntity;
import com.jijia.operational.utils.LocalDateConverter;
import com.jijia.operational.utils.StringConverter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
 * 合同对象 op_contract
 *
 * @author leitianyu
 * @date 2024-03-21
 */
@ExcelIgnoreUnannotated
public class OpContractVo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 合同id */
    private Long contractId;

    /** 开具日期 */
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    @ExcelProperty(value = {"合同台账","日期"},converter = LocalDateConverter.class)
    private LocalDate issueTime;

    /** 委托方 */
    @ExcelProperty(value = {"合同台账","委托方"})
    private String contractUnit;

    /** 工程名称 */
    @ExcelProperty(value = {"合同台账","工程名称"})
    private String projectMsg;

    /** 合同编号 */
    @ExcelProperty(value = {"合同台账","合同编号"})
    private String contractNumber;

    /** 数量 */
    @ExcelProperty(value = {"合同台账","数量"})
    private String amount;

    /** 联系人 */
    @ExcelProperty(value = {"合同台账","联系人"})
    private String publisher;

    /** 领取人 */
    @ExcelProperty(value = {"合同台账","领取人"})
    private String recipient;

    /** 领取方式 */
    @ExcelProperty(value = {"合同台账","领取方式"})
    private String recipientType;

    /** 快递单号 */
    @ExcelProperty(value = {"合同台账","快递单号"})
    private String courierNumber;

    /** 是否已回 */
    @ExcelProperty(value = {"合同台账","是否已回"})
    private String isReceipt;

    /** 已回份数 */
    @ExcelProperty(value = {"合同台账","已回份数"})
    private Long receiptAmount;

    /** 已回日期 */
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    @ExcelProperty(value = {"合同台账","已回日期"},converter = LocalDateConverter.class)
    private LocalDate receiptTime;

    /** 项目类型 */
    @ExcelProperty(value = {"合同台账","项目类型"})
    private String projectType;

    /** 合同暂定金额 */
    @ExcelProperty(value = {"合同台账","合同暂定金额"})
    private String tentativeAmount;

    /** 是否开票 */
    @ExcelProperty(value = {"合同台账","是否开票"}, converter = StringConverter.class)
    private String isInvoice;

    @ExcelProperty(value = {"合同台账","备注"})
    private String remark;

    @ExcelProperty(value = {"合同台账","是否导入"} , converter = StringConverter.class)
    private String isImport;


    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIsImport() {
        return isImport;
    }

    public void setIsImport(String isImport) {
        this.isImport = isImport;
    }

    public void setContractId(Long contractId)
    {
        this.contractId = contractId;
    }

    public Long getContractId()
    {
        return contractId;
    }
    public void setIssueTime(LocalDate issueTime)
    {
        this.issueTime = issueTime;
    }

    public LocalDate getIssueTime()
    {
        return issueTime;
    }
    public void setContractUnit(String contractUnit)
    {
        this.contractUnit = contractUnit;
    }

    public String getContractUnit()
    {
        return contractUnit;
    }
    public void setProjectMsg(String projectMsg)
    {
        this.projectMsg = projectMsg;
    }

    public String getProjectMsg()
    {
        return projectMsg;
    }
    public void setContractNumber(String contractNumber)
    {
        this.contractNumber = contractNumber;
    }

    public String getContractNumber()
    {
        return contractNumber;
    }
    public void setAmount(String amount)
    {
        this.amount = amount;
    }

    public String getAmount()
    {
        return amount;
    }
    public void setPublisher(String publisher)
    {
        this.publisher = publisher;
    }

    public String getPublisher()
    {
        return publisher;
    }
    public void setRecipient(String recipient)
    {
        this.recipient = recipient;
    }

    public String getRecipient()
    {
        return recipient;
    }
    public void setRecipientType(String recipientType)
    {
        this.recipientType = recipientType;
    }

    public String getRecipientType()
    {
        return recipientType;
    }
    public void setCourierNumber(String courierNumber)
    {
        this.courierNumber = courierNumber;
    }

    public String getCourierNumber()
    {
        return courierNumber;
    }
    public void setIsReceipt(String isReceipt)
    {
        this.isReceipt = isReceipt;
    }

    public String getIsReceipt()
    {
        return isReceipt;
    }
    public void setReceiptAmount(Long receiptAmount)
    {
        this.receiptAmount = receiptAmount;
    }

    public Long getReceiptAmount()
    {
        return receiptAmount;
    }
    public void setReceiptTime(LocalDate receiptTime)
    {
        this.receiptTime = receiptTime;
    }

    public LocalDate getReceiptTime()
    {
        return receiptTime;
    }
    public void setProjectType(String projectType)
    {
        this.projectType = projectType;
    }

    public String getProjectType()
    {
        return projectType;
    }
    public void setTentativeAmount(String tentativeAmount)
    {
        this.tentativeAmount = tentativeAmount;
    }

    public String getTentativeAmount()
    {
        return tentativeAmount;
    }
    public void setIsInvoice(String isInvoice)
    {
        this.isInvoice = isInvoice;
    }

    public String getIsInvoice()
    {
        return isInvoice;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("contractId", getContractId())
                .append("issueTime", getIssueTime())
                .append("contractUnit", getContractUnit())
                .append("projectMsg", getProjectMsg())
                .append("contractNumber", getContractNumber())
                .append("amount", getAmount())
                .append("publisher", getPublisher())
                .append("recipient", getRecipient())
                .append("recipientType", getRecipientType())
                .append("courierNumber", getCourierNumber())
                .append("isReceipt", getIsReceipt())
                .append("receiptAmount", getReceiptAmount())
                .append("receiptTime", getReceiptTime())
                .append("remark", getRemark())
                .append("projectType", getProjectType())
                .append("tentativeAmount", getTentativeAmount())
                .append("isInvoice", getIsInvoice())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("updateBy", getUpdateBy())
                .toString();
    }
}
