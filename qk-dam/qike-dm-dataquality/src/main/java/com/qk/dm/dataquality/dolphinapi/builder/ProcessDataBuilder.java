package com.qk.dm.dataquality.dolphinapi.builder;

import com.qk.dam.commons.util.GsonUtil;
import com.qk.dm.dataquality.constant.RuleTypeEnum;
import com.qk.dm.dataquality.dolphinapi.dto.ResourceDTO;
import com.qk.dm.dataquality.vo.DqcSchedulerBasicInfoVO;
import com.qk.dm.dataquality.vo.DqcSchedulerInfoVO;
import com.qk.dm.dataquality.vo.DqcSchedulerRulesVO;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.dolphinscheduler.common.enums.Priority;
import org.apache.dolphinscheduler.common.enums.TaskType;
import org.apache.dolphinscheduler.common.model.TaskNode;
import org.apache.dolphinscheduler.common.process.Property;
import org.apache.dolphinscheduler.common.task.shell.ShellParameters;
import org.apache.dolphinscheduler.dao.entity.ProcessData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 构建ProcessData对象
 *
 * @author wjq
 * @date 2021/11/19
 * @since 1.0.0
 */
@Builder
@EqualsAndHashCode(callSuper = false)
@Data
public class ProcessDataBuilder {

    public static final String TASKS_NAME_MATCH = "tasks-";
    public static final String TASK_TIMEOUT = "{\"strategy\":\"\",\"interval\":null,\"enable\":false}";
    public static final String TASK_RUN_FLAG = "NORMAL";
    public static final String TASK_CONDITION_RESULT = "{\"successNode\":[\"\"],\"failedNode\":[\"\"]}";
    public static final String TASK_NULL_VALUE = "";
    public static final int TASK_MAX_RETRY_TIMES = 0;
    public static final int TASK_RETRY_INTERVAL = 1;
    public static final String TASK_WORKER_GROUP = "default";

    private final ProcessData processData = new ProcessData();

    public ProcessData getProcessData() {
        return processData;
    }

    public ProcessDataBuilder info(DqcSchedulerInfoVO dqcSchedulerInfoVO, ResourceDTO mySqlScriptResource) {
        taskNode(dqcSchedulerInfoVO);
        globalParams();
        timeout();
        tenantId();
        return this;
    }

    public ProcessDataBuilder taskNode(DqcSchedulerInfoVO dqcSchedulerInfoVO) {
        processData.setTasks(getTaskNodes(dqcSchedulerInfoVO));
        return this;
    }

    public ProcessDataBuilder globalParams() {
        List<Property> globalParams = new ArrayList<>();
        processData.setGlobalParams(globalParams);
        return this;
    }

    public ProcessDataBuilder timeout() {
        //TODO 超时时间
        processData.setTimeout(0);
        return this;

    }

    public ProcessDataBuilder tenantId() {
        //TODO 租户编码
        processData.setTenantId(0);
        return this;
    }

    /**
     * 构建任务实例执行节点信息集合
     *
     * @param dqcSchedulerInfoVO
     * @return List<TaskNode>
     */
    public List<TaskNode> getTaskNodes(DqcSchedulerInfoVO dqcSchedulerInfoVO) {
        List<TaskNode> tasks = new ArrayList<>();

        DqcSchedulerBasicInfoVO basicInfoVO = dqcSchedulerInfoVO.getDqcSchedulerBasicInfoVO();
        List<DqcSchedulerRulesVO> rulesVOList = dqcSchedulerInfoVO.getDqcSchedulerRulesVOList();

        for (DqcSchedulerRulesVO rulesVO : rulesVOList) {
            tasks.add(setTaskNodeInfo(basicInfoVO, rulesVO));
        }
        return tasks;
    }

    private TaskNode setTaskNodeInfo(DqcSchedulerBasicInfoVO basicInfoVO, DqcSchedulerRulesVO rulesVO) {
        TaskNode taskNode = null;
        try {
            taskNode = new TaskNode();
            setTaskNodeBasicInfo(basicInfoVO, rulesVO, taskNode);
            setTaskNodeShellParameters(basicInfoVO, rulesVO, taskNode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return taskNode;
    }

    private void setTaskNodeBasicInfo(DqcSchedulerBasicInfoVO basicInfoVO, DqcSchedulerRulesVO rulesVO, TaskNode taskNode) throws IOException {
        taskNode.setId(TASKS_NAME_MATCH + basicInfoVO.getJobId());
        taskNode.setName(Objects.requireNonNull(RuleTypeEnum.getVal(rulesVO.getScanType())).getName());
        taskNode.setDesc(Objects.requireNonNull(RuleTypeEnum.getVal(rulesVO.getScanType())).getName());
        taskNode.setTimeout(TASK_TIMEOUT);
        taskNode.setRunFlag(TASK_RUN_FLAG);
        taskNode.setConditionResult(TASK_CONDITION_RESULT);
        taskNode.setDependence(TASK_NULL_VALUE);
        taskNode.setMaxRetryTimes(TASK_MAX_RETRY_TIMES);
        taskNode.setRetryInterval(TASK_RETRY_INTERVAL);
        taskNode.setTaskInstancePriority(Priority.MEDIUM);
        taskNode.setWorkerGroup(TASK_WORKER_GROUP);
        taskNode.setPreTasks(TASK_NULL_VALUE);
    }

    private void setTaskNodeShellParameters(DqcSchedulerBasicInfoVO basicInfoVO, DqcSchedulerRulesVO rulesVO, TaskNode taskNode) {
        ShellParameters shellParameters = new ShellParameters();
        taskNode.setType(TaskType.SHELL.getDescp());

        //TODO 需要查询获取资源信息
        shellParameters.setResourceList(new ArrayList<>());
        shellParameters.setLocalParams(new ArrayList<>());
        //TODO 需要定义参数信息
        shellParameters.setRawScript("SQL");
        taskNode.setParams(GsonUtil.toJsonString(shellParameters));

    }

}
