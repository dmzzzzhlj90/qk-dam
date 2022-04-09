package com.qk.dm.dataquality.dolphinapi.handler.impl;

import com.qk.dm.dataquality.dolphinapi.builder.LocationsBuilder;
import com.qk.dm.dataquality.dolphinapi.config.DolphinSchedulerInfoConfig;
import com.qk.dm.dataquality.dolphinapi.dto.TaskNodeLocation;
import com.qk.dm.dataquality.dolphinapi.handler.LocationsHandler;
import com.qk.dm.dataquality.vo.DqcSchedulerBasicInfoVO;

import java.util.List;

/**
 * DAG流程图,任务节点位置信息_构建执行器
 *
 * @author wjq
 * @date 2021/11/26
 * @since 1.0.0
 */
public class DqcLocationsHandler implements LocationsHandler<DqcSchedulerBasicInfoVO> {


    /**
     * 构建任务节点位置信息
     *
     * @param dqcSchedulerBasicInfoVO
     * @param dolphinSchedulerInfoConfig
     * @return locationsDTO
     */
    @Override
    public List<TaskNodeLocation> buildLocations(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO,
                                                 DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig) {
        return LocationsBuilder.builder()
                .build()
                .info(dqcSchedulerBasicInfoVO, dolphinSchedulerInfoConfig)
                .getLocations();
    }

}
