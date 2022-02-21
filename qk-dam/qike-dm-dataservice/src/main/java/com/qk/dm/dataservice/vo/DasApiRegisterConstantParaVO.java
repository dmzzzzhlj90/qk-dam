package com.qk.dm.dataservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 注册API_常量参数VO
 *
 * @author wjq
 * @date 20210907
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DasApiRegisterConstantParaVO {

  /** 常量参数名称 */
  private String constantParaName;

  /** 参数位置 */
  private String constantParaPosition;

  /** 参数类型 */
  private String constantParaType;

  /** 参数值 */
  private String constantParaValue;

  /** 描述 */
  private String description;
}
