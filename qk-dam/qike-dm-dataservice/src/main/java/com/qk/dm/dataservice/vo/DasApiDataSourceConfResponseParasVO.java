package com.qk.dm.dataservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DasApiDataSourceConfResponseParasVO {

  /** 参数名 */
  private String paraName;

  /** 绑定字段 */
  private String mappingName;

  /** 参数类型 */
  private String paraType;

  /** 示例值 */
  private String exampleValue;

  /** 默认值 */
  private String defaultValue;

  /** 描述 */
  private String description;
}
