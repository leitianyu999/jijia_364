package com.jijia.operational.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jijia.common.core.annotation.Excel;
import com.jijia.common.core.web.domain.BaseEntity;
import com.jijia.operational.utils.BusinessStatusConverter;
import com.jijia.operational.utils.LocalDateConverter;
import com.jijia.operational.utils.StatusConverter;
import com.jijia.operational.utils.StringConverter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
 * 业务部对象 op_business_msg
 *
 * @author leitianyu
 * @date 2024-01-15
 */
@ExcelIgnoreUnannotated
public class OpBusinessMsgVo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long businessId;

    /** 报告单位 */
    @ExcelProperty(value = {"业务部","报告单位"})
    private String reportUnit;

    /** 工程名称 */
    @ExcelProperty(value = {"业务部","工程名称"})
    private String projectMsg;

    /** 下单人 */
    @ExcelProperty(value = {"业务部","下单人"})
    private String publisher;

    /** 业务经理 */
    @ExcelProperty(value = {"业务部","业务经理"})
    private String businessManager;

    /** 状态 */
    @ExcelProperty(value = {"业务部","状态"},converter = BusinessStatusConverter.class)
    private Integer status;

    /** 收款时间 */
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    @ExcelProperty(value = {"业务部","收款时间"},converter = LocalDateConverter.class)
    private LocalDate collectionTime;

    /** 金额 */
    @ExcelProperty(value = {"业务部","金额"})
    private BigDecimal amount;

    @ExcelProperty(value = {"业务部","备注"})
    /** 备注 */
    private String remark;

    @ExcelProperty(value = {"业务部","是否导入"} , converter = StringConverter.class)
    private String isImport;

    private String filePath;

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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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
    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Integer getStatus()
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

