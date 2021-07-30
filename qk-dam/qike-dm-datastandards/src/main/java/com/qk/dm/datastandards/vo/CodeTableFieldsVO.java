package com.qk.dm.datastandards.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
     * 字段中文名称
     */
    private String name_ch;

    /**
     * 字段英文名称
     */
    private String name_en;


    /**
     * 字段数据类型
     */
    private String data_type;

}