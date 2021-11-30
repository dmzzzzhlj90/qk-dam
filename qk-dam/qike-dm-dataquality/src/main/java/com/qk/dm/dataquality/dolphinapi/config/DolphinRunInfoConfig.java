package com.qk.dm.dataquality.dolphinapi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 调度引擎Dolphin Scheduler 规则调度配置信息
 *
 * @author wjq
 * @date 2021/11/17
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "dolphin-run-info", ignoreInvalidFields = true)
@Component
public class DolphinRunInfoConfig {
  /** 失败策略，默认 0-继续 */
  Integer failureStrategy;
  /** 流程实例优先级，默认 3-MEDIUM */
  Integer processInstancePriority;
  /** 发送策略，默认 1 都不发 */
  Integer warningType;
  /** 指令类型，默认 0-null */
  Integer execType;
  /** 执行方式，默认 1-串行 */
  Integer runMode;
  /** 任务依赖类型，默认 3-TASK_POST */
  Integer taskDependType;

  /** 定时时间 */
  String scheduleTime;
  /** 收件人，默认 null */
  String receivers;
  /** 收件人(抄送)，默认 null */
  String receiversCc;
  /** 开始节点列表(节点name)，默认 null */
  String startNodeList;
  /** 超时时间，默认 null */
  Integer timeout;
  /** 发送组ID,默认 0 */
  Integer warningGroupId = 0;
  /** Worker分组 默认 "default" */
  String workerGroup;
}
