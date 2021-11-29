package com.qk.dm.dataquality.dolphinapi.handler;

import com.qk.dm.dataquality.dolphinapi.config.DolphinSchedulerInfoConfig;
import com.qk.dm.dataquality.dolphinapi.dto.LocationsDTO;
import com.qk.dm.dataquality.dolphinapi.dto.ProcessDataDTO;
import com.qk.dm.dataquality.dolphinapi.dto.ResourceDTO;
import com.qk.dm.dataquality.dolphinapi.dto.TenantDTO;

/**
 * DAG流程图_位置信息_处理器
 *
 * @author wjq
 * @date 2021/11/26
 * @since 1.0.0
 */
public interface LocationsHandler<T> {

    LocationsDTO buildLocationsDTO(T t, DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig);

}
