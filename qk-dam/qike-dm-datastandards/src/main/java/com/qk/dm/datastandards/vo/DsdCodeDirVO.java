package com.qk.dm.datastandards.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DsdCodeDirVO {


    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 码表标准分类ID
     */
//    @NotBlank(message = "码表标准分类ID不能为空！")
    private String codeDirId;

    /**
     * 码表标准分类名称
     */
    @NotBlank(message = "码表标准分类名称不能为空！")
    private String codeDirName;

    /**
     * 父级ID
     */
    @NotBlank(message = "目录父级ID不能为空！")
    private String parentId;

    /**
     * 描述
     */
    private String description;


}
