package com.jijia.camunda.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jijia.system.api.domain.SysDept;
import com.jijia.system.api.domain.SysMenu;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Treeselect树结构实体类
 * 
 * @author leitianyu
 */
@Data
public class TreeSelect implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 节点ID */
    private Long id;

    /** 节点名称 */
    private String label;

    private boolean disabled;

    private CmdCategoryVo categoryVo;

    /** 子节点 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelect> children;

    public TreeSelect()
    {

    }

    public TreeSelect(CmdCategoryVo categoryVo)
    {
        this.id = categoryVo.getCategoryId();
        this.label = categoryVo.getName();
        if (categoryVo.getModelId() == null) {
            this.setDisabled(true);
        }
        this.categoryVo = categoryVo;
        this.children = categoryVo.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }


}
