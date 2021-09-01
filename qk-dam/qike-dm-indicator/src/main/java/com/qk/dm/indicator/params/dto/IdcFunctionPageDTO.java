package com.qk.dm.indicator.params.dto;

import com.qk.dam.jpa.pojo.Pagination;
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
public class IdcFunctionPageDTO {

  private Pagination pagination;

  /** 开始时间 */
  private String beginDay;

  /** 结束时间 */
  private String endDay;

  /** 函数名称 */
  private String name;

  /** 函数 */
  private String function;

  /** 引擎 */
  private String engine;

  /** 类型名称 */
  private String typeName;
}
