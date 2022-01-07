package com.qk.dam.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据标准返回值
 * @author zys
 * @date 2022/1/4 19:36
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DsdBasicinfoParamsVO {
  /** ID编号 */
  private Long id;

  /** 标准名称 */
  private String dsdName;

  /** 标准代码 */

  private String dsdCode;
}