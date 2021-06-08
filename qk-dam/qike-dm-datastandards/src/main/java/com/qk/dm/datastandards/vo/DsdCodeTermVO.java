package com.qk.dm.datastandards.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

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
    @NotBlank(message = "码表分类编码不能为空！")
    @ExcelProperty("码表分类编码")
    private Integer codeDirId;

    /**
     * 码表编码
     */
    @NotBlank(message = "码表编码不能为空！")
    @ExcelProperty("码表编码")
    private String codeId;

    /**
     * 码表名称
     */
    @NotBlank(message = "码表名称不能为空！")
    @ExcelProperty("码表名称")
    private String codeName;

    /**
     * 数据类型编码
     */
    @NotBlank(message = "数据类型编码不能为空！")
    @ExcelProperty("数据类型编码")
    private Integer termId;

    /**
     * 描述
     */
    @ExcelProperty("描述")
    private String description;


}
