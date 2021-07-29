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
    private String code_table_id;

    /**
     * 中文名称
     */
    private String name_ch;

    /**
     * 英文名称
     */
    private String name_en;


    /**
     * 数据类型
     */
    private String data_type;

}