package com.qk.dm.dataservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DasApiRegisterVO {
  /** API基础信息 */
  private DasApiBasicInfoVO dasApiBasicInfoVO;

  /** 主键ID */
  private Long id;

  /** API基础信息ID */
  private String apiId;

  /** 请求协议 */
  private String protocolType;

  /** 请求方式 */
  private String requestType;

  /** 后端服务 HOST */
  private String backendHost;

  /** 后端服务 PATH */
  private String backendPath;

  /** 后端超时 (ms) */
  private String backendTimeout;

  /** 后端服务参数 */
  private String backendRequestParas;

  /** 常量参数 */
  private String backendConstants;

  /** 描述 */
  private String description;
}
