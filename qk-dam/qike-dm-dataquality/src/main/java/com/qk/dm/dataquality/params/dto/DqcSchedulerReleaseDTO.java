package com.qk.dm.dataquality.params.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author shenpj
 * @date 2021/11/25 3:19 下午
 * @since 1.0.0
 */
@Data
public class DqcSchedulerReleaseDTO {

  /** 主键ID */
  @NotNull(message = "主键ID不能为空！")
  private Long id;

  /** 调度状态 "OFFLINE":"下线","ONLINE":"上线" */
  @NotNull(message = "调度状态不能为空！")
  private String schedulerState;
}
