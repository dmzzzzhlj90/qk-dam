package com.qk.dm.service;

import org.springframework.stereotype.Service;

/**
 * 监控调度dolphinscheduler任务实例情况
 *
 * @author wjq
 * @date 2021/7/2 14:29
 * @since 1.0.0
 */
@Service
public interface MxTaskSchedulerService {

  void sendProcessInstanceState();
}
