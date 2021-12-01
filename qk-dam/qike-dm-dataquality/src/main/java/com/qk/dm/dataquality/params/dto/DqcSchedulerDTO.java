package com.qk.dm.dataquality.params.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author shenpj
 * @date 2021/11/25 3:19 下午
 * @since 1.0.0
 */
@Data
public class DqcSchedulerDTO {

  /** 主键ID */
  @NotNull(message = "id不能为空！")
  private Long id;

  /** 作业ID */
  @NotNull(message = "作业ID不能为空！")
  private String jobId;
}
