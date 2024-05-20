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
 * 财务台账对象 op_financial_msg
 *
 * @author leitianyu
 * @date 2024-01-17
 */
public class OpFinancialMsgInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long financialId;

    /** 收款时间 */
    @JsonFormat(pattern = "yyyy/MM/dd")
    @Excel(name = "收款时间", width = 30, dateFormat = "yyyy/MM/dd")
    private LocalDate collectionTime;

    /** 委托单位 */
    @Excel(name = "委托单位")
    private String reportUnit;

    /** 工程名称 */
    @Excel(name = "工程名称")
    private String projectMsg;

    /** 转入金额 */
    @Excel(name = "转入金额")
    private BigDecimal intoAmount;

    /** 转出金额 */
    @Excel(name = "转出金额")
    private BigDecimal outAmount;

    /** 下单人 */
    @Excel(name = "下单人")
    private String publisher;

    /** 业务经理 */
    @Excel(name = "业务经理")
    private String businessManager;

    /** 收款/支出方式 */
    @Excel(name = "微信/网银/现金方式")
    private String payType;

    /** 公司 */
    @Excel(name = "公司")
    private String companyMsg;

    /** 创建者 */
    @Excel(name = "创建者")
    private String createBy;

    /** 更新者 */
    @Excel(name = "更新者")
    private String updateBy;

    @Override
    public String getCreateBy() {
        return createBy;
    }

    @Override
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    @Override
    public String getUpdateBy() {
        return updateBy;
    }

    @Override
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    private List<Long> financialIds;

    public List<Long> getFinancialIds() {
        return financialIds;
    }

    public void setFinancialIds(List<Long> financialIds) {
        this.financialIds = financialIds;
    }

    public void setFinancialId(Long financialId)
    {
        this.financialId = financialId;
    }

    public Long getFinancialId()
    {
        return financialId;
    }
    public void setCollectionTime(LocalDate collectionTime)
    {
        this.collectionTime = collectionTime;
    }

    public LocalDate getCollectionTime()
    {
        return collectionTime;
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
    public void setIntoAmount(BigDecimal intoAmount)
    {
        this.intoAmount = intoAmount;
    }

    public BigDecimal getIntoAmount()
    {
        return intoAmount;
    }
    public void setOutAmount(BigDecimal outAmount)
    {
        this.outAmount = outAmount;
    }

    public BigDecimal getOutAmount()
    {
        return outAmount;
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
    public void setPayType(String payType)
    {
        this.payType = payType;
    }

    public String getPayType()
    {
        return payType;
    }
    public void setCompanyMsg(String companyMsg)
    {
        this.companyMsg = companyMsg;
    }

    public String getCompanyMsg()
    {
        return companyMsg;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("financialId", getFinancialId())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("collectionTime", getCollectionTime())
                .append("reportUnit", getReportUnit())
                .append("projectMsg", getProjectMsg())
                .append("intoAmount", getIntoAmount())
                .append("outAmount", getOutAmount())
                .append("publisher", getPublisher())
                .append("businessManager", getBusinessManager())
                .append("payType", getPayType())
                .append("remark", getRemark())
                .append("companyMsg", getCompanyMsg())
                .toString();
    }
}
