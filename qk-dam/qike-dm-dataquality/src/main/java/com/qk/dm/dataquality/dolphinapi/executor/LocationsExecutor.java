package com.qk.dm.dataquality.dolphinapi.executor;

import com.qk.dm.dataquality.dolphinapi.config.DolphinSchedulerInfoConfig;
import com.qk.dm.dataquality.dolphinapi.dto.LocationsDTO;
import com.qk.dm.dataquality.dolphinapi.dto.ProcessDataDTO;
import com.qk.dm.dataquality.dolphinapi.dto.ResourceDTO;
import com.qk.dm.dataquality.dolphinapi.dto.TenantDTO;
import com.qk.dm.dataquality.dolphinapi.handler.impl.DqcLocationsHandler;
import com.qk.dm.dataquality.dolphinapi.handler.impl.DqcProcessDataHandler;
import com.qk.dm.dataquality.vo.DqcSchedulerBasicInfoVO;

/**
 * DAG流程图_位置信息_构建执行器
 *
 * @author wjq
 * @date 2021/11/26
 * @since 1.0.0
 */
public class LocationsExecutor {

    public LocationsExecutor() {
        throw new IllegalStateException("Utility class");
    }

    public static LocationsDTO dqcLocations(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO, DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig) {
        return new DqcLocationsHandler().buildLocationsDTO(dqcSchedulerBasicInfoVO,dolphinSchedulerInfoConfig);
    }

}
