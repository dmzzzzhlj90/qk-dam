package com.qk.dm.dataquality.params.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author shenpj
 * @date 2021/11/25 2:45 下午
 * @since 1.0.0
 */
@Data
public class DqcRuleTemplateReleaseDTO {

  /** id */
  @NotNull(message = "id不能为空！")
  private Long id;

  /** 发布状态 "OFFLINE":"下线","RELEASE":"发布 */
  @NotNull(message = "发布状态不能为空！")
  private String publishState;
}
