package com.qk.dm.datastandards.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ContentRowHeight(20)
@HeadRowHeight(20)
@ColumnWidth(25)
public class DsdBasicinfoVO {


    /**
     * ID编号
     */
    @ExcelProperty("ID编号")
    private Integer id;

    /**
     * 标准名称
     */
    @NotBlank(message = "标准名称不能为空！")
    @ExcelProperty("标准名称")
    private String dsdName;

    /**
     * 标准代码
     */
    @NotBlank(message = "标准代码不能为空！")
    @ExcelProperty("标准代码")
    private String dsdCode;

    /**
     * 字段名称
     */
    @NotBlank(message = "字段名称不能为空！")
    @ExcelProperty("字段名称")
    private String colName;
    /**
     * 数据类型
     */
    @NotBlank(message = "数据类型不能为空！")
    @ExcelProperty("数据类型")
    private String dataType;

    /**
     * 数据容量
     */
    @NotBlank(message = "数据容量不能为空！")
    @ExcelProperty("数据容量")
    private String dataCapacity;

    /**
     * 引用码表
     */
    @NotBlank(message = "标准代码不能为空！")
    @ExcelProperty("引用码表")
    private String useCodeId;

    /**
     * 码表字段
     */
//    @NotBlank(message = "码表字段不能为空！")
    @ExcelProperty("码表字段")
    @ExcelIgnore
    private String codeCol;

    /**
     * 标准层级
     */
    @NotBlank(message = "标准层级字段不能为空！")
    @ExcelProperty("标准层级")
    private String dsdLevel;


    /**
     * 描述
     */
    @ExcelProperty("描述")
    private String description;


}
