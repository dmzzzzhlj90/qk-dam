package com.qk.dm.dataquality.dolphinapi.handler;

import com.qk.dam.datasource.entity.ConnectBasicInfo;
import com.qk.dm.dataquality.dolphinapi.config.DolphinSchedulerInfoConfig;
import com.qk.dm.dataquality.dolphinapi.dto.ProcessDataDTO;
import com.qk.dm.dataquality.dolphinapi.dto.ResourceDTO;
import com.qk.dm.dataquality.dolphinapi.dto.TenantDTO;
import com.qk.dm.dataquality.vo.DqcSchedulerBasicInfoVO;

import java.util.Map;

/**
 * 流程实例构构建处理器
 *
 * @author wjq
 * @date 2021/11/26
 * @since 1.0.0
 */
public interface ProcessDataHandler<T> {

    ProcessDataDTO buildProcessDataDTO(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO,
                                       ResourceDTO mySqlScriptResource,
                                       TenantDTO tenantDTO,
                                       DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig,
                                       Map<String, ConnectBasicInfo> dataSourceInfo);

}
