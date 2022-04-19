package com.qk.dm.dataquality.dolphinapi.handler;

import com.qk.dm.dataquality.dolphinapi.dto.TaskRelationDTO;
import com.qk.dm.dataquality.vo.DqcSchedulerBasicInfoVO;

import java.util.List;

/**
 * DAG流程图_位置信息_处理器
 *
 * @author wjq
 * @date 2021/11/26
 * @since 1.0.0
 */
public interface TaskRelationsHandler<T> {

    List<TaskRelationDTO> buildTaskRelations(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO, Integer version);

}
