package com.qk.dm.dataquality.dolphinapi.executor;

import com.qk.dm.dataquality.dolphinapi.dto.TaskRelationDTO;
import com.qk.dm.dataquality.dolphinapi.handler.impl.DqcTaskRelationHandler;
import com.qk.dm.dataquality.vo.DqcSchedulerBasicInfoVO;

import java.util.List;

/**
 * 流程定义节点关联信息_构建执行器
 *
 * @author wjq
 * @date 2021/12/21
 * @since 1.0.0
 */
public class TaskRelationExecutor {

    public TaskRelationExecutor() {
        throw new IllegalStateException("Utility class");
    }

    public static List<TaskRelationDTO> dqcTaskRelations(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO, Integer version) {
        return new DqcTaskRelationHandler()
                .buildTaskRelations(
                        dqcSchedulerBasicInfoVO,
                        version);
    }

}
