package com.qk.dm.reptile.params.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 维度字段信息
 * @author zys
 * @date 2021/12/8 10:59
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RptDimensionInfoColumnDTO {
  /**
   * 主键id（修改接口为必填项）
   */
  private Long id;

  /**
   * 维度目录id
   */
  private Long dimensionId;

  /**
   * 描述
   */
  private String description;

  /**
   * 维度字段名称
   */
  @NotBlank(message = "维度名不能为空")
  private String dimensionColumnName;

  /**
   * 修改时间
   */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date gmtModified;

  /**
   * 创建时间
   */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date gmtCreate;

  /**
   * 创建人id
   */
  private Long createUserid;

  /**
   * 修改人id
   */
  private Long updateUserid;

  /**
   * 创建人姓名
   */
  private String createUsername;

  /**
   * 修改人姓名
   */
  private String updateUsername;

  /**
   * 维度字段编码
   */
  @NotBlank(message = "维度编码不能为空")
  private String dimensionColumnCode;
}