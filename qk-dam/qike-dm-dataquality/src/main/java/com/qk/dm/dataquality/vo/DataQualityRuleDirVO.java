package com.qk.dm.dataquality.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataQualityRuleDirVO {

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 数据标准分类编号
     */
    private String dirDsdId;

    /**
     * 数据标准分类名称
     */
    @NotBlank(message = "数据标准分类名称不能为空！")
    private String dirDsdName;

    /**
     * 父级id
     */
    @NotBlank(message = "目录父级id不能为空！")
    private String parentId;

    /**
     * 描述
     */
    private String description;

}
