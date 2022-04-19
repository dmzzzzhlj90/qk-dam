package com.qk.dm.dataquality.dolphinapi.builder;

import com.qk.dm.dataquality.dolphinapi.config.DolphinSchedulerInfoConfig;
import com.qk.dm.dataquality.dolphinapi.dto.TaskNodeLocation;
import com.qk.dm.dataquality.vo.DqcSchedulerBasicInfoVO;
import com.qk.dm.dataquality.vo.DqcSchedulerRulesVO;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
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

    private final List<TaskNodeLocation> locations = new ArrayList<>();


    public LocationsBuilder info(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO,
                                 DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig) {
        buildLocations(dqcSchedulerBasicInfoVO, dolphinSchedulerInfoConfig);
        return this;
    }

    /**
     * 构建位置信息集合对象Locations
     *
     * @param dqcSchedulerBasicInfoVO
     * @param dolphinSchedulerInfoConfig
     * @return
     */
    private List<TaskNodeLocation> buildLocations(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO,
                                                  DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig) {

        List<DqcSchedulerRulesVO> dqcSchedulerRulesVOList = dqcSchedulerBasicInfoVO.getDqcSchedulerRulesVOList();

        AtomicInteger index = new AtomicInteger();
        for (DqcSchedulerRulesVO rulesVO : dqcSchedulerRulesVOList) {
            TaskNodeLocation taskNodeLocation = setTaskNodeLocation(index.get(), rulesVO, dolphinSchedulerInfoConfig);
            locations.add(taskNodeLocation);
            index.incrementAndGet();
        }
        return locations;
    }

    /**
     * 构建任务单节点位置信息
     *
     * @param index
     * @param rulesVO
     * @param dolphinSchedulerInfoConfig
     * @return
     */
    private TaskNodeLocation setTaskNodeLocation(int index,
                                                 DqcSchedulerRulesVO rulesVO,
                                                 DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig) {
        TaskNodeLocation taskNodeLocation = new TaskNodeLocation();
        //设置taskCode关联节点信息
        taskNodeLocation.setTaskCode(rulesVO.getTaskCode());

        //设置X轴位置
        taskNodeLocation.setX(dolphinSchedulerInfoConfig.getXLocationInitialValue());
        //设置Y轴位置
        taskNodeLocation.setY(
                dolphinSchedulerInfoConfig.getYLocationInitialValue()
                        + dolphinSchedulerInfoConfig.getLocationIncrement() * index);
        return taskNodeLocation;
    }

    public List<TaskNodeLocation> getLocations() {
        return locations;
    }
}
