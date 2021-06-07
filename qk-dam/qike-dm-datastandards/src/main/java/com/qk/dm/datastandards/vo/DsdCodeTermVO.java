package com.qk.dm.datastandards.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DsdCodeTermVO {

    /**
     * ID编号
     */
    @ExcelProperty("ID编号")
    private Integer id;

    /**
     * 码表分类编码
     */
    @ExcelProperty("码表分类编码")
    private Integer codeDirId;

    /**
     * 码表编码
     */
    @ExcelProperty("码表编码")
    private String codeId;

    /**
     * 码表名称
     */
    @ExcelProperty("码表名称")
    private String codeName;

    /**
     * 数据类型编码
     */
    @ExcelProperty("数据类型编码")
    private Integer termId;

    /**
     * 描述
     */
    @ExcelProperty("描述")
    private String description;


}
