package com.qk.dm.dataquality.dolphinapi.executor;

import com.qk.dam.datasource.entity.ConnectBasicInfo;
import com.qk.dm.dataquality.dolphinapi.config.DolphinSchedulerInfoConfig;
import com.qk.dm.dataquality.dolphinapi.dto.TaskDefinitionDTO;
import com.qk.dm.dataquality.dolphinapi.dto.ResourceDTO;
import com.qk.dm.dataquality.dolphinapi.handler.impl.DqcTaskDefinitionDataHandler;
import com.qk.dm.dataquality.vo.DqcSchedulerBasicInfoVO;

import java.util.List;
import java.util.Map;

/**
 * 流程实例构建执行器
 *
 * @author wjq
 * @date 2021/11/26
 * @since 1.0.0
 */
public class TaskDefinitionExecutor {

    public TaskDefinitionExecutor() {
        throw new IllegalStateException("Utility class");
    }

    public static List<TaskDefinitionDTO> dqcTaskDefinitionData(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO,
                                                                ResourceDTO mySqlScriptResource,
                                                                DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig,
                                                                Map<String, ConnectBasicInfo> dataSourceInfo,
                                                                Integer version) {
        return new DqcTaskDefinitionDataHandler()
                .buildTaskDefinitionDataDTO(
                        dqcSchedulerBasicInfoVO,
                        mySqlScriptResource,
                        dolphinSchedulerInfoConfig,
                        dataSourceInfo,
                        version);
    }

}
