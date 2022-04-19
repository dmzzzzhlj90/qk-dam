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
public class IdcCompositePageDTO {

  private Pagination pagination;

  /** 开始时间 */
  private String beginDay;

  /** 结束时间 */
  private String endDay;

  /** 原子指标名称 */
  private String compositeIndicatorName;

  /** 复合指标编码 */
  private String compositeIndicatorCode;

  /** 指标名称或指标编码 */
  private String nameOrCode;
}
