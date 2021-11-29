package com.qk.dm.dataquality.dolphinapi.handler.impl;

import com.qk.dam.commons.util.GsonUtil;
import com.qk.dm.dataquality.dolphinapi.builder.ProcessDataBuilder;
import com.qk.dm.dataquality.dolphinapi.constant.Priority;
import com.qk.dm.dataquality.dolphinapi.dto.*;
import com.qk.dm.dataquality.dolphinapi.handler.ProcessDataHandler;
import com.qk.dm.dataquality.vo.DqcSchedulerBasicInfoVO;
import com.qk.dm.dataquality.vo.DqcSchedulerRulesVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wjq
 * @date 2021/11/26
 * @since 1.0.0
 */
public class DqcProcessDataHandler implements ProcessDataHandler<DqcSchedulerBasicInfoVO> {
    public static final String TASKS_NAME_MATCH = "tasks-";
    public static final String TASK_RUN_FLAG = "NORMAL";
    public static final String TASK_NULL_VALUE = "";
    public static final int TASK_MAX_RETRY_TIMES = 0;
    public static final int TASK_RETRY_INTERVAL = 1;
    public static final String TASK_WORKER_GROUP = "default";
    public static final String PYTHON_3_EXECUTE_COMMAND = "/opt/soft/python3/bin/python3";
    public static final String SPACE_PLACEHOLDER = " ";
    public static final String SINGLE_QUOTE = "'";
    public static final String TYPE_SHELL = "SHELL";

    @Override
    public ProcessDataDTO buildProcessDataDTO(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO, ResourceDTO mySqlScriptResource, TenantDTO tenantDTO) {
        List<TaskNodeDTO> taskNodes = getTaskNodes(dqcSchedulerBasicInfoVO, mySqlScriptResource);

        return ProcessDataBuilder.builder()
                .build()
                .info(taskNodes, tenantDTO)
                .getProcessData();
    }


    /**
     * 构建任务实例执行节点信息集合
     *
     * @param dqcSchedulerBasicInfoVO
     * @param mySqlScriptResource
     * @return List<TaskNode>
     */
    public List<TaskNodeDTO> getTaskNodes(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO, ResourceDTO mySqlScriptResource) {
        List<TaskNodeDTO> tasks = new ArrayList<>();
        List<DqcSchedulerRulesVO> rulesVOList = dqcSchedulerBasicInfoVO.getDqcSchedulerRulesVOList();
        AtomicInteger index = new AtomicInteger();

        for (DqcSchedulerRulesVO rulesVO : rulesVOList) {
            tasks.add(setTaskNodeInfo(dqcSchedulerBasicInfoVO, rulesVO, mySqlScriptResource, index.get()));
            index.incrementAndGet();
        }
        return tasks;
    }

    private TaskNodeDTO setTaskNodeInfo(DqcSchedulerBasicInfoVO basicInfoVO, DqcSchedulerRulesVO rulesVO, ResourceDTO mySqlScriptResource, int index) {
        TaskNodeDTO taskNode = null;
        try {
            taskNode = new TaskNodeDTO();
            setTaskNodeBasicInfo(rulesVO, taskNode, index);
            setTaskNodeShellParameters(basicInfoVO, rulesVO, taskNode, mySqlScriptResource);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return taskNode;
    }

    private void setTaskNodeBasicInfo(DqcSchedulerRulesVO rulesVO, TaskNodeDTO taskNode, int index) {
        taskNode.setId(TASKS_NAME_MATCH + index);
        taskNode.setName(rulesVO.getRuleType() + rulesVO.getRuleTempId());
        taskNode.setTimeout(setTimeOutDTO());
        taskNode.setRunFlag(TASK_RUN_FLAG);
        taskNode.setConditionResult(setConditionResultDTO());
        taskNode.setDependence(new HashMap<>(16));
        taskNode.setMaxRetryTimes(TASK_MAX_RETRY_TIMES);
        taskNode.setRetryInterval(TASK_RETRY_INTERVAL);
        taskNode.setTaskInstancePriority(Priority.MEDIUM);
        taskNode.setWorkerGroup(TASK_WORKER_GROUP);
        taskNode.setPreTasks(new ArrayList<>());
        taskNode.setDescription(TASK_NULL_VALUE);
    }

    private ConditionResultDTO setConditionResultDTO() {
        ConditionResultDTO conditionResultDTO = new ConditionResultDTO();
        List<String> arrayList = new ArrayList<>();
        arrayList.add(TASK_NULL_VALUE);
        conditionResultDTO.setSuccessNode(arrayList);
        conditionResultDTO.setFailedNode(arrayList);
        return conditionResultDTO;
    }

    private TimeOutDTO setTimeOutDTO() {
        TimeOutDTO timeOutDTO = new TimeOutDTO();
        timeOutDTO.setStrategy(TASK_NULL_VALUE);
        timeOutDTO.setEnable(false);
        return timeOutDTO;
    }

    private void setTaskNodeShellParameters(DqcSchedulerBasicInfoVO basicInfoVO, DqcSchedulerRulesVO rulesVO, TaskNodeDTO taskNode, ResourceDTO mySqlScriptResource) {
        taskNode.setType(TYPE_SHELL);
        List<ResourceDTO> resourceList = new ArrayList<>();
        resourceList.add(mySqlScriptResource);

        ShellParameters.ShellParametersBuilder shellParametersBuilder =
                ShellParameters.builder()
                        .resourceList(resourceList)
                        .localParams(new ArrayList<>())
                        .rawScript(setMysqlRuleScript(basicInfoVO, rulesVO, mySqlScriptResource));

        taskNode.setParams(shellParametersBuilder.build());
    }

    private String setMysqlRuleScript(DqcSchedulerBasicInfoVO basicInfoVO, DqcSchedulerRulesVO rulesVO, ResourceDTO mySqlScriptResource) {
        MysqlRawScript.MysqlRawScriptBuilder scriptBuilder = MysqlRawScript.builder();

        scriptBuilder.from_host("172.20.0.24").from_user("root").from_password("Zhudao123!").from_database("qkdam");
        scriptBuilder
                .search_sql(rulesVO.getScanSql());

        scriptBuilder
                .to_host("172.20.0.24")
                .to_user("root")
                .to_password("Zhudao123!")
                .to_database("qkdam");

        scriptBuilder
                .job_id(basicInfoVO.getJobId())
                .job_name(basicInfoVO.getJobName())
                .dir_id(basicInfoVO.getDirId())
                .rule_temp_id(rulesVO.getRuleTempId());

        MysqlRawScript mysqlRawScript = scriptBuilder.build();
        String mysqlRawScriptJson = GsonUtil.toJsonString(mysqlRawScript);

        return PYTHON_3_EXECUTE_COMMAND + SPACE_PLACEHOLDER + mySqlScriptResource.getRes() + SPACE_PLACEHOLDER + SINGLE_QUOTE + mysqlRawScriptJson + SINGLE_QUOTE;
    }
}
