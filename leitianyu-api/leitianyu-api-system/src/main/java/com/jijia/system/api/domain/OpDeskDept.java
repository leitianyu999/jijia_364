package com.jijia.system.api.domain;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.jijia.common.core.web.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 参数台账对象 op_parameter
 *
 * @author leitianyu
 * @date 2023-02-07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class OpDeskDept extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long deptId;

    private String deptName;

    private int permissionLevel;
}

