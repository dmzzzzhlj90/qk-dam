package com.qk.dam.entity;

import com.qk.dam.jpa.pojo.Pagination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
/**
 * 数据标准入参DTO
 */
public class DsdBasicInfoParamsDTO {

  private Pagination pagination;

  /** 标准名称 */
  private String dsdName;

  /** 标准代码 */
  private String dsdCode;

  /** 标准层级 */
  private String dsdLevel;

  /** 标准层级ID */
  private String dsdLevelId;

  /** 开始时间 */
  private String beginDay;

  /** 结束时间 */
  private String endDay;
}
