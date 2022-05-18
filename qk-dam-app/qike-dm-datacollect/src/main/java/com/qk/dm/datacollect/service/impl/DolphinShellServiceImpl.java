package com.qk.dm.datacollect.service.impl;

import com.dolphinscheduler.shell.client.DolphinShellClient;
import com.google.gson.reflect.TypeToken;
import com.qk.dam.catacollect.vo.MetadataConnectInfoVo;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.datasource.entity.ResultDatasourceInfo;
import com.qk.dm.client.DataBaseInfoDefaultApi;
import com.qk.dm.datacollect.config.AtlasInfoConfig;
import com.qk.dm.datacollect.mapstruct.DctDataBaseMapper;
import com.qk.dm.datacollect.service.DolphinProcessDefinitionService;
import com.qk.dm.datacollect.service.cron.CronService;
import com.qk.dm.datacollect.vo.DctSchedulerBasicInfoVO;
import com.qk.dm.datacollect.vo.ShellParamsVO;
import com.qk.dm.dolphin.common.dto.ProcessDefinitionDTO;
import com.qk.dm.dolphin.common.manager.DolphinProcessManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

/**
 * @author shenpj
 * @date 2022/4/28 15:50
 * @since 1.0.0
 */
@Service
@Slf4j
public class DolphinShellServiceImpl implements DolphinProcessDefinitionService {
    /**
     * 空值占位符
     */
    public static final String SPACE_PLACEHOLDER = " ";
    /**
     * 空值占位符
     */
    public static final String SINGLE_QUOTE = "'";
    private final DolphinShellClient dolphinShellClient;
    private final Map<String, CronService> cronServiceMap;
    private final DolphinProcessManager dolphinProcessManager;
    private final DataBaseInfoDefaultApi dataBaseInfoDefaultApi;
    private final AtlasInfoConfig atlasInfoConfig;

    public DolphinShellServiceImpl(DolphinShellClient dolphinShellClient,
                                   Map<String, CronService> cronServiceMap,
                                   DolphinProcessManager dolphinProcessManager, DataBaseInfoDefaultApi dataBaseInfoDefaultApi, AtlasInfoConfig atlasInfoConfig) {
        this.dolphinShellClient = dolphinShellClient;
        this.cronServiceMap = cronServiceMap;
        this.dolphinProcessManager = dolphinProcessManager;
        this.dataBaseInfoDefaultApi = dataBaseInfoDefaultApi;
        this.atlasInfoConfig = atlasInfoConfig;
    }

    @Override
    public void insert(DctSchedulerBasicInfoVO dctSchedulerBasicInfoVO) {
        String name = dctSchedulerBasicInfoVO.getDirId() + "_" + dctSchedulerBasicInfoVO.getName();
        String description = dctSchedulerBasicInfoVO.getDescription();
        //1、生成cron
        dctSchedulerBasicInfoVO.getSchedulerConfig().generateCron(dctSchedulerBasicInfoVO.getSchedulerConfig(), cronServiceMap);
        //2、生成LocalParams参数
        Object params = ShellParamsVO.createList(dctSchedulerBasicInfoVO);
        //3、生成rawScript参数
        String rawScript = createRawScript(dctSchedulerBasicInfoVO);
        try {
            //4、创建流程定义
            dolphinShellClient.createProcessDefinition(name, params, rawScript, description);
        } catch (Exception e) {
            log.error("创建流程定义失败[{}]", e.getMessage());
            throw new BizException("创建流程定义失败");
        }
    }

    @Override
    public void update(DctSchedulerBasicInfoVO dctSchedulerBasicInfoVO) {
        String name = dctSchedulerBasicInfoVO.getDirId() + "_" + dctSchedulerBasicInfoVO.getName();
        String description = dctSchedulerBasicInfoVO.getDescription();
        //1、生成cron
        dctSchedulerBasicInfoVO.getSchedulerConfig().generateCron(dctSchedulerBasicInfoVO.getSchedulerConfig(), cronServiceMap);
        //2、生成LocalParams参数
        Object params = ShellParamsVO.createList(dctSchedulerBasicInfoVO);
        //3、生成rawScript参数
        String rawScript = createRawScript(dctSchedulerBasicInfoVO);
        //4、根据详情获取taskCode
        ProcessDefinitionDTO processDefinitionDTO = dolphinProcessManager.detailToProcess(dctSchedulerBasicInfoVO.getCode());
        long taskCode = GsonUtil.toJsonArray(processDefinitionDTO.getLocations()).get(0).getAsJsonObject().get("taskCode").getAsLong();
        try {
            //5、修改流程定义
            dolphinShellClient.updateProcessDefinition(dctSchedulerBasicInfoVO.getCode(), taskCode, name, params, rawScript, description, null);
        } catch (Exception e) {
            log.error("修改流程定义失败[{}]", e.getMessage());
            throw new BizException("修改流程定义失败");
        }
    }

    private String createRawScript(DctSchedulerBasicInfoVO dctSchedulerBasicInfoVO) {
        ResultDatasourceInfo dsDatasourceVO = dataBaseInfoDefaultApi.getResultDataSourceByCode(dctSchedulerBasicInfoVO.getSchedulerRules().getDataSourceId());
        if (Objects.nonNull(dsDatasourceVO)) {
            MetadataConnectInfoVo metadataConnectInfoVo = GsonUtil.fromJsonString(
                    dsDatasourceVO.getConnectBasicInfoJson(), new TypeToken<MetadataConnectInfoVo>() {
                    }.getType());
            DctDataBaseMapper.INSTANCE.schedulerRulesToConnect(dctSchedulerBasicInfoVO.getSchedulerRules(), metadataConnectInfoVo);
            metadataConnectInfoVo.setAuth(atlasInfoConfig.getBasicAuth());
            metadataConnectInfoVo.setAtalsServer(atlasInfoConfig.getAddress());
            return SPACE_PLACEHOLDER + SINGLE_QUOTE + GsonUtil.toJsonString(metadataConnectInfoVo) + SINGLE_QUOTE;
        } else {
            throw new BizException("根据连接名称获取连接信息失败");
        }
    }
}
