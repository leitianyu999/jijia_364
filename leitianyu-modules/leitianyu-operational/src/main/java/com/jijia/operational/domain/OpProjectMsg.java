package com.jijia.operational.domain;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jijia.common.core.annotation.Excel;
import com.jijia.common.core.web.domain.BaseEntity;

/**
 * 工程类型对象 op_project_msg
 *
 * @author leitianyu
 * @date 2023-02-05
 */
@ExcelIgnoreUnannotated
public class OpProjectMsg extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long projectId;

    /** 插号工程编号 */
    @ExcelProperty(value = "插号工程编号")
    private String projectChahaoCode;

    /** 工程编号 */
    @ExcelProperty(value = "工程编号")
    private String projectCode;

    /** 委托单位 */
    @ExcelProperty(value = "委托单位")
    private String entrustmentUnit;

    /** 工程名称 */
    @ExcelProperty(value = "工程名称")
    private String projectName;

    /** 工程类型 */
    @ExcelProperty(value = "工程类型")
    private Integer projectType;

    /** 地区 */
    @ExcelProperty(value = "地区")
    private String region;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public void setProjectId(Long projectId)
    {
        this.projectId = projectId;
    }

    public Long getProjectId()
    {
        return projectId;
    }
    public void setProjectChahaoCode(String projectChahaoCode)
    {
        this.projectChahaoCode = projectChahaoCode;
    }

    public String getProjectChahaoCode()
    {
        return projectChahaoCode;
    }
    public void setProjectCode(String projectCode)
    {
        this.projectCode = projectCode;
    }

    public String getProjectCode()
    {
        return projectCode;
    }
    public void setEntrustmentUnit(String entrustmentUnit)
    {
        this.entrustmentUnit = entrustmentUnit;
    }

    public String getEntrustmentUnit()
    {
        return entrustmentUnit;
    }
    public void setProjectName(String projectName)
    {
        this.projectName = projectName;
    }

    public String getProjectName()
    {
        return projectName;
    }
    public void setProjectType(Integer projectType)
    {
        this.projectType = projectType;
    }

    public Integer getProjectType()
    {
        return projectType;
    }
    public void setRegion(String region)
    {
        this.region = region;
    }

    public String getRegion()
    {
        return region;
    }
    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("projectId", getProjectId())
                .append("projectChahaoCode", getProjectChahaoCode())
                .append("projectCode", getProjectCode())
                .append("entrustmentUnit", getEntrustmentUnit())
                .append("projectName", getProjectName())
                .append("projectType", getProjectType())
                .append("region", getRegion())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("delFlag", getDelFlag())
                .toString();
    }
}

