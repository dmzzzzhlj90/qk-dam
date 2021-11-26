package com.qk.dm.dataquality.dolphinapi.executor;

import com.qk.dm.dataquality.dolphinapi.dto.ProcessDataDTO;
import com.qk.dm.dataquality.dolphinapi.dto.ResourceDTO;
import com.qk.dm.dataquality.dolphinapi.dto.TenantDTO;
import com.qk.dm.dataquality.dolphinapi.handler.ProcessDataHandler;
import com.qk.dm.dataquality.vo.DqcSchedulerBasicInfoVO;

/**
 * 流程实例构建执行器
 *
 * @author wjq
 * @date 2021/11/26
 * @since 1.0.0
 */
public class ProcessDataExecutor {


    private ProcessDataExecutor() {
        throw new IllegalStateException("Utility class");
    }

    public static ProcessDataDTO dqcProcessData(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO,
                                                ResourceDTO mySqlScriptResource,
                                                TenantDTO tenantDTO) {
        return null;

    }

}
