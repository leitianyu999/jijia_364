package com.jijia.operational.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
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
 * 财务台账明细表对象 op_financial_data
 *
 * @author leitianyu
 * @date 2024-01-21
 */
@ExcelIgnoreUnannotated
public class OpFinancialDataVo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long financialCode;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    @ExcelProperty(value = {"财务部明细台账","收款时间"},converter = LocalDateConverter.class)
    private LocalDate collectionTime;

    @ExcelProperty(value = {"财务部明细台账","公司"})
    private String companyMsg;

    /** 委托单位 */
    @ExcelProperty(value = {"财务部明细台账","委托单位"})
    private String reportUnit;

    /** 工程名称 */
    @ExcelProperty(value = {"财务部明细台账","工程名称"})
    private String projectMsg;

    /** 检测项目名称 */
    @ExcelProperty(value = {"财务部明细台账","检测项目名称"})
    private String detectName;

    /** 报告编号 */
    @ExcelProperty(value = {"财务部明细台账","报告编号"})
    private String reportSerialNumber;

    /** 单价 */
    @ExcelProperty(value = {"财务部明细台账","单价"})
    private BigDecimal price;

    /** 数量 */
    @ExcelProperty(value = {"财务部明细台账","数量"})
    private BigDecimal amount;

    @ExcelProperty(value = {"财务部明细台账","小计"})
    private BigDecimal subtotal;


    /** 收款情况 */
    @ExcelProperty(value = {"财务部明细台账","收款情况"})
    private String collectionMsg;

    /** 管理费 */
    @ExcelProperty(value = {"财务部明细台账","管理费"})
    private BigDecimal managementFee;

    /** 其他费用 */
    @ExcelProperty(value = {"财务部明细台账","其他费用"})
    private BigDecimal otherFee;

    @ExcelProperty(value = {"财务部明细台账","备注"})
    /** 备注 */
    private String remark;

    @ExcelProperty(value = {"财务部明细台账","是否导入"} , converter = StringConverter.class)
    private String isImport;


    /** financial_id */
    private Long financialId;

    private double total;


    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDate getCollectionTime() {
        return collectionTime;
    }

    public void setCollectionTime(LocalDate collectionTime) {
        this.collectionTime = collectionTime;
    }

    public String getCompanyMsg() {
        return companyMsg;
    }

    public void setCompanyMsg(String companyMsg) {
        this.companyMsg = companyMsg;
    }

    public String getReportUnit() {
        return reportUnit;
    }

    public void setReportUnit(String reportUnit) {
        this.reportUnit = reportUnit;
    }

    public String getProjectMsg() {
        return projectMsg;
    }

    public void setProjectMsg(String projectMsg) {
        this.projectMsg = projectMsg;
    }



    public String getIsImport() {
        return isImport;
    }

    public void setIsImport(String isImport) {
        this.isImport = isImport;
    }

    public void setFinancialCode(Long financialCode)
    {
        this.financialCode = financialCode;
    }

    public Long getFinancialCode()
    {
        return financialCode;
    }
    public void setDetectName(String detectName)
    {
        this.detectName = detectName;
    }

    public String getDetectName()
    {
        return detectName;
    }
    public void setReportSerialNumber(String reportSerialNumber)
    {
        this.reportSerialNumber = reportSerialNumber;
    }

    public String getReportSerialNumber()
    {
        return reportSerialNumber;
    }
    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    public BigDecimal getPrice()
    {
        return price;
    }
    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }

    public BigDecimal getAmount()
    {
        return amount;
    }
    public void setCollectionMsg(String collectionMsg)
    {
        this.collectionMsg = collectionMsg;
    }

    public String getCollectionMsg()
    {
        return collectionMsg;
    }
    public void setManagementFee(BigDecimal managementFee)
    {
        this.managementFee = managementFee;
    }

    public BigDecimal getManagementFee()
    {
        return managementFee;
    }
    public void setOtherFee(BigDecimal otherFee)
    {
        this.otherFee = otherFee;
    }

    public BigDecimal getOtherFee()
    {
        return otherFee;
    }
    public void setFinancialId(Long financialId)
    {
        this.financialId = financialId;
    }

    public Long getFinancialId()
    {
        return financialId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("financialCode", getFinancialCode())
                .append("detectName", getDetectName())
                .append("reportSerialNumber", getReportSerialNumber())
                .append("price", getPrice())
                .append("amount", getAmount())
                .append("collectionMsg", getCollectionMsg())
                .append("managementFee", getManagementFee())
                .append("otherFee", getOtherFee())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("updateBy", getUpdateBy())
                .append("remark", getRemark())
                .append("financialId", getFinancialId())
                .toString();
    }
}
