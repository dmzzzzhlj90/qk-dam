package com.qk.dm.indicator.params.dto;

import com.qk.dam.jpa.pojo.Pagination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdcDerivedPageDTO {

  private Pagination pagination;

  /** 衍生指标名称 */
  private String derivedIndicatorName;

  /** 开始时间 */
  private String startTime;
  /** 结束时间 */
  private String endTime;
}
