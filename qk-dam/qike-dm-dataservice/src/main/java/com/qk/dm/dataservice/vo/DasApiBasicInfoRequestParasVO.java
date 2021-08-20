package com.qk.dm.dataservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DasApiBasicInfoRequestParasVO {

  /** http请求参数 */
  private String paraName;

  /** 入参位置 */
  private String paraPosition;

  /** 参数类型 */
  private String paraType;

  /** 是否必填 */
  private boolean necessary;

  /** 允许空值 */
  private boolean supportNull;

  /** 示例值 */
  private String defaultValue;

  /** 默认值 */
  private String exampleValue;

  /** 描述 */
  private String description;
}
