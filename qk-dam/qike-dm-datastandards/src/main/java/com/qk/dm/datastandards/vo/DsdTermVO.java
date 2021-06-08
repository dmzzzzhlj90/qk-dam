package com.qk.dm.datastandards.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DsdTermVO {

    /**
     * ID编号
     */
    @ExcelProperty("ID编号")
    private Integer id;

    /**
     * 中文名称
     */
    @NotBlank(message = "中文名称不能为空！")
    @ExcelProperty("中文名称")
    private String chineseName;

    /**
     * 英文名称
     */
    @NotBlank(message = "英文名称不能为空！")
    @ExcelProperty("英文名称")
    private String englishName;

    /**
     * 英文简称
     */
    @NotBlank(message = "英文简称不能为空！")
    @ExcelProperty("英文简称")
    private String shortEnglishName;

    /**
     * 词根名称
     */
    @NotBlank(message = "词根名称不能为空！")
    @ExcelProperty("词根名称")
    private String rootName;

    /**
     * 状态
     */
    @ExcelProperty("状态")
    private Integer state;


}
