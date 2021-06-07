package com.qk.dm.datastandards.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
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
    @ExcelProperty("中文名称")
    private String chineseName;

    /**
     * 英文名称
     */
    @ExcelProperty("英文名称")
    private String englishName;

    /**
     * 英文简称
     */
    @ExcelProperty("英文简称")
    private String shortEnglishName;

    /**
     * 词根名称
     */
    @ExcelProperty("词根名称")
    private String rootName;

    /**
     * 状态
     */
    @ExcelProperty("状态")
    private Integer state;


}
