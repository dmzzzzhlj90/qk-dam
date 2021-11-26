package com.qk.dm.dataquality.dolphinapi.handler.impl;

import com.qk.dm.dataquality.dolphinapi.builder.LocationsBuilder;
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
    public static final String TASKS_NAME_MATCH = "tasks-";
    public static final String LOCATION_NODE_NUMBER = "0";
    public static final String TASK_NULL_VALUE = "";
    public static final int LOCATION_X_INITIAL_VALUE = 200;
    public static final int LOCATION_Y_INITIAL_VALUE = 200;
    public static final int LOCATION_INCREMENT = 150;

    @Override
    public LocationsDTO buildLocationsDTO(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO) {
        Map<String, TaskNodeLocation> taskNodeLocationMap = getLocationsDTOInfo(dqcSchedulerBasicInfoVO);

        return LocationsBuilder.builder()
                .build()
                .info(taskNodeLocationMap)
                .getLocationsDTO();
    }

    private Map<String, TaskNodeLocation> getLocationsDTOInfo(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO) {
        Map<String, TaskNodeLocation> taskNodeLocationMap = new HashMap<>(16);

        List<DqcSchedulerRulesVO> dqcSchedulerRulesVOList = dqcSchedulerBasicInfoVO.getDqcSchedulerRulesVOList();

        AtomicInteger index = new AtomicInteger();
        for (DqcSchedulerRulesVO rulesVO : dqcSchedulerRulesVOList) {
            String key = TASKS_NAME_MATCH + index.get();
            TaskNodeLocation taskNodeLocation = setTaskNodeLocation(index.get(), rulesVO);
            taskNodeLocationMap.put(key, taskNodeLocation);
            index.incrementAndGet();
        }
        return taskNodeLocationMap;
    }

    private TaskNodeLocation setTaskNodeLocation(int index, DqcSchedulerRulesVO rulesVO) {
        TaskNodeLocation taskNodeLocation = new TaskNodeLocation();
        taskNodeLocation.setName(rulesVO.getRuleType() + rulesVO.getRuleTempId());
        taskNodeLocation.setNodenumber(LOCATION_NODE_NUMBER);
        taskNodeLocation.setTargetarr(TASK_NULL_VALUE);
        taskNodeLocation.setX(LOCATION_X_INITIAL_VALUE);
        taskNodeLocation.setY(LOCATION_Y_INITIAL_VALUE + LOCATION_INCREMENT * index);
        return taskNodeLocation;
    }
}
