package com.qk.dm.dataquality.params.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 数据质量_规则调度_基础信息VO
 *
 * @author wjq
 * @date 2021/11/10
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DqcSchedulerBasicInfoDTO {

  /** 主键ID */
  private Long id;

  /** 作业id */
  private String jobId;

  /** 作业名称 */
  @NotBlank(message = "作业名称不能为空！")
  private String jobName;

  /** 分类目录 */
  @NotNull(message = "分类目录不能为空！")
  private String dirId;

  /** 提示级别 */
  @NotNull(message = "提示级别不能为空！")
  private String notifyLevel;

  /** 通知状态 */
  @NotNull(message = "通知状态不能为空！")
  private Boolean notifyState;

  /** 通知类型 */
  @NotNull(message = "通知类型不能为空！")
  private String notifyType;

  /** 主题，多个以逗号分隔 */
  @NotBlank(message = "主题不能为空！")
  private String notifyThemeId;
}
