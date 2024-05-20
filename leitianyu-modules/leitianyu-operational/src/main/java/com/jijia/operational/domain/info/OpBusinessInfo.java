package com.jijia.operational.domain.info;

import com.alibaba.excel.annotation.ExcelProperty;
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
 * 业务部对象 op_business_msg
 *
 * @author leitianyu
 * @date 2024-01-15
 */
public class OpBusinessInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long businessId;

    /** 报告单位 */
    @Excel(name = "报告单位")
    private String reportUnit;

    /** 工程名称 */
    @Excel(name = "工程名称")
    private String projectMsg;

    /** 下单人 */
    @Excel(name = "下单人")
    private String publisher;

    /** 业务经理 */
    @Excel(name = "业务经理")
    private String businessManager;

    /** 状态 */
    @Excel(name = "状态")
    private List<Integer> status;

    /** 收款时间 */
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    @Excel(name = "收款时间", width = 30, dateFormat = "yyyy/MM/dd")
    private LocalDate collectionTime;

    /** 金额 */
    @Excel(name = "金额")
    private BigDecimal amount;

    @Excel(name = "备注")
    /** 备注 */
    private String remark;

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    private List<Long> businessIds;

    public List<Long> getBusinessIds() {
        return businessIds;
    }

    public void setBusinessIds(List<Long> businessIds) {
        this.businessIds = businessIds;
    }

    public void setBusinessId(Long businessId)
    {
        this.businessId = businessId;
    }

    public Long getBusinessId()
    {
        return businessId;
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
    public void setStatus(List<Integer> status)
    {
        this.status = status;
    }

    public List<Integer> getStatus()
    {
        return status;
    }
    public void setCollectionTime(LocalDate collectionTime)
    {
        this.collectionTime = collectionTime;
    }

    public LocalDate getCollectionTime()
    {
        return collectionTime;
    }
    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }

    public BigDecimal getAmount()
    {
        return amount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("businessId", getBusinessId())
                .append("reportUnit", getReportUnit())
                .append("projectMsg", getProjectMsg())
                .append("publisher", getPublisher())
                .append("businessManager", getBusinessManager())
                .append("status", getStatus())
                .append("collectionTime", getCollectionTime())
                .append("amount", getAmount())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}

