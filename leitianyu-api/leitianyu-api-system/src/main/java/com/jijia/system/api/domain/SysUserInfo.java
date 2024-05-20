package com.jijia.system.api.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jijia.common.core.annotation.Excel;
import com.jijia.common.core.web.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;


/**
 * 用户详细信息对象 sys_user_info
 *
 * @author leitianyu
 * @date 2023-02-03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户id */
    private Long userId;

    /** 身份证 */
    @Excel(name = "身份证")
    private String idCard;

    /** 民族 */
    @Excel(name = "民族")
    private String ethnic;

    /** 年龄 */
    @Excel(name = "年龄")
    private Integer age;

    /** 毕业学校 */
    @Excel(name = "毕业学校")
    private String graduationSchool;

    /** 学位 */
    @Excel(name = "学位")
    private String degree;

    /** 专业 */
    @Excel(name = "专业")
    private String professional;

    /** 毕业时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "毕业时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date graduationTime;

    /** 职称 */
    @Excel(name = "职称")
    private String professionalTitle;

    /** 政治面貌 */
    @Excel(name = "政治面貌")
    private String politicalLandscape;

    /** 户籍所在地 */
    @Excel(name = "户籍所在地")
    private String domiciliaryRegister;

    /** 入职时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入职时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date onboardingTime;

    /** 紧急联系人 */
    @Excel(name = "紧急联系人")
    private Long emergencyContactsTel;

    /** 现住址 */
    @Excel(name = "现住址")
    private String currentAddress;

    /** 转正时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "转正时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date regularStaffTime;

    /** 社保日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "社保日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date socialInsuranceTime;

    /** 参加工作年限 */
    @Excel(name = "参加工作年限")
    private Integer workTime;

    /** 开户行、工资账户 */
    @Excel(name = "开户行、工资账户")
    private String bankAccount;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

}
