package com.jijia.camunda.mapper.newM;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jijia.camunda.domain.CmdCategory;
import com.jijia.camunda.domain.CmdForm;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * TODO
 *
 * @author Raoqian
 * @company ZhiDao Tech
 * @create 2024/11/7
 */
@Mapper
public interface CmdFormMapper extends BaseMapper<CmdForm> {

    /**
     * 查询BPM 单列表
     *
     * @param cmdForm BPM 单
     * @return BPM 单集合
     */
    public List<CmdForm> selectCmdFormList(CmdForm cmdForm);

}
