package com.qk.dm.dataquality.params.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author shenpj
 * @date 2021/11/25 3:19 下午
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DqcSchedulerDTO {

  /** 流程定义code */
  @NotNull(message = "流程定义ID不能为空！")
  Long processDefinitionCode;

  /** 作业ID */
  @NotNull(message = "作业ID不能为空！")
  private String jobId;
}
