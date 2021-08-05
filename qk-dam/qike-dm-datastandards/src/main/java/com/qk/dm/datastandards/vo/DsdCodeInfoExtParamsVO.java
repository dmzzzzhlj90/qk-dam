package com.qk.dm.datastandards.vo;

import com.qk.dam.jpa.pojo.Pagination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DsdCodeInfoExtParamsVO {

  private Pagination pagination;

  /** 开始时间 */
  private String beginDay;

  /** 结束时间 */
  private String endDay;

  /** 关键码表信息表id */
  private String dsdCodeInfoId;

  /** 建表配置编码 */
  private String tableConfCode;

  /** 建表配置值 */
  private String tableConfValue;
}
