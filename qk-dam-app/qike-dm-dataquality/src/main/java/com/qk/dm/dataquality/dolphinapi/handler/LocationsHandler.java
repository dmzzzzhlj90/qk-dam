package com.qk.dm.dataquality.dolphinapi.handler;

import com.qk.dm.dataquality.dolphinapi.config.DolphinSchedulerInfoConfig;
import com.qk.dm.dataquality.dolphinapi.dto.TaskNodeLocation;

import java.util.List;

/**
 * DAG流程图_位置信息_处理器
 *
 * @author wjq
 * @date 2021/11/26
 * @since 1.0.0
 */
public interface LocationsHandler<T> {

    List<TaskNodeLocation> buildLocations(T t, DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig);

}
