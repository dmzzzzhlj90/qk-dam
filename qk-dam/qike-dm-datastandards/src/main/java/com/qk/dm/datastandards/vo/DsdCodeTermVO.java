package com.qk.dm.datastandards.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ContentRowHeight(20)
@HeadRowHeight(20)
@ColumnWidth(25)
public class  DsdCodeTermVO {

    /**
     * ID编号
     */
    @ExcelIgnore
    @ExcelProperty("ID编号")
    private Integer id;

    /**
     * 中文表名称
     */
    @NotBlank(message = "中文表名称不能为空！")
    @ExcelProperty("中文表名称")
    private String codeTableChnName;

    /**
     * 英文表名称
     */
    @NotNull(message = "英文表名称不能为空！")
    @ExcelProperty("英文表名称")
    private String codeTableEnName;

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
     * 码表层级
     */
    @NotBlank(message = "码表层级不能为空！")
    @ExcelProperty("码表层级")
    private String codeDirLevel;

    /**
     * 码表目录Id
     */
    @ExcelIgnore
    @NotBlank(message = "码表目录Id不能为空！")
    @ExcelProperty("码表目录Id")
    private String codeDirId;

    /**
     * 码表层级目录名称
     */
//    @NotBlank(message = "码表层级目录名称不能为空！")
    @ExcelProperty("码表层级目录名称")
    @ExcelIgnore
    private String codeDirLevelName;

    /**
     * 数据类型编码
     */
    //    @NotNull(message = "数据类型编码不能为空！")
    @ExcelProperty("数据类型编码")
    private Integer termId;

    /**
     * 描述
     */
    @ExcelProperty("描述")
    private String description;

    /**
     * 修改时间
     */
    @ExcelIgnore
    @ExcelProperty("修改时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtModified;
}
