package com.qk.dm.dataquality.dolphinapi.builder;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.datasource.entity.ConnectBasicInfo;
import com.qk.dm.dataquality.constant.EngineTypeEnum;
import com.qk.dm.dataquality.dolphinapi.config.DolphinSchedulerInfoConfig;
import com.qk.dm.dataquality.dolphinapi.constant.Flag;
import com.qk.dm.dataquality.dolphinapi.constant.Priority;
import com.qk.dm.dataquality.dolphinapi.constant.SchedulerConstant;
import com.qk.dm.dataquality.dolphinapi.constant.TimeoutFlag;
import com.qk.dm.dataquality.dolphinapi.dto.*;
import com.qk.dm.dataquality.vo.DqcSchedulerBasicInfoVO;
import com.qk.dm.dataquality.vo.DqcSchedulerRulesVO;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private final static Logger LOG = LoggerFactory.getLogger(TaskDefinitionBuilder.class);
    private final List<TaskDefinitionDTO> taskDefinitionList = new ArrayList<>();


    public TaskDefinitionBuilder info(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO,
                                      ResourceDTO mySqlScriptResource,
                                      DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig,
                                      Map<String, ConnectBasicInfo> dataSourceInfo,
                                      Integer version) {
        //工作流定义节点信息
        taskNodes(dqcSchedulerBasicInfoVO, mySqlScriptResource, dolphinSchedulerInfoConfig, dataSourceInfo, version);
        return this;
    }


    /**
     * 构建任务实例执行节点信息集合
     *
     * @param dqcSchedulerBasicInfoVO
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

        try {
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
        } catch (Exception e) {
            LOG.info("JobId:【{}】,构建TaskDefinition对象失败!!!", dqcSchedulerBasicInfoVO.getJobId());
            e.printStackTrace();
            throw new BizException("构建TaskDefinition对象失败!!!");
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
        TaskDefinitionDTO taskNode = new TaskDefinitionDTO();
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
        return taskNode;
    }

    /**
     * 设置节点基础信息
     *
     * @param rulesVO
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
                                setRuleScript(basicInfoVO, rulesVO, mySqlScriptResource, dolphinSchedulerInfoConfig, dataSourceInfo))
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
     * 设置执行脚本信息(后期可做策略匹配)
     *
     * @param basicInfoVO
     * @param rulesVO
     * @param mySqlScriptResource
     * @param dolphinSchedulerInfoConfig
     * @param dataSourceInfo
     * @return String
     */
    private String setRuleScript(DqcSchedulerBasicInfoVO basicInfoVO,
                                 DqcSchedulerRulesVO rulesVO,
                                 ResourceDTO mySqlScriptResource,
                                 DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig,
                                 Map<String, ConnectBasicInfo> dataSourceInfo) {
        String ruleScript = null;
        if (EngineTypeEnum.MYSQL.getCode().equalsIgnoreCase(rulesVO.getEngineType())) {
            ruleScript = getMysqlRuleScript(basicInfoVO, rulesVO, mySqlScriptResource, dolphinSchedulerInfoConfig, dataSourceInfo);
        } else if ((EngineTypeEnum.HIVE.getCode().equalsIgnoreCase(rulesVO.getEngineType()))) {
            ruleScript = getHiveRuleScript(basicInfoVO, rulesVO, mySqlScriptResource, dolphinSchedulerInfoConfig, dataSourceInfo);
        } else if ((EngineTypeEnum.ELASTICSEARCH.getCode().equalsIgnoreCase(rulesVO.getEngineType()))) {
            ruleScript = getESRuleScript(basicInfoVO, rulesVO, mySqlScriptResource, dolphinSchedulerInfoConfig, dataSourceInfo);
        }

        return ruleScript;
    }

    /**
     * mysql设置执行脚本信息(后期可做策略匹配)
     *
     * @param basicInfoVO
     * @param rulesVO
     * @param mySqlScriptResource
     * @param dolphinSchedulerInfoConfig
     * @param dataSourceInfo
     * @return String
     */
    private String getMysqlRuleScript(DqcSchedulerBasicInfoVO basicInfoVO,
                                      DqcSchedulerRulesVO rulesVO,
                                      ResourceDTO mySqlScriptResource,
                                      DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig,
                                      Map<String, ConnectBasicInfo> dataSourceInfo) {
        MysqlRawScript.MysqlRawScriptBuilder scriptBuilder = MysqlRawScript.builder();

        //数据源连接信息规则调度里设置
        ConnectBasicInfo connectBasicInfo = dataSourceInfo.get(rulesVO.getDataSourceName());

        //来源数据源
        scriptBuilder
                .from_host(connectBasicInfo.getServer())
                .from_port(Integer.valueOf(connectBasicInfo.getPort()))
                .from_user(connectBasicInfo.getUserName())
                .from_password(connectBasicInfo.getPassword())
                .from_database(rulesVO.getDatabaseName());

//        scriptBuilder.search_sql(rulesVO.getExecuteSql());

        //目标数据源
        scriptBuilder
                .to_host(dolphinSchedulerInfoConfig.getResultDataDbHost())
                .to_port(dolphinSchedulerInfoConfig.getResultDataDbPort())
                .to_user(dolphinSchedulerInfoConfig.getResultDataDbUser())
                .to_password(dolphinSchedulerInfoConfig.getResultDataDbPassword())
                .to_database(dolphinSchedulerInfoConfig.getResultDataDbDatabase());

        //基础参数信息
        scriptBuilder
                .job_id(basicInfoVO.getJobId())
                .job_name(basicInfoVO.getJobName())
                .rule_id(rulesVO.getRuleId())
                .rule_name(rulesVO.getRuleName())
                .rule_temp_id(rulesVO.getRuleTempId())
                .task_code(rulesVO.getTaskCode())
                .rule_meta_data(getRuleMetaData(rulesVO));

        //动态实时sql请求地址
        scriptBuilder.sql_rpc_url(getSqlRpcUrl(rulesVO, dolphinSchedulerInfoConfig));

        //告警表达式结果获取url
        scriptBuilder.warn_rpc_url(getWarnRpcUrl(rulesVO, dolphinSchedulerInfoConfig));

        MysqlRawScript mysqlRawScript = scriptBuilder.build();
        String mysqlRawScriptJson = GsonUtil.toJsonString(mysqlRawScript);

        return dolphinSchedulerInfoConfig.getMysqlExecuteCommand() + SchedulerConstant.SPACE_PLACEHOLDER
                + mySqlScriptResource.getRes()
                + SchedulerConstant.SPACE_PLACEHOLDER + SchedulerConstant.SINGLE_QUOTE
                + mysqlRawScriptJson
                + SchedulerConstant.SINGLE_QUOTE;
    }

    /**
     * hive设置执行脚本信息(后期可做策略匹配)
     *
     * @param basicInfoVO
     * @param rulesVO
     * @param mySqlScriptResource
     * @param dolphinSchedulerInfoConfig
     * @param dataSourceInfo
     * @return String
     */
    private String getHiveRuleScript(DqcSchedulerBasicInfoVO basicInfoVO,
                                     DqcSchedulerRulesVO rulesVO,
                                     ResourceDTO mySqlScriptResource,
                                     DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig,
                                     Map<String, ConnectBasicInfo> dataSourceInfo) {
        HiveRawScript.HiveRawScriptBuilder scriptBuilder = HiveRawScript.builder();

        //数据源连接信息规则调度里设置
        ConnectBasicInfo connectBasicInfo = dataSourceInfo.get(rulesVO.getDataSourceName());

        //来源数据源
        scriptBuilder
                .from_host(connectBasicInfo.getHiveServer2())
                .from_port(Integer.valueOf(connectBasicInfo.getPort()))
                .from_user(connectBasicInfo.getUserName())
                .from_password(connectBasicInfo.getPassword())
                .from_database(rulesVO.getDatabaseName());

//        scriptBuilder.search_sql(rulesVO.getExecuteSql());

        //目标数据源
        scriptBuilder
                .to_host(dolphinSchedulerInfoConfig.getResultDataDbHost())
                .to_port(dolphinSchedulerInfoConfig.getResultDataDbPort())
                .to_user(dolphinSchedulerInfoConfig.getResultDataDbUser())
                .to_password(dolphinSchedulerInfoConfig.getResultDataDbPassword())
                .to_database(dolphinSchedulerInfoConfig.getResultDataDbDatabase());

        //基础参数信息
        scriptBuilder
                .job_id(basicInfoVO.getJobId())
                .job_name(basicInfoVO.getJobName())
                .rule_id(rulesVO.getRuleId())
                .rule_name(rulesVO.getRuleName())
                .rule_temp_id(rulesVO.getRuleTempId())
                .task_code(rulesVO.getTaskCode())
                .rule_meta_data(getRuleMetaData(rulesVO));

        //动态实时sql请求地址
        scriptBuilder.sql_rpc_url(getSqlRpcUrl(rulesVO, dolphinSchedulerInfoConfig));

        //告警表达式结果获取url
        scriptBuilder.warn_rpc_url(getWarnRpcUrl(rulesVO, dolphinSchedulerInfoConfig));

        HiveRawScript hiveRawScript = scriptBuilder.build();
        String hiveRawScriptJson = GsonUtil.toJsonString(hiveRawScript);

        return dolphinSchedulerInfoConfig.getHiveExecuteCommand()
                + SchedulerConstant.SPACE_PLACEHOLDER + SchedulerConstant.SINGLE_QUOTE
                + hiveRawScriptJson
                + SchedulerConstant.SINGLE_QUOTE;
    }

    /**
     * es设置执行脚本信息(后期可做策略匹配)
     *
     * @param basicInfoVO
     * @param rulesVO
     * @param mySqlScriptResource
     * @param dolphinSchedulerInfoConfig
     * @param dataSourceInfo
     * @return String
     */
    private String getESRuleScript(DqcSchedulerBasicInfoVO basicInfoVO,
                                   DqcSchedulerRulesVO rulesVO,
                                   ResourceDTO mySqlScriptResource,
                                   DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig,
                                   Map<String, ConnectBasicInfo> dataSourceInfo) {
        ESRawScript.ESRawScriptBuilder scriptBuilder = ESRawScript.builder();

        //数据源连接信息规则调度里设置
        ConnectBasicInfo connectBasicInfo = dataSourceInfo.get(rulesVO.getDataSourceName());

        //来源数据源
        scriptBuilder
                .from_host(connectBasicInfo.getServer())
//                .from_port(Integer.valueOf(connectBasicInfo.getPort()))
                .from_user(connectBasicInfo.getUserName())
                .from_password(connectBasicInfo.getPassword())
                .from_database(rulesVO.getDatabaseName());

//        scriptBuilder.search_sql(rulesVO.getExecuteSql());

        //目标数据源
        scriptBuilder
                .to_host(dolphinSchedulerInfoConfig.getResultDataDbHost())
                .to_port(dolphinSchedulerInfoConfig.getResultDataDbPort())
                .to_user(dolphinSchedulerInfoConfig.getResultDataDbUser())
                .to_password(dolphinSchedulerInfoConfig.getResultDataDbPassword())
                .to_database(dolphinSchedulerInfoConfig.getResultDataDbDatabase());

        //基础参数信息
        scriptBuilder
                .job_id(basicInfoVO.getJobId())
                .job_name(basicInfoVO.getJobName())
                .rule_id(rulesVO.getRuleId())
                .rule_name(rulesVO.getRuleName())
                .rule_temp_id(rulesVO.getRuleTempId())
                .task_code(rulesVO.getTaskCode())
//                .rule_meta_data(getRuleMetaData(rulesVO.getFieldList()))
        ;

        //动态实时sql请求地址
        scriptBuilder.sql_rpc_url(getSqlRpcUrl(rulesVO, dolphinSchedulerInfoConfig));

        //告警表达式结果获取url
        scriptBuilder.warn_rpc_url(getWarnRpcUrl(rulesVO, dolphinSchedulerInfoConfig));

        ESRawScript esRawScript = scriptBuilder.build();
        String esRawScriptJson = GsonUtil.toJsonString(esRawScript);

        return dolphinSchedulerInfoConfig.getEsExecuteCommand()
                + SchedulerConstant.SPACE_PLACEHOLDER + SchedulerConstant.SINGLE_QUOTE
                + esRawScriptJson
                + SchedulerConstant.SINGLE_QUOTE;
    }

    /**
     * 动态实时sql请求地址
     */
    private String getSqlRpcUrl(DqcSchedulerRulesVO rulesVO, DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig) {
        String sqlRpcUrl = dolphinSchedulerInfoConfig.getSqlRpcUrl();
        sqlRpcUrl = sqlRpcUrl + SchedulerConstant.SQL_RPC_URL_PART + rulesVO.getRuleId();
        return sqlRpcUrl;
    }

    /**
     * 告警表达式结果获取url
     */
    private String getWarnRpcUrl(DqcSchedulerRulesVO rulesVO, DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig) {
        String warnRpcUrl = dolphinSchedulerInfoConfig.getWarnRpcUrl();
        warnRpcUrl = warnRpcUrl + SchedulerConstant.WARN_RPC_URL_PART + rulesVO.getRuleId();
        return warnRpcUrl;
    }

    private String getRuleMetaData(DqcSchedulerRulesVO rulesVO) {
        String ruleMetaDataStr = "";
        if (rulesVO.getFieldList() != null) {
             ruleMetaDataStr = String.join(",", rulesVO.getFieldList());
        }
        return ruleMetaDataStr;
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
