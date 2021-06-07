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
public class DsdBasicinfoVO {


    /**
     * ID编号
     */
    @ExcelProperty("ID编号")
    private Integer id;

    /**
     * 标准代码
     */
    @ExcelProperty("标准代码")
    private String dsdId;

    /**
     * 标准名称
     */
    @ExcelProperty("标准名称")
    private String dsdName;

    /**
     * 数据容量
     */
    @ExcelProperty("数据容量")
    private String dataCapacity;

    /**
     * 引用码表
     */
    @ExcelProperty("引用码表")
    private String useCodeId;

    /**
     * 码表字段
     */
    @ExcelProperty("码表字段")
    private String codeCol;

    /**
     * 描述
     */
    @ExcelProperty("描述")
    private String description;

}
