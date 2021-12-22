package com.qk.dm.dataquality.dolphinapi.builder;

import com.qk.dm.dataquality.dolphinapi.constant.SchedulerConstant;
import com.qk.dm.dataquality.dolphinapi.dto.TaskRelationDTO;
import com.qk.dm.dataquality.vo.DqcSchedulerBasicInfoVO;
import com.qk.dm.dataquality.vo.DqcSchedulerRulesVO;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
public class TaskRelationsBuilder {

    private final List<TaskRelationDTO> taskRelations = new ArrayList<>();


    public TaskRelationsBuilder info(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO, Integer version) {
        buildTaskRelations(dqcSchedulerBasicInfoVO,version);
        return this;
    }

    /**
     * 构建流程定义节点关联集合对象 taskRelations
     *
     * @param dqcSchedulerBasicInfoVO
     * @param version
     * @return
     */
    private List<TaskRelationDTO> buildTaskRelations(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO, Integer version) {

        List<DqcSchedulerRulesVO> dqcSchedulerRulesVOList = dqcSchedulerBasicInfoVO.getDqcSchedulerRulesVOList();

        AtomicInteger index = new AtomicInteger();
        for (DqcSchedulerRulesVO rulesVO : dqcSchedulerRulesVOList) {
            TaskRelationDTO taskRelationDTO = setTaskRelation(rulesVO,version);
            taskRelations.add(taskRelationDTO);
            index.incrementAndGet();
        }
        return taskRelations;
    }

    /**
     * 构建单个流程定义节点关联
     *
     * @param rulesVO
     * @param version
     * @return
     */
    private TaskRelationDTO setTaskRelation(DqcSchedulerRulesVO rulesVO, Integer version) {
        TaskRelationDTO taskRelation = new TaskRelationDTO();
        //设置taskCode关联节点信息
        taskRelation.setPostTaskCode(rulesVO.getTaskCode());

        //设置其他信息
        taskRelation.setName(SchedulerConstant.NULL_VALUE);
        taskRelation.setPreTaskCode(SchedulerConstant.ZERO_VALUE);
        taskRelation.setPreTaskVersion(SchedulerConstant.ZERO_VALUE);
        taskRelation.setPostTaskVersion(version);
        taskRelation.setConditionType(SchedulerConstant.ZERO_VALUE);

        //设置条件参数
        taskRelation.setConditionParams(new HashMap<>(16));

        return taskRelation;
    }


    public List<TaskRelationDTO> getTaskRelationList() {
        return taskRelations;
    }
}
