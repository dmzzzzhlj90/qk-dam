package com.qk.dm.datacollect.service.impl;

import com.dolphinscheduler.shell.client.DolphinShellClient;
import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.datasource.entity.ResultDatasourceInfo;
import com.qk.dam.metadata.catacollect.pojo.MetadataConnectInfoVo;
import com.qk.dm.client.DataBaseInfoDefaultApi;
import com.qk.dm.datacollect.mapstruct.DctDataBaseMapper;
import com.qk.dm.datacollect.service.DolphinProcessDefinitionService;
import com.qk.dm.datacollect.service.cron.CronService;
import com.qk.dm.datacollect.vo.DctSchedulerBasicInfoVO;
import com.qk.dm.datacollect.vo.ShellParamsVO;
import com.qk.dm.dolphin.common.dto.ProcessDefinitionDTO;
import com.qk.dm.dolphin.common.manager.DolphinProcessManager;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

/**
 * @author shenpj
 * @date 2022/4/28 15:50
 * @since 1.0.0
 */
@Service
public class DolphinShellServiceImpl implements DolphinProcessDefinitionService {
    private final DolphinShellClient dolphinShellClient;
    private final Map<String, CronService> cronServiceMap;
    private final DolphinProcessManager dolphinProcessManager;
    private final DataBaseInfoDefaultApi dataBaseInfoDefaultApi;

    /**
     * 空值占位符
     */
    public static final String SPACE_PLACEHOLDER = " ";
    /**
     * 空值占位符
     */
    public static final String SINGLE_QUOTE = "'";

    public DolphinShellServiceImpl(DolphinShellClient dolphinShellClient,
                                   Map<String, CronService> cronServiceMap,
                                   DolphinProcessManager dolphinProcessManager, DataBaseInfoDefaultApi dataBaseInfoDefaultApi) {
        this.dolphinShellClient = dolphinShellClient;
        this.cronServiceMap = cronServiceMap;
        this.dolphinProcessManager = dolphinProcessManager;
        this.dataBaseInfoDefaultApi = dataBaseInfoDefaultApi;
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
        //4、创建流程定义
        dolphinShellClient.createProcessDefinition(name, params, null, rawScript, description);
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
        //5、修改流程定义
        dolphinShellClient.updateProcessDefinition(dctSchedulerBasicInfoVO.getCode(), taskCode, name, params, null, rawScript, description);
    }



    private String createRawScript(DctSchedulerBasicInfoVO dctSchedulerBasicInfoVO) {
        ResultDatasourceInfo dsDatasourceVO = dataBaseInfoDefaultApi.getResultDataSourceById(dctSchedulerBasicInfoVO.getSchedulerRules().getDataSourceId());
        if (Objects.nonNull(dsDatasourceVO)) {
            MetadataConnectInfoVo metadataConnectInfoVo = GsonUtil.fromJsonString(
                    dsDatasourceVO.getConnectBasicInfoJson(), new TypeToken<MetadataConnectInfoVo>() {
                    }.getType());
            DctDataBaseMapper.INSTANCE.from(dctSchedulerBasicInfoVO.getSchedulerRules(), metadataConnectInfoVo);
            return SPACE_PLACEHOLDER+SINGLE_QUOTE+GsonUtil.toJsonString(metadataConnectInfoVo)+SINGLE_QUOTE;
        } else {
            throw new BizException("根据连接名称获取连接信息失败");
        }
    }
}
