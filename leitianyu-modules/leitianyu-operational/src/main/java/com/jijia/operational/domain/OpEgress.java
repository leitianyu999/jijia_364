package com.jijia.operational.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jijia.common.core.annotation.Excel;
import com.jijia.common.core.web.domain.BaseEntity;

/**
 * 外出检测台账对象 op_egress
 *
 * @author leitianyu
 * @date 2023-02-12
 */
public class OpEgress extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 外出台账id */
    private Long egressId;

    /** 委托单位 */
    @Excel(name = "委托单位")
    private String entrustmentUnit;

    /** 工程名称 */
    @Excel(name = "工程名称")
    private String projecyName;

    /** 工程地点 */
    @Excel(name = "工程地点")
    private String projectWay;

    /** 检测日期 */
    @Excel(name = "检测日期")
    private String detectTime;

    /** 检测人员 */
    @Excel(name = "检测人员")
    private String detectPeople;

    /** 检测项目 */
    @Excel(name = "检测项目")
    private String detectItems;

    /** 车辆 */
    @Excel(name = "车辆")
    private String car;

    /** 检测数量 */
    @Excel(name = "检测数量")
    private String amount;

    /** 业务经理 */
    @Excel(name = "业务经理")
    private String businessManager;

    /** 委托人 */
    @Excel(name = "委托人")
    private String addressee;

    /** 委托人电话 */
    @Excel(name = "委托人电话")
    private String addresseeTel;

    @Excel(name = "文件")
    private String filePath;



    public void setEgressId(Long egressId)
    {
        this.egressId = egressId;
    }

    public Long getEgressId()
    {
        return egressId;
    }
    public void setProjecyName(String projecyName)
    {
        this.projecyName = projecyName;
    }

    public String getProjecyName()
    {
        return projecyName;
    }
    public void setProjectWay(String projectWay)
    {
        this.projectWay = projectWay;
    }

    public String getProjectWay()
    {
        return projectWay;
    }
    public void setDetectTime(String detectTime)
    {
        this.detectTime = detectTime;
    }

    public String getDetectTime()
    {
        return detectTime;
    }
    public void setDetectPeople(String detectPeople)
    {
        this.detectPeople = detectPeople;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getDetectPeople()
    {
        return detectPeople;
    }
    public void setDetectItems(String detectItems)
    {
        this.detectItems = detectItems;
    }

    public String getDetectItems()
    {
        return detectItems;
    }
    public void setCar(String car)
    {
        this.car = car;
    }

    public String getCar()
    {
        return car;
    }
    public void setAmount(String amount)
    {
        this.amount = amount;
    }

    public String getAmount()
    {
        return amount;
    }
    public void setBusinessManager(String businessManager)
    {
        this.businessManager = businessManager;
    }

    public String getBusinessManager()
    {
        return businessManager;
    }
    public void setAddressee(String addressee)
    {
        this.addressee = addressee;
    }

    public String getAddressee()
    {
        return addressee;
    }
    public void setAddresseeTel(String addresseeTel)
    {
        this.addresseeTel = addresseeTel;
    }

    public String getAddresseeTel()
    {
        return addresseeTel;
    }
    public void setEntrustmentUnit(String entrustmentUnit)
    {
        this.entrustmentUnit = entrustmentUnit;
    }

    public String getEntrustmentUnit()
    {
        return entrustmentUnit;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("egressId", getEgressId())
                .append("projecyName", getProjecyName())
                .append("projectWay", getProjectWay())
                .append("detectTime", getDetectTime())
                .append("detectPeople", getDetectPeople())
                .append("detectItems", getDetectItems())
                .append("car", getCar())
                .append("amount", getAmount())
                .append("businessManager", getBusinessManager())
                .append("addressee", getAddressee())
                .append("addresseeTel", getAddresseeTel())
                .append("entrustmentUnit", getEntrustmentUnit())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}

