package com.qk.dm.dataquality.dolphinapi.handler.impl;

import com.qk.dm.dataquality.constant.DqcConstant;
import com.qk.dm.dataquality.dolphinapi.builder.LocationsBuilder;
import com.qk.dm.dataquality.dolphinapi.config.DolphinSchedulerInfoConfig;
import com.qk.dm.dataquality.dolphinapi.constant.SchedulerConstant;
import com.qk.dm.dataquality.dolphinapi.dto.LocationsDTO;
import com.qk.dm.dataquality.dolphinapi.dto.TaskNodeLocation;
import com.qk.dm.dataquality.dolphinapi.handler.LocationsHandler;
import com.qk.dm.dataquality.vo.DqcSchedulerBasicInfoVO;
import com.qk.dm.dataquality.vo.DqcSchedulerRulesVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wjq
 * @date 2021/11/26
 * @since 1.0.0
 */
public class DqcLocationsHandler implements LocationsHandler<DqcSchedulerBasicInfoVO> {

    @Override
    public LocationsDTO buildLocationsDTO(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO,
                                          DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig) {
        Map<String, TaskNodeLocation> taskNodeLocationMap = getLocationsDTOInfo(dqcSchedulerBasicInfoVO,dolphinSchedulerInfoConfig);

        return LocationsBuilder.builder()
                .build()
                .info(taskNodeLocationMap)
                .getLocationsDTO();
    }

    private Map<String, TaskNodeLocation> getLocationsDTOInfo(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO,
                                                              DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig) {
        Map<String, TaskNodeLocation> taskNodeLocationMap = new HashMap<>(16);

        List<DqcSchedulerRulesVO> dqcSchedulerRulesVOList = dqcSchedulerBasicInfoVO.getDqcSchedulerRulesVOList();

        AtomicInteger index = new AtomicInteger();
        for (DqcSchedulerRulesVO rulesVO : dqcSchedulerRulesVOList) {
            String key = dolphinSchedulerInfoConfig.getTasksNameMatch() + index.get();
            TaskNodeLocation taskNodeLocation = setTaskNodeLocation(index.get(), rulesVO,dolphinSchedulerInfoConfig);
            taskNodeLocationMap.put(key, taskNodeLocation);
            index.incrementAndGet();
        }
        return taskNodeLocationMap;
    }

    private TaskNodeLocation setTaskNodeLocation(int index,
                                                 DqcSchedulerRulesVO rulesVO,
                                                 DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig) {
        TaskNodeLocation taskNodeLocation = new TaskNodeLocation();
        taskNodeLocation.setName(rulesVO.getRuleType() + rulesVO.getRuleTempId());
        taskNodeLocation.setNodenumber(dolphinSchedulerInfoConfig.getLocationNodeNumber());
        taskNodeLocation.setTargetarr(SchedulerConstant.NULL_VALUE);
        taskNodeLocation.setX(dolphinSchedulerInfoConfig.getXLocationInitialValue());
        taskNodeLocation.setY(
                dolphinSchedulerInfoConfig.getYLocationInitialValue()
                        + dolphinSchedulerInfoConfig.getLocationIncrement() * index);
        return taskNodeLocation;
    }
}
