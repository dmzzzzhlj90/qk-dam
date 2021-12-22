package com.qk.dm.dataquality.dolphinapi.builder;

import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.datasource.entity.ConnectBasicInfo;
import com.qk.dm.dataquality.dolphinapi.config.DolphinSchedulerInfoConfig;
import com.qk.dm.dataquality.dolphinapi.constant.Flag;
import com.qk.dm.dataquality.dolphinapi.constant.Priority;
import com.qk.dm.dataquality.dolphinapi.constant.SchedulerConstant;
import com.qk.dm.dataquality.dolphinapi.constant.TimeoutFlag;
import com.qk.dm.dataquality.dolphinapi.dto.*;
import com.qk.dm.dataquality.utils.CodeGenerateUtils;
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
 * 构建TaskDefinition对象
 *
 * @author wjq
 * @date 2021/11/19
 * @since 1.0.0
 */
@Builder
@EqualsAndHashCode(callSuper = false)
@Data
public class TaskDefinitionBuilder {
    private final List<TaskDefinitionDTO> taskDefinitionList = new ArrayList<>();


    public TaskDefinitionBuilder info(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO,
                                      ResourceDTO mySqlScriptResource,
                                      DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig,
                                      Map<String, ConnectBasicInfo> dataSourceInfo,
                                      Integer version) {
        //工作流定义节点信息
        taskNodes(dqcSchedulerBasicInfoVO, mySqlScriptResource, dolphinSchedulerInfoConfig, dataSourceInfo,version);
        return this;
    }


    /**
     * 构建任务实例执行节点信息集合
     *  @param dqcSchedulerBasicInfoVO
     * @param mySqlScriptResource
     * @param dolphinSchedulerInfoConfig
     * @param dataSourceInfo
     * @param version
     */
    public void taskNodes(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO,
                          ResourceDTO mySqlScriptResource,
                          DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig,
                          Map<String, ConnectBasicInfo> dataSourceInfo, Integer version) {
        List<DqcSchedulerRulesVO> rulesVOList = dqcSchedulerBasicInfoVO.getDqcSchedulerRulesVOList();
        AtomicInteger index = new AtomicInteger();

        for (DqcSchedulerRulesVO rulesVO : rulesVOList) {
            taskDefinitionList.add(
                    //构建单节点信息
                    setTaskNodeInfo(
                            dqcSchedulerBasicInfoVO,
                            rulesVO,
                            mySqlScriptResource,
                            dolphinSchedulerInfoConfig,
                            dataSourceInfo,
                            version
                            ));
            index.incrementAndGet();
        }
    }

    /**
     * 构建单节点信息
     *
     * @param basicInfoVO
     * @param rulesVO
     * @param mySqlScriptResource
     * @param dolphinSchedulerInfoConfig
     * @param dataSourceInfo
     * @param version
     * @return taskDefinitionDTO
     */
    private TaskDefinitionDTO setTaskNodeInfo(DqcSchedulerBasicInfoVO basicInfoVO,
                                              DqcSchedulerRulesVO rulesVO,
                                              ResourceDTO mySqlScriptResource,
                                              DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig,
                                              Map<String, ConnectBasicInfo> dataSourceInfo, Integer version) {
        TaskDefinitionDTO taskNode = null;
        try {
            taskNode = new TaskDefinitionDTO();
            //设置节点基础信息
            setTaskNodeBasicInfo(
                    rulesVO,
                    taskNode,
                    dolphinSchedulerInfoConfig,
                    version);
            //设置节点参数信息(目前SHELL方式,后期可做匹配策略)
            setTaskNodeShellParameters(
                    basicInfoVO,
                    rulesVO,
                    taskNode,
                    mySqlScriptResource,
                    dolphinSchedulerInfoConfig,
                    dataSourceInfo);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return taskNode;
    }

    /**
     * 设置节点基础信息
     *  @param rulesVO
     * @param taskNode
     * @param dolphinSchedulerInfoConfig
     * @param version
     */
    private void setTaskNodeBasicInfo(DqcSchedulerRulesVO rulesVO,
                                      TaskDefinitionDTO taskNode,
                                      DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig,
                                      Integer version) {
        //将生成的taskCode关联到对应执行规则
        taskNode.setCode(rulesVO.getTaskCode());
        taskNode.setName(rulesVO.getRuleName());
        taskNode.setVersion(version);
        taskNode.setTaskType(dolphinSchedulerInfoConfig.getTypeShell());
        taskNode.setDescription(SchedulerConstant.NULL_VALUE);
        taskNode.setTaskType(dolphinSchedulerInfoConfig.getTypeShell());
        taskNode.setFlag(Flag.YES.getDesc());
        taskNode.setTaskPriority(Priority.MEDIUM.getDesc());
        taskNode.setWorkerGroup(dolphinSchedulerInfoConfig.getTaskWorkerGroup());
        taskNode.setFailRetryTimes(dolphinSchedulerInfoConfig.getFailRetryTimes());
        taskNode.setFailRetryInterval(dolphinSchedulerInfoConfig.getFailRetryInterval());
        taskNode.setTimeoutFlag(TimeoutFlag.CLOSE.getDesc());
        taskNode.setTimeoutNotifyStrategy(SchedulerConstant.NULL_VALUE);
        taskNode.setTimeout(SchedulerConstant.ZERO_VALUE);
        taskNode.setDelayTime(SchedulerConstant.ZERO_VALUE);
        taskNode.setEnvironmentCode(dolphinSchedulerInfoConfig.getEnvironmentCode());
    }

    /**
     * 设置节点参数信息(目前SHELL方式,后期可做匹配策略)
     *
     * @param basicInfoVO
     * @param rulesVO
     * @param taskNode
     * @param mySqlScriptResource
     * @param dolphinSchedulerInfoConfig
     * @param dataSourceInfo
     */
    private void setTaskNodeShellParameters(DqcSchedulerBasicInfoVO basicInfoVO,
                                            DqcSchedulerRulesVO rulesVO,
                                            TaskDefinitionDTO taskNode,
                                            ResourceDTO mySqlScriptResource,
                                            DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig,
                                            Map<String, ConnectBasicInfo> dataSourceInfo) {
        List<ResourceDTO> resourceList = new ArrayList<>();
        resourceList.add(mySqlScriptResource);

        TaskParamsDTO.TaskParamsDTOBuilder taskParamsDTOBuilder =
                TaskParamsDTO.builder()
                        .resourceList(resourceList)
                        .localParams(new ArrayList<>())
                        .rawScript(
                                setMysqlRuleScript(basicInfoVO, rulesVO, mySqlScriptResource, dolphinSchedulerInfoConfig, dataSourceInfo))
                        .dependence(new HashMap<>(16))
                        .conditionResult(
                                setConditionResult())
                        .waitStartTimeout(new HashMap<>(16))
                        .switchResult(new HashMap<>(16));

        taskNode.setTaskParams(taskParamsDTOBuilder.build());
    }

    private ConditionResultDTO setConditionResult() {
        ConditionResultDTO conditionResultDTO = new ConditionResultDTO();
        conditionResultDTO.setSuccessNode(new ArrayList<>());
        conditionResultDTO.setFailedNode(new ArrayList<>());
        return conditionResultDTO;
    }


    /**
     * 设置mysql执行脚本信息(后期可做策略匹配)
     *
     * @param basicInfoVO
     * @param rulesVO
     * @param mySqlScriptResource
     * @param dolphinSchedulerInfoConfig
     * @param dataSourceInfo
     * @return String
     */
    private String setMysqlRuleScript(DqcSchedulerBasicInfoVO basicInfoVO,
                                      DqcSchedulerRulesVO rulesVO,
                                      ResourceDTO mySqlScriptResource,
                                      DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig,
                                      Map<String, ConnectBasicInfo> dataSourceInfo) {
        MysqlRawScript.MysqlRawScriptBuilder scriptBuilder = MysqlRawScript.builder();

        //数据源连接信息规则调度里设置
        ConnectBasicInfo connectBasicInfo = dataSourceInfo.get(rulesVO.getDataSourceName());
        scriptBuilder
                .from_host(connectBasicInfo.getServer())
                .from_user(connectBasicInfo.getUserName())
                .from_password(connectBasicInfo.getPassword())
                .from_database(rulesVO.getDatabaseName());

        scriptBuilder.search_sql(rulesVO.getExecuteSql());

        scriptBuilder
                .to_host(dolphinSchedulerInfoConfig.getResultDataDbHost())
                .to_user(dolphinSchedulerInfoConfig.getResultDataDbUser())
                .to_password(dolphinSchedulerInfoConfig.getResultDataDbPassword())
                .to_database(dolphinSchedulerInfoConfig.getResultDataDbDatabase());

        scriptBuilder
                .job_id(basicInfoVO.getJobId())
                .job_name(basicInfoVO.getJobName())
                .rule_id(rulesVO.getRuleId())
                .rule_name(rulesVO.getRuleName())
                .rule_temp_id(rulesVO.getRuleTempId())
                .task_code(rulesVO.getTaskCode());

        MysqlRawScript mysqlRawScript = scriptBuilder.build();
        String mysqlRawScriptJson = GsonUtil.toJsonString(mysqlRawScript);

        return dolphinSchedulerInfoConfig.getPython3ExecuteCommand() + SchedulerConstant.SPACE_PLACEHOLDER
                + mySqlScriptResource.getRes()
                + SchedulerConstant.SPACE_PLACEHOLDER + SchedulerConstant.SINGLE_QUOTE
                + mysqlRawScriptJson
                + SchedulerConstant.SINGLE_QUOTE;
    }

    public List<TaskDefinitionDTO> getTaskDefinitions() {
        return taskDefinitionList;
    }

//
//    public TaskDefinitionBuilder taskNode(List<TaskDefinitionDTO> taskNodes) {
//        processData.setTasks(taskNodes);
//        return this;
//    }
//
//    public TaskDefinitionBuilder globalParams() {
//        List<PropertyDTO> globalParams = new ArrayList<>();
//        processData.setGlobalParams(globalParams);
//        return this;
//    }

    //    private  TimeOutDTO setTimeOutDTO(DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig) {
//        TimeOutDTO timeOutDTO = new TimeOutDTO();
//        timeOutDTO.setStrategy(SchedulerConstant.NULL_VALUE);
//        timeOutDTO.setEnable(false);
//        return timeOutDTO;
//    }

//    public TaskDefinitionBuilder timeout() {
//        //TODO 超时时间
//        processData.setTimeout(0);
//        return this;
//
//    }
//
//    public TaskDefinitionBuilder tenantId(TenantDTO tenantDTO) {
//        processData.setTenantId(tenantDTO.getId());
//        return this;
//    }
//

}
