package com.qk.dm.indicator.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author shenpj
 * @date 2021/9/1 2:48 下午
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdcFunctionVO {
  private Long id;

  /** 函数名称 */
  private String name;

  /** 函数 */
  private String function;

  /** 引擎 */
  private String engine;

  /** 类型名称 */
  private String typeName;
}
