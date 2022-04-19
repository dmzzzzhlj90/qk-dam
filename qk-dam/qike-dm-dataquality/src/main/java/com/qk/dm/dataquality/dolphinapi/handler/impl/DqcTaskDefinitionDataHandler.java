package com.qk.dm.dataquality.dolphinapi.handler.impl;

import com.qk.dam.datasource.entity.ConnectBasicInfo;
import com.qk.dm.dataquality.dolphinapi.builder.TaskDefinitionBuilder;
import com.qk.dm.dataquality.dolphinapi.config.DolphinSchedulerInfoConfig;
import com.qk.dm.dataquality.dolphinapi.dto.ResourceDTO;
import com.qk.dm.dataquality.dolphinapi.dto.TaskDefinitionDTO;
import com.qk.dm.dataquality.dolphinapi.handler.TaskDefinitionDataHandler;
import com.qk.dm.dataquality.vo.DqcSchedulerBasicInfoVO;

import java.util.List;
import java.util.Map;


/**
 * @author wjq
 * @date 2021/11/26
 * @since 1.0.0
 */
public class DqcTaskDefinitionDataHandler implements TaskDefinitionDataHandler<DqcSchedulerBasicInfoVO> {

    @Override
    public List<TaskDefinitionDTO> buildTaskDefinitionDataDTO(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO,
                                                              ResourceDTO mySqlScriptResource,
                                                              DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig,
                                                              Map<String, ConnectBasicInfo> dataSourceInfo,
                                                              Integer version) {

        return TaskDefinitionBuilder.builder()
                .build()
                .info(
                        dqcSchedulerBasicInfoVO,
                        mySqlScriptResource,
                        dolphinSchedulerInfoConfig,
                        dataSourceInfo,
                        version)
                .getTaskDefinitions();
    }

}
