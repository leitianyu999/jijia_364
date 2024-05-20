package com.jijia.operational.domain;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jijia.common.core.annotation.Excel;
import com.jijia.common.core.web.domain.BaseEntity;

/**
 * 参数台账对象 op_parameter
 *
 * @author leitianyu
 * @date 2023-02-07
 */
@ExcelIgnoreUnannotated
public class OpParameter extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 参数id */
    private Long parameterId;

    /** 参数代码 */
    @ExcelProperty(value = "参数代码")
    private String parameterCode;

    /** 最大份数 */
    @ExcelProperty(value = "数量")
    private Integer maxNumber;


    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public void setParameterId(Long parameterId)
    {
        this.parameterId = parameterId;
    }

    public Long getParameterId()
    {
        return parameterId;
    }
    public void setParameterCode(String parameterCode)
    {
        this.parameterCode = parameterCode;
    }

    public String getParameterCode()
    {
        return parameterCode;
    }
    public void setMaxNumber(Integer maxNumber)
    {
        this.maxNumber = maxNumber;
    }

    public Integer getMaxNumber()
    {
        return maxNumber;
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
                .append("parameterId", getParameterId())
                .append("parameterCode", getParameterCode())
                .append("maxNumber", getMaxNumber())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("delFlag", getDelFlag())
                .toString();
    }
}

