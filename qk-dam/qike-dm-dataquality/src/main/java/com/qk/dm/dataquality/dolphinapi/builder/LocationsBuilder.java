package com.qk.dm.dataquality.dolphinapi.builder;

import com.qk.dm.dataquality.dolphinapi.dto.LocationsDTO;
import com.qk.dm.dataquality.dolphinapi.dto.TaskNodeLocation;
import com.qk.dm.dataquality.vo.DqcSchedulerBasicInfoVO;
import com.qk.dm.dataquality.vo.DqcSchedulerRulesVO;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    private final LocationsDTO locationsDTO = new LocationsDTO();

    public LocationsDTO taskNodeLocations() {
        return locationsDTO;
    }

    public LocationsBuilder info(Map<String, TaskNodeLocation> taskNodeLocationMap) {
        locationsDTO.setTaskNodeLocationMap(taskNodeLocationMap);
        return this;
    }


}
