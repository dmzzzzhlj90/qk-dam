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
public class DasApiCreateRequestParasVO {

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
