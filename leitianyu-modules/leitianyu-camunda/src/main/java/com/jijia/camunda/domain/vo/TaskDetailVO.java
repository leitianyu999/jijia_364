package com.jijia.camunda.domain.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author LoveMyOrange
 * @create 2022-10-16 9:38
 */
@Data
public class TaskDetailVO {
    private String taskId;
    private String activityId;
    private String name;
    private Date createTime;
    private Date endTime;
    private String signImage;
    private List<OptionVO> optionVOList;
    private List<CommentVO> commentVOList;
}
