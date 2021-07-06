package com.qk.dm.service.impl;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.qk.dam.sqlloader.constant.LongGovConstant;
import com.qk.dam.sqlloader.repo.DolphinSchedulerAgg;
import com.qk.dam.sqlloader.vo.DolphinProcessInstanceVO;
import com.qk.dm.constant.CustomMetricsConstant;
import com.qk.dm.service.MxTaskSchedulerService;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wjq
 * @date 2021/6/22 17:00
 * @since 1.0.0
 */
@Service
public class MxTaskSchedulerServiceImpl implements MxTaskSchedulerService {

  private static final Log LOG = LogFactory.get("监控调度任务实例状态情况");
  private final MeterRegistry meterRegistry;

  @Autowired
  public MxTaskSchedulerServiceImpl(MeterRegistry meterRegistry) {
    this.meterRegistry = meterRegistry;
  }

  @Override
  public void sendProcessInstanceState() {
    String dataDay =
        DateTimeFormatter.ofPattern(LongGovConstant.DATE_TIME_PATTERN).format(LocalDateTime.now());
    LOG.info("开始查询当日【{}】调度任务实例数据数据", dataDay);
    SimpleDateFormat simpleDateFormat =
        new SimpleDateFormat(LongGovConstant.DATE_TIME_SECOND_PATTERN);

    List<DolphinProcessInstanceVO> processInstanceVOList =
        DolphinSchedulerAgg.schedulerProcessInstanceInfo(dataDay);
    LOG.info("查询到调度任务实例数据数量为:【{}】!", processInstanceVOList.size());

    LOG.info("开始同步调度任务实例数据到prometheus中......");
    processInstanceVOList.forEach(
        DPIV -> {
          int state = DPIV.getState();
          Gauge.builder(
                  CustomMetricsConstant.CUSTOM_TASK_SCHEDULER_PROCESS_INSTANCE,
                  state,
                  Integer::intValue)
              .tags(
                  CustomMetricsConstant.TASK_SCHEDULER_PROCESS_DEFINITION_CODE,
                  String.valueOf(DPIV.getProcess_definition_code()),
                  CustomMetricsConstant.TASK_SCHEDULER_NAME,
                  DPIV.getName(),
                  CustomMetricsConstant.TASK_SCHEDULER_STATE,
                  String.valueOf(DPIV.getState()),
                  CustomMetricsConstant.TASK_SCHEDULER_USER_NAME,
                  DPIV.getUser_name(),
                  CustomMetricsConstant.TASK_SCHEDULER_UPDATE_TIME,
                  DPIV.getUpdate_time())
              .register(meterRegistry);
          LOG.info(
              "同步任务实例ID为【{}】,名称为:【{}】的数据到prometheus中......",
              DPIV.getProcess_definition_code(),
              DPIV.getName());
        });
    LOG.info("成功同步调度任务实例数据到prometheus中!!!");
  }
}
