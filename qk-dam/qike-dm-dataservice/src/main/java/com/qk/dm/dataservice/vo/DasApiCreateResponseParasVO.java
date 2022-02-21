package com.qk.dm.dataservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 新建API_配置方式_请求参数VO
 *
 * @author wjq
 * @date 20210907
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DasApiCreateResponseParasVO {

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
