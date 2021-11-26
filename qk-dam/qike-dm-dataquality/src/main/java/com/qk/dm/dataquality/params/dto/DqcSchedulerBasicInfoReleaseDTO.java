package com.qk.dm.dataquality.params.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author shenpj
 * @date 2021/11/25 3:19 下午
 * @since 1.0.0
 */
@Data
public class DqcSchedulerBasicInfoReleaseDTO {

  /** 主键ID */
  @NotNull(message = "id不能为空！")
  private Long id;

  /** 调度开启状态 0-停止 1-启动 */
  @NotNull(message = "启动状态不能为空！")
  private Integer schedulerOpenState;
}
