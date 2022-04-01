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
 * 访问（api）VO
 * @author zys
 * @date 2022/3/31 14:56
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ContentRowHeight(20)
@HeadRowHeight(20)
@ColumnWidth(25)
public class ResourceApiVO {
  /**
   * 主键id(修改为必填参)
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
   * 网址路径
   */
  @ExcelProperty(value = "网址路径",order = 2)
  @NotBlank(message = "网址路径不能为空")
  private String path;

  /**
   * 描述
   */
  @ExcelProperty(value = "描述",order = 3)
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
  @ExcelProperty(value = "创建人名称",order = 4)
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
   * 服务id
   */
  @ExcelProperty(value = "服务UUID",order = 5)
  @NotBlank(message = "服务uuid不能为空")
  private String serviceId;

  /**
   * api的uuid
   */
  @ExcelIgnore
  private String resourcesId;
}