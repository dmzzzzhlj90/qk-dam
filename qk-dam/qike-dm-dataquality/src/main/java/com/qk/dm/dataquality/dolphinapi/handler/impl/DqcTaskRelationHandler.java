package com.qk.dm.dataquality.dolphinapi.handler.impl;

import com.qk.dm.dataquality.dolphinapi.builder.TaskRelationsBuilder;
import com.qk.dm.dataquality.dolphinapi.dto.TaskRelationDTO;
import com.qk.dm.dataquality.dolphinapi.handler.TaskRelationsHandler;
import com.qk.dm.dataquality.vo.DqcSchedulerBasicInfoVO;

import java.util.List;

/**
 * 流程定义节点关联信息执行器
 *
 * @author wjq
 * @date 2021/11/26
 * @since 1.0.0
 */
public class DqcTaskRelationHandler implements TaskRelationsHandler<DqcSchedulerBasicInfoVO> {


    /**
     * 构建任务节点位置信息
     *
     * @param dqcSchedulerBasicInfoVO
     * @param version
     * @return locationsDTO
     */
    @Override
    public List<TaskRelationDTO> buildTaskRelations(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO, Integer version) {
        return TaskRelationsBuilder.builder()
                .build()
                .info(
                        dqcSchedulerBasicInfoVO,
                        version)
                .getTaskRelationList();
    }

}
