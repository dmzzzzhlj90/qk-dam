package com.qk.dm.authority.vo.powervo;

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

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 *资源VO
 * @author zys
 * @date 2022/2/24 17:03
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ContentRowHeight(20)
@HeadRowHeight(20)
@ColumnWidth(25)
public class ResourceVO {
  /**
   * 主键id(修改时候为必填字段)
   */
  @ExcelIgnore
  private Long id;

  /**
   * 资源（API）名称
   */
  @ExcelProperty(value = "资源名称",order = 1)
  @NotBlank(message = "资源（API）名称不能为空")
  private String name;

  /**
   * 标识
   */
  @ExcelProperty(value = "资源标识",order = 2)
  @NotBlank(message = "标识不能为空")
  private String powerSign;

  /**
   * 网址路径
   */
  @ExcelProperty(value = "网址路径",order = 3)
  @NotBlank(message = "网址路径不能为空")
  private String path;

  /**
   * 描述
   */
  @ExcelProperty(value = "描述",order = 4)
  private String description;

  /**
   * 创建人id
   */
  @ExcelIgnore
  private Long createUserid;

  /**
   * 修改人id
   */
  @ExcelIgnore
  private Long updateUserid;

  /**
   * 创建人名称
   */
  @ExcelProperty(value = "创建人名称",order = 5)
  private String createName;

  /**
   * 修改人名称
   */
  @ExcelIgnore
  private String updateName;

  /**
   * 创建时间
   */
  @ExcelIgnore
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date gmtCreate;

  /**
   * 修改时间
   */
  @ExcelIgnore
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date gmtModified;

  /**
   * 父级id（API类型fid默认为-1）
   */
  @ExcelProperty(value = "父级id(API时为-1)",order = 6)
  private Long pid;

  /**
   * 服务id
   */
  @ExcelProperty(value = "服务id",order = 7)
  private Long serviceId;

  /**
   * 0表示API，1表示资源
   */
  @ExcelProperty(value = "资源类型（0表示API，1表示资源）",order = 8)
  private Integer type;

  /**
   * 资源uuid
   */
  @ExcelIgnore
  private String resourcesid;
}