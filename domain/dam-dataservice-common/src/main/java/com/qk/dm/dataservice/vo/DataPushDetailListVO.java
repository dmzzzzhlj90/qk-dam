package com.qk.dm.dataservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author shen
 * @version 1.0
 * @description:
 * @date 2021-07-12 14:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataPushDetailListVO {
  /** 企业名称 */
  @NotBlank(message = "企业名称不能为空！")
  private String entName;

  /** 注册号 */
  @NotBlank(message = "注册号不能为空！")
  private String uniscId;

  /** 统一代码查询企业ID */
  @NotBlank(message = "统一代码查询企业ID不能为空！")
  private String regNo;
}
