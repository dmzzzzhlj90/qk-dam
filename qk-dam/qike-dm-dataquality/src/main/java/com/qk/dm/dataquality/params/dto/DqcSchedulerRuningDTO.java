package com.qk.dm.dataquality.params.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author shenpj
 * @date 2021/11/25 3:19 下午
 * @since 1.0.0
 */
@Data
public class DqcSchedulerRuningDTO {

  /** 主键ID */
  @NotNull(message = "id不能为空！")
  private Long id;

  /** 运行实例状态 1-运行中 2-停止 */
  @NotNull(message = "运行实例状态不能为空！")
  private Integer runInstanceState;

  /** 实例id，停止时需要 */
  private Integer instanceId;
}
