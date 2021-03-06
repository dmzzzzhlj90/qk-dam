package com.qk.dm.constant;

/**
 * 自定义监听指标常量
 *
 * @author wjq
 * @date 2021/6/22 12:03
 * @since 1.0.0
 */
public class CustomMetricsConstant {
  /** 自定义指标_批次sql数据同步任务_指标名称 */
  public static final String CUSTOM_TASK_PICI_LOG = "custom_task_pici_log";
  /** 自定义指标_批次sql数据同步任务_指标标签 */
  public static final String PICI_TASK_TABLENAME = "pici_task_tablename";

  public static final String PICI_TASK_PICI = "pici_task_pici";
  public static final String PICI_TASK_IS_DOWN = "pici_task_is_down";
  public static final String PICI_TASK_IS_MYSQL_UPDATED = "pici_task_is_mysql_updated";
  public static final String PICI_TASK_IS_ES_UPDATED = "pici_task_is_es_updated";
  public static final String PICI_TASK_DOWN_TIME = "pici_task_down_time";
  public static final String PICI_TASK_UPDATED = "pici_task_updated";

  /** 自定义指标_监控调度任务_指标名称 */
  public static final String CUSTOM_TASK_SCHEDULER_PROCESS_INSTANCE =
      "custom_task_scheduler_process_instance";

  public static final String TASK_SCHEDULER_PROCESS_DEFINITION_CODE =
      "task_scheduler_process_definition_code";
  public static final String TASK_SCHEDULER_NAME = "task_scheduler_name";
  public static final String TASK_SCHEDULER_STATE = "task_scheduler_state";
  public static final String TASK_SCHEDULER_UPDATE_TIME = "task_scheduler_update_time";
  public static final String TASK_SCHEDULER_USER_NAME = "task_scheduler_user_name";
}
