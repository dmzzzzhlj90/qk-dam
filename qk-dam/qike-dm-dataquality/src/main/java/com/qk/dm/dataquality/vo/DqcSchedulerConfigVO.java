package com.qk.dm.dataquality.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 数据质量_规则调度_配置信息VO
 *
 * @author wjq
 * @date 2021/11/10
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DqcSchedulerConfigVO {

  /** 主键ID */
  private Long id;

  /** 作业id */
  @NotBlank(message = "作业id不能为空！")
  private String jobId;

  /** 调度执行方式 1-手动执行 2-调度执行 */
  @NotNull(message = "调度执行方式不能为空！")
  private Integer runType;

  /** 有效日期开始 */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date effectiveTimeStart;

  /** 有效日期结束 */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date effectiveTimeEnt;

  /** 调度周期 1-分钟 2-小时 3-天 4-周 */
  private Integer schedulerCycle;

  /** 间隔时间 调度周期为周 1-周一 2-周二 3-周三 4-周四 5-周五 6-周六 7-周日 */
  private String schedulerIntervalTime;

  /** 调度时间 */
  private String schedulerTime;

  /** 调度执行cron表达式 */
  private String cron;

  /** 调度定时id */
  private Integer schedulerId;

  /** 创建人 */
  private String createUserid;

  /** 修改人 */
  private String updateUserid;

  /** 创建时间 */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date gmtCreate;

  /** 修改时间 */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date gmtModified;
}
