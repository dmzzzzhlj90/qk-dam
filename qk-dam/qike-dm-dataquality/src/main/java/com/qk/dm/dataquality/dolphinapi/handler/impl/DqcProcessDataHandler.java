package com.qk.dm.dataquality.dolphinapi.handler.impl;

import com.qk.dam.commons.util.GsonUtil;
import com.qk.dm.dataquality.dolphinapi.builder.ProcessDataBuilder;
import com.qk.dm.dataquality.dolphinapi.config.DolphinSchedulerInfoConfig;
import com.qk.dm.dataquality.dolphinapi.constant.Priority;
import com.qk.dm.dataquality.dolphinapi.constant.SchedulerConstant;
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

    @Override
    public ProcessDataDTO buildProcessDataDTO(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO,
                                              ResourceDTO mySqlScriptResource,
                                              TenantDTO tenantDTO,
                                              DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig) {
        List<TaskNodeDTO> taskNodes = getTaskNodes(dqcSchedulerBasicInfoVO, mySqlScriptResource, dolphinSchedulerInfoConfig);

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
     * @param dolphinSchedulerInfoConfig
     * @return List<TaskNode>
     */
    public List<TaskNodeDTO> getTaskNodes(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO,
                                          ResourceDTO mySqlScriptResource,
                                          DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig) {
        List<TaskNodeDTO> tasks = new ArrayList<>();
        List<DqcSchedulerRulesVO> rulesVOList = dqcSchedulerBasicInfoVO.getDqcSchedulerRulesVOList();
        AtomicInteger index = new AtomicInteger();

        for (DqcSchedulerRulesVO rulesVO : rulesVOList) {
            tasks.add(
                    setTaskNodeInfo(dqcSchedulerBasicInfoVO,
                            rulesVO,
                            mySqlScriptResource,
                            index.get(),
                            dolphinSchedulerInfoConfig));
            index.incrementAndGet();
        }
        return tasks;
    }

    private TaskNodeDTO setTaskNodeInfo(DqcSchedulerBasicInfoVO basicInfoVO,
                                        DqcSchedulerRulesVO rulesVO,
                                        ResourceDTO mySqlScriptResource,
                                        int index,
                                        DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig) {
        TaskNodeDTO taskNode = null;
        try {
            taskNode = new TaskNodeDTO();
            setTaskNodeBasicInfo(
                    rulesVO,
                    taskNode,
                    index,
                    dolphinSchedulerInfoConfig);

            setTaskNodeShellParameters(
                    basicInfoVO,
                    rulesVO,
                    taskNode,
                    mySqlScriptResource,
                    dolphinSchedulerInfoConfig);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return taskNode;
    }

    private void setTaskNodeBasicInfo(DqcSchedulerRulesVO rulesVO,
                                      TaskNodeDTO taskNode,
                                      int index,
                                      DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig) {
        taskNode.setId(dolphinSchedulerInfoConfig.getTasksNameMatch() + index);
        taskNode.setName(rulesVO.getRuleName());
        taskNode.setTimeout(setTimeOutDTO(dolphinSchedulerInfoConfig));
        taskNode.setRunFlag(dolphinSchedulerInfoConfig.getTaskRunFlag());
        taskNode.setConditionResult(setConditionResultDTO(dolphinSchedulerInfoConfig));
        taskNode.setDependence(new HashMap<>(16));
        taskNode.setMaxRetryTimes(dolphinSchedulerInfoConfig.getTaskMaxRetryTimes());
        taskNode.setRetryInterval(dolphinSchedulerInfoConfig.getTaskRetryInterval());
        taskNode.setTaskInstancePriority(Priority.MEDIUM);
        taskNode.setWorkerGroup(dolphinSchedulerInfoConfig.getTaskWorkerGroup());
        taskNode.setPreTasks(new ArrayList<>());
        taskNode.setDescription(SchedulerConstant.NULL_VALUE);
    }

    private ConditionResultDTO setConditionResultDTO(DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig) {
        ConditionResultDTO conditionResultDTO = new ConditionResultDTO();
        List<String> arrayList = new ArrayList<>();
        arrayList.add(SchedulerConstant.NULL_VALUE);
        conditionResultDTO.setSuccessNode(arrayList);
        conditionResultDTO.setFailedNode(arrayList);
        return conditionResultDTO;
    }

    private TimeOutDTO setTimeOutDTO(DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig) {
        TimeOutDTO timeOutDTO = new TimeOutDTO();
        timeOutDTO.setStrategy(SchedulerConstant.NULL_VALUE);
        timeOutDTO.setEnable(false);
        return timeOutDTO;
    }

    private void setTaskNodeShellParameters(DqcSchedulerBasicInfoVO basicInfoVO,
                                            DqcSchedulerRulesVO rulesVO,
                                            TaskNodeDTO taskNode,
                                            ResourceDTO mySqlScriptResource,
                                            DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig) {
        taskNode.setType(dolphinSchedulerInfoConfig.getTypeShell());
        List<ResourceDTO> resourceList = new ArrayList<>();
        resourceList.add(mySqlScriptResource);

        ShellParameters.ShellParametersBuilder shellParametersBuilder =
                ShellParameters.builder()
                        .resourceList(resourceList)
                        .localParams(new ArrayList<>())
                        .rawScript(setMysqlRuleScript(basicInfoVO, rulesVO, mySqlScriptResource, dolphinSchedulerInfoConfig));

        taskNode.setParams(shellParametersBuilder.build());
    }

    private String setMysqlRuleScript(DqcSchedulerBasicInfoVO basicInfoVO,
                                      DqcSchedulerRulesVO rulesVO,
                                      ResourceDTO mySqlScriptResource,
                                      DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig) {
        MysqlRawScript.MysqlRawScriptBuilder scriptBuilder = MysqlRawScript.builder();

        //TODO 数据源连接信息规则调度里设置
        scriptBuilder
                .from_host("172.20.0.24")
                .from_user("root")
                .from_password("Zhudao123!")
                .from_database("qkdam");

        scriptBuilder.search_sql(rulesVO.getExecuteSql());

        scriptBuilder
                .to_host("172.20.0.24")
                .to_user("root")
                .to_password("Zhudao123!")
                .to_database("qkdam");

        scriptBuilder
                .job_id(basicInfoVO.getJobId())
                .job_name(basicInfoVO.getJobName())
                .rule_id(rulesVO.getRuleId())
                .rule_name(rulesVO.getRuleName())
                .rule_temp_id(rulesVO.getRuleTempId());

        MysqlRawScript mysqlRawScript = scriptBuilder.build();
        String mysqlRawScriptJson = GsonUtil.toJsonString(mysqlRawScript);

        return dolphinSchedulerInfoConfig.getPython3ExecuteCommand() + SchedulerConstant.SPACE_PLACEHOLDER
                + mySqlScriptResource.getRes()
                + SchedulerConstant.SPACE_PLACEHOLDER + SchedulerConstant.SINGLE_QUOTE
                + mysqlRawScriptJson
                + SchedulerConstant.SINGLE_QUOTE;
    }
}
