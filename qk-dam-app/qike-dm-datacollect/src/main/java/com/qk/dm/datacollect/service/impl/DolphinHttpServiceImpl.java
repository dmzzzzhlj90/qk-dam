package com.qk.dm.datacollect.service.impl;

import com.dolphinscheduler.http.client.DolphinHttpClient;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dm.datacollect.service.DolphinHttpService;
import com.qk.dm.datacollect.service.cron.CronService;
import com.qk.dm.datacollect.vo.DctSchedulerBasicInfoVO;
import com.qk.dm.datacollect.vo.HttpParamsVO;
import com.qk.dm.dolphin.common.dto.ProcessDefinitionDTO;
import com.qk.dm.dolphin.common.service.DolphinProcessService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author shenpj
 * @date 2022/4/28 15:50
 * @since 1.0.0
 */
@Service
public class DolphinHttpServiceImpl implements DolphinHttpService {
    private final DolphinHttpClient dolphinHttpClient;
    private final Map<String, CronService> cronServiceMap;
    private final DolphinProcessService dolphinProcessService;

    public DolphinHttpServiceImpl(DolphinHttpClient dolphinHttpClient,
                                  Map<String, CronService> cronServiceMap,
                                  DolphinProcessService dolphinProcessService) {
        this.dolphinHttpClient = dolphinHttpClient;
        this.cronServiceMap = cronServiceMap;
        this.dolphinProcessService = dolphinProcessService;
    }

    @Override
    public void insert(DctSchedulerBasicInfoVO dctSchedulerBasicInfoVO) {
        String name = dctSchedulerBasicInfoVO.getDirId() + "_" + dctSchedulerBasicInfoVO.getName();
        String description = dctSchedulerBasicInfoVO.getDescription();
        //1、生成cron
        dctSchedulerBasicInfoVO.getSchedulerConfig().generateCron(dctSchedulerBasicInfoVO.getSchedulerConfig(), cronServiceMap);
        //2、生成参数
        Object httpParams = HttpParamsVO.createList(dctSchedulerBasicInfoVO);
        //3、创建流程定义
        dolphinHttpClient.createProcessDefinition(name, httpParams, description);
    }

    @Override
    public void update(DctSchedulerBasicInfoVO dctSchedulerBasicInfoVO) {
        String name = dctSchedulerBasicInfoVO.getDirId() + "_" + dctSchedulerBasicInfoVO.getName();
        String description = dctSchedulerBasicInfoVO.getDescription();
        //1、生成cron
        dctSchedulerBasicInfoVO.getSchedulerConfig().generateCron(dctSchedulerBasicInfoVO.getSchedulerConfig(), cronServiceMap);
        //2、生成参数
        Object httpParams = HttpParamsVO.createList(dctSchedulerBasicInfoVO);
        //3、根据详情获取taskCode
        ProcessDefinitionDTO processDefinitionDTO = dolphinProcessService.detailToProcess(dctSchedulerBasicInfoVO.getCode());
        long taskCode = GsonUtil.toJsonArray(processDefinitionDTO.getLocations()).get(0).getAsJsonObject().get("taskCode").getAsLong();
        //4、修改流程定义
        dolphinHttpClient.updateProcessDefinition(dctSchedulerBasicInfoVO.getCode(), taskCode, name, httpParams, description);
    }
}
