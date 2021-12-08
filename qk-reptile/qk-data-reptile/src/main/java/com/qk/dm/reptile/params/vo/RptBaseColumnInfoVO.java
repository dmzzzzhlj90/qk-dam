package com.qk.dm.reptile.params.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 配置信息字段出参VO
 * @author zys
 * @date 2021/12/8 11:16
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RptBaseColumnInfoVO {
  /**
   * 主键id（修改接口为必填项）
   */
  private Long id;

  /**
   * 配置表id
   */
  private Long baseInfoId;

  /**
   * 描述
   */
  private String description;

  /**
   * 手动执行
   */
  private String manualExecution;

  /**
   * 正则
   */
  private Long regular;

  /**
   * 选择器类型
   */
  private Long selector;

  /**
   * 维度字段名称
   */
  private String columnName;

  /**
   * 创建时间
   */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date gmtCreate;

  /**
   * 修改时间
   */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date gmtModified;
}