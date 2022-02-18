package com.qk.dm.dataservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wjq
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DasApiBasicInfoRequestParasVO {

  /** http请求参数 */
  private String paraName;

  /** http请求参数中文名称 */
  private String paraCHNName;

  /** 入参位置 */
  private String paraPosition;

  /** 参数类型 */
  private String paraType;

  /** 是否必填 */
  private boolean necessary;

  /** 允许空值 */
  private boolean supportNull;

  /** 示例值 */
  private Object defaultValue;

  /** 默认值 */
  private String exampleValue;

  /** 描述 */
  private String description;
}
