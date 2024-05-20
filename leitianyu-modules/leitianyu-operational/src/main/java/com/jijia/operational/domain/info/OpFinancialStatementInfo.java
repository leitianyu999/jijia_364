package com.jijia.operational.domain.info;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jijia.common.core.annotation.Excel;
import com.jijia.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * 应收账款台账对象 op_financial_statement
 *
 * @author leitianyu
 * @date 2024-01-18
 */
public class OpFinancialStatementInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long financialStatementId;

    /** 订单号/合同号 */
    @Excel(name = "订单号/合同号")
    private String orderNumber;

    /** 报告单位 */
    @Excel(name = "报告单位")
    private String reportUnit;

    /** 工程名称 */
    @Excel(name = "工程名称")
    private String projectMsg;

    /** 总金额 */
    @Excel(name = "总金额")
    private BigDecimal amount;

    /** 本期应收金额 */
    @Excel(name = "本期应收金额")
    private BigDecimal receivableAmount;

    /** 该笔款支付期限 */
    @JsonFormat(pattern = "yyyy/MM/dd")
    @Excel(name = "该笔款支付期限", width = 30, dateFormat = "yyyy/MM/dd")
    private LocalDate receivableTime;

    /** 发票号码 */
    @Excel(name = "发票号码")
    private String invoiceNumber;

    /** 发票开具日期 */
    @JsonFormat(pattern = "yyyy/MM/dd")
    @Excel(name = "发票开具日期", width = 30, dateFormat = "yyyy/MM/dd")
    private LocalDate invoiceTime;

    /** 税率 */
    @Excel(name = "税率")
    private String rate;

    /** 发票类型 */
    @Excel(name = "发票类型")
    private String invoiceType;

    /** 发票金额 */
    @Excel(name = "发票金额")
    private BigDecimal invoiceAmount;

    /** 到款日期 */
    @JsonFormat(pattern = "yyyy/MM/dd")
    @Excel(name = "到款日期", width = 30, dateFormat = "yyyy/MM/dd")
    private LocalDate arrivalTime;

    /** 到账金额 */
    @Excel(name = "到账金额")
    private BigDecimal receivableNumber;

    /** 未付金额 */
    @Excel(name = "未付金额")
    private BigDecimal unpaidAmount;

    /** 支付方式 */
    @Excel(name = "支付方式")
    private String payType;

    /** 回款率 */
    @Excel(name = "回款率")
    private String reimbursementRate;

    /** 是否是吉嘉真实业务 */
    @Excel(name = "是否是吉嘉真实业务")
    private String isReal;

    /** 下单人 */
    @Excel(name = "下单人")
    private String publisher;

    /** 业务经理 */
    @Excel(name = "业务经理")
    private String businessManager;

    /** 吉嘉入账金额 */
    @Excel(name = "吉嘉入账金额")
    private BigDecimal creditedAmount;

    /** 回扣 */
    @Excel(name = "回扣")
    private BigDecimal kickback;

    /** 已收税金 */
    @Excel(name = "已收税金")
    private BigDecimal taxesCollected;

    /** 吉嘉实际收入 */
    @Excel(name = "吉嘉实际收入")
    private BigDecimal realAmount;

    private List<Long> financialStatementIds;

    public List<Long> getFinancialStatementIds() {
        return financialStatementIds;
    }

    public void setFinancialStatementIds(List<Long> financialStatementIds) {
        this.financialStatementIds = financialStatementIds;
    }

    public void setFinancialStatementId(Long financialStatementId)
    {
        this.financialStatementId = financialStatementId;
    }

    public Long getFinancialStatementId()
    {
        return financialStatementId;
    }
    public void setOrderNumber(String orderNumber)
    {
        this.orderNumber = orderNumber;
    }

    public String getOrderNumber()
    {
        return orderNumber;
    }
    public void setReportUnit(String reportUnit)
    {
        this.reportUnit = reportUnit;
    }

    public String getReportUnit()
    {
        return reportUnit;
    }
    public void setProjectMsg(String projectMsg)
    {
        this.projectMsg = projectMsg;
    }

    public String getProjectMsg()
    {
        return projectMsg;
    }
    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }

    public BigDecimal getAmount()
    {
        return amount;
    }
    public void setReceivableAmount(BigDecimal receivableAmount)
    {
        this.receivableAmount = receivableAmount;
    }

    public BigDecimal getReceivableAmount()
    {
        return receivableAmount;
    }
    public void setReceivableTime(LocalDate receivableTime)
    {
        this.receivableTime = receivableTime;
    }

    public LocalDate getReceivableTime()
    {
        return receivableTime;
    }
    public void setInvoiceNumber(String invoiceNumber)
    {
        this.invoiceNumber = invoiceNumber;
    }

    public String getInvoiceNumber()
    {
        return invoiceNumber;
    }
    public void setInvoiceTime(LocalDate invoiceTime)
    {
        this.invoiceTime = invoiceTime;
    }

    public LocalDate getInvoiceTime()
    {
        return invoiceTime;
    }
    public void setRate(String rate)
    {
        this.rate = rate;
    }

    public String getRate()
    {
        return rate;
    }
    public void setInvoiceType(String invoiceType)
    {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceType()
    {
        return invoiceType;
    }
    public void setInvoiceAmount(BigDecimal invoiceAmount)
    {
        this.invoiceAmount = invoiceAmount;
    }

    public BigDecimal getInvoiceAmount()
    {
        return invoiceAmount;
    }
    public void setArrivalTime(LocalDate arrivalTime)
    {
        this.arrivalTime = arrivalTime;
    }

    public LocalDate getArrivalTime()
    {
        return arrivalTime;
    }
    public void setReceivableNumber(BigDecimal receivableNumber)
    {
        this.receivableNumber = receivableNumber;
    }

    public BigDecimal getReceivableNumber()
    {
        return receivableNumber;
    }
    public void setUnpaidAmount(BigDecimal unpaidAmount)
    {
        this.unpaidAmount = unpaidAmount;
    }

    public BigDecimal getUnpaidAmount()
    {
        return unpaidAmount;
    }
    public void setPayType(String payType)
    {
        this.payType = payType;
    }

    public String getPayType()
    {
        return payType;
    }
    public void setReimbursementRate(String reimbursementRate)
    {
        this.reimbursementRate = reimbursementRate;
    }

    public String getReimbursementRate()
    {
        return reimbursementRate;
    }
    public void setIsReal(String isReal)
    {
        this.isReal = isReal;
    }

    public String getIsReal()
    {
        return isReal;
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
    public void setCreditedAmount(BigDecimal creditedAmount)
    {
        this.creditedAmount = creditedAmount;
    }

    public BigDecimal getCreditedAmount()
    {
        return creditedAmount;
    }
    public void setKickback(BigDecimal kickback)
    {
        this.kickback = kickback;
    }

    public BigDecimal getKickback()
    {
        return kickback;
    }
    public void setTaxesCollected(BigDecimal taxesCollected)
    {
        this.taxesCollected = taxesCollected;
    }

    public BigDecimal getTaxesCollected()
    {
        return taxesCollected;
    }
    public void setRealAmount(BigDecimal realAmount)
    {
        this.realAmount = realAmount;
    }

    public BigDecimal getRealAmount()
    {
        return realAmount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("financialStatementId", getFinancialStatementId())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("orderNumber", getOrderNumber())
                .append("reportUnit", getReportUnit())
                .append("projectMsg", getProjectMsg())
                .append("amount", getAmount())
                .append("receivableAmount", getReceivableAmount())
                .append("receivableTime", getReceivableTime())
                .append("invoiceNumber", getInvoiceNumber())
                .append("invoiceTime", getInvoiceTime())
                .append("rate", getRate())
                .append("invoiceType", getInvoiceType())
                .append("invoiceAmount", getInvoiceAmount())
                .append("arrivalTime", getArrivalTime())
                .append("receivableNumber", getReceivableNumber())
                .append("unpaidAmount", getUnpaidAmount())
                .append("payType", getPayType())
                .append("reimbursementRate", getReimbursementRate())
                .append("isReal", getIsReal())
                .append("publisher", getPublisher())
                .append("businessManager", getBusinessManager())
                .append("remark", getRemark())
                .append("creditedAmount", getCreditedAmount())
                .append("kickback", getKickback())
                .append("taxesCollected", getTaxesCollected())
                .append("realAmount", getRealAmount())
                .toString();
    }
}
