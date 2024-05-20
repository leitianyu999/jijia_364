package com.jijia.operational.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jijia.common.core.annotation.Excel;
import com.jijia.common.core.web.domain.BaseEntity;

/**
 * 财务台账明细表对象 op_financial_data
 *
 * @author leitianyu
 * @date 2024-01-21
 */
public class OpFinancialData extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long financialCode;

    /** 委托单位 */
    @Excel(name = "委托单位")
    private String reportUnit;

    /** 工程名称 */
    @Excel(name = "工程名称")
    private String projectMsg;

    @Excel(name = "公司")
    private String companyMsg;

    /** 检测项目名称 */
    @Excel(name = "检测项目名称")
    private String detectName;

    /** 报告编号 */
    @Excel(name = "报告编号")
    private String reportSerialNumber;

    /** 单价 */
    @Excel(name = "单价")
    private BigDecimal price;

    /** 数量 */
    @Excel(name = "数量")
    private BigDecimal amount;


    /** 收款情况 */
    @Excel(name = "收款情况")
    private String collectionMsg;

    /** 管理费 */
    @Excel(name = "管理费")
    private BigDecimal managementFee;

    /** 其他费用 */
    @Excel(name = "其他费用")
    private BigDecimal otherFee;

    /** financial_id */
    private Long financialId;

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

    public String getCompanyMsg() {
        return companyMsg;
    }

    public void setCompanyMsg(String companyMsg) {
        this.companyMsg = companyMsg;
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
