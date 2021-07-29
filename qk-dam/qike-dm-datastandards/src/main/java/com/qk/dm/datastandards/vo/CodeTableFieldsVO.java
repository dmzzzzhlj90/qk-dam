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
public class CodeTableFieldsVO {

    /**
     * 配置字段编码
     */
    @NotBlank(message = "配置字段编码不能为空！")
    private String code_table_id;

    /**
     * 中文名称
     */
    @NotBlank(message = "字段中文名称不能为空！")
    private String name_ch;

    /**
     * 英文名称
     */
    @NotBlank(message = "英文名称不能为空！")
    private String name_en;


    /**
     * 数据类型
     */
    @NotBlank(message = "数据类型不能为空！")
    private String data_type;

}