package com.qk.dm.dataquality.dolphinapi.builder;

import com.qk.dm.dataquality.constant.RuleTypeEnum;
import com.qk.dm.dataquality.dolphinapi.dto.LocationsDTO;
import com.qk.dm.dataquality.dolphinapi.dto.TaskNodeLocation;
import com.qk.dm.dataquality.vo.DqcSchedulerBasicInfoVO;
import com.qk.dm.dataquality.vo.DqcSchedulerInfoVO;
import com.qk.dm.dataquality.vo.DqcSchedulerRulesVO;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 构建Locations对象
 *
 * @author wjq
 * @date 2021/11/24
 * @since 1.0.0
 */
@Builder
@EqualsAndHashCode(callSuper = false)
@Data
public class LocationsBuilder {
    public static final String TASKS_NAME_MATCH = "tasks-";
    public static final String LOCATION_NODE_NUMBER = "0";
    public static final int LOCATION_X_INITIAL_VALUE = 200;
    public static final int LOCATION_Y_INITIAL_VALUE = 200;
    public static final int LOCATION_INCREMENT = 150;

    private final LocationsDTO locationsDTO = new LocationsDTO();

    public LocationsDTO taskNodeLocations() {
        return locationsDTO;
    }

    public LocationsBuilder info(DqcSchedulerInfoVO dqcSchedulerInfoVO) {
        setLocationsDTO(dqcSchedulerInfoVO);
        return this;
    }

    private void setLocationsDTO(DqcSchedulerInfoVO dqcSchedulerInfoVO) {
        Map<String, TaskNodeLocation> taskNodeLocationMap = new HashMap<>(16);

        DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO = dqcSchedulerInfoVO.getDqcSchedulerBasicInfoVO();
        List<DqcSchedulerRulesVO> dqcSchedulerRulesVOList = dqcSchedulerInfoVO.getDqcSchedulerRulesVOList();

        AtomicInteger index = new AtomicInteger();
        for (DqcSchedulerRulesVO rulesVO : dqcSchedulerRulesVOList) {
            String key = TASKS_NAME_MATCH + index.get();
            TaskNodeLocation taskNodeLocation = setTaskNodeLocation(index.get(), rulesVO);
            taskNodeLocationMap.put(key, taskNodeLocation);
            index.incrementAndGet();
        }
        locationsDTO.setTaskNodeLocationMap(taskNodeLocationMap);
    }

    private TaskNodeLocation setTaskNodeLocation(int index, DqcSchedulerRulesVO rulesVO) {
        TaskNodeLocation taskNodeLocation = new TaskNodeLocation();
        taskNodeLocation.setName(rulesVO.getRuleType() + rulesVO.getRuleTempId());
        taskNodeLocation.setNodenumber(LOCATION_NODE_NUMBER);
        taskNodeLocation.setTargetarr("");
        taskNodeLocation.setX(LOCATION_X_INITIAL_VALUE);
        taskNodeLocation.setY(LOCATION_Y_INITIAL_VALUE + LOCATION_INCREMENT * index);
        return taskNodeLocation;
    }

}
