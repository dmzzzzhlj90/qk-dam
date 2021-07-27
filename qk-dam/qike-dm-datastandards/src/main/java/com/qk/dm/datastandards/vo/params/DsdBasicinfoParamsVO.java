package com.qk.dm.datastandards.vo.params;

import com.qk.dm.datastandards.vo.Pagination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DsdBasicinfoParamsVO {

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
