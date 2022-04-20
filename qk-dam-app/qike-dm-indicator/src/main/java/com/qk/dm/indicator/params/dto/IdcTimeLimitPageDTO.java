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
public class IdcTimeLimitPageDTO {

  private Pagination pagination;

  /** 限定名称 */
  private String limitName;
  /** 开始时间 */
  private String startTime;
  /** 结束时间 */
  private String endTime;
}
