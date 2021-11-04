package com.qk.dm.datastandards.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ContentRowHeight(20)
@HeadRowHeight(20)
@ColumnWidth(25)
public class DsdCodeInfoVO {

  /** 主键ID */
  @ExcelIgnore private Long id;

  /** 所属目录ID */
  @ExcelIgnore
  @NotNull(message = "所属目录ID不能为空！")
  private String codeDirId;

  /** 所属目录层级 */
  @ExcelProperty("所属目录层级")
  @NotBlank(message = "所属目录层级不能为空！")
  private String codeDirLevel;

  /** 基础配置-表名 */
  @ExcelProperty("表名")
  @NotBlank(message = "表名不能为空！")
  private String tableName;

  /** 基础配置-表编码 */
  @ExcelProperty("表编码")
  @NotBlank(message = "表名不能为空！")
  private String tableCode;

  /** 基础配置-描述 */
  @ExcelProperty("描述")
  private String tableDesc;

  /** 修改时间 */
  @ExcelIgnore
  @ExcelProperty("修改时间")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date gmtModified;

  /** 建表配置字段集合 */
  @ExcelIgnore private List<CodeTableFieldsVO> codeTableFieldsList;

  /** 配置字段编码 */
  @ExcelProperty("字段编码")
  private String codeTableId;

  /** 字段中文名称 */
  @ExcelProperty("字段中文名称")
  private String nameCh;

  /** 字段英文名称 */
  @ExcelProperty("字段英文名称")
  private String nameEn;

  /** 字段数据类型 */
  @ExcelProperty("字段数据类型")
  private String dataType;
}
