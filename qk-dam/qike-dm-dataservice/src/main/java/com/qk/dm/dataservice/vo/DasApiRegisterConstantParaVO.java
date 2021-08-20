package com.qk.dm.dataservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
