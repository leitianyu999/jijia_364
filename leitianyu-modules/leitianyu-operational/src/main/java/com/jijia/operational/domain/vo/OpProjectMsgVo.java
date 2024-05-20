package com.jijia.operational.domain.vo;

import com.jijia.common.core.annotation.Excel;
import com.jijia.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 工程类型对象 op_project_msg
 *
 * @author leitianyu
 * @date 2023-02-05
 */
public class OpProjectMsgVo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long projectId;

    /** 委托单位 */
    @Excel(name = "委托单位")
    private String entrustmentUnit;

    /** 工程名称 */
    @Excel(name = "工程名称")
    private String projectName;

    /** 工程类型 */
    @Excel(name = "工程类型")
    private Integer projectType;

    public void setProjectId(Long projectId)
    {
        this.projectId = projectId;
    }

    public Long getProjectId()
    {
        return projectId;
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

    public Integer getProjectType() {
        return projectType;
    }

    public void setProjectType(Integer projectType) {
        this.projectType = projectType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("projectId", getProjectId())
                .append("entrustmentUnit", getEntrustmentUnit())
                .append("projectName", getProjectName())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}

