package com.qk.dm.dataservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DasApiDataSourceConfRequestParasVO {

  /** 绑定参数 */
  private String paraName;

  /** 绑定字段 */
  private String mappingName;

  /** 操作符 */
  private String conditionType;

  /** 后端参数 */
  private String backendParaName;

  /** 后端参数位置 */
  private String backendParaPosition;
}
