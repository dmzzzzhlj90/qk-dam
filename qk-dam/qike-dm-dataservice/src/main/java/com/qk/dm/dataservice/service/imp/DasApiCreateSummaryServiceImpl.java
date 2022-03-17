package com.qk.dm.dataservice.service.imp;

import com.google.common.collect.Maps;
import com.qk.dam.commons.exception.BizException;
import com.qk.dm.dataservice.constant.CreateTypeEnum;
import com.qk.dm.dataservice.entity.*;
import com.qk.dm.dataservice.repositories.DasApiBasicInfoRepository;
import com.qk.dm.dataservice.repositories.DasApiCreateConfigRepository;
import com.qk.dm.dataservice.repositories.DasApiCreateSqlScriptRepository;
import com.qk.dm.dataservice.service.DasApiCreateConfigService;
import com.qk.dm.dataservice.service.DasApiCreateSqlScriptService;
import com.qk.dm.dataservice.service.DasApiCreateSummaryService;
import com.qk.dm.dataservice.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * 数据服务_新建API公共接口
 *
 * @author wjq
 * @date 2022/03/08
 * @since 1.0.0
 */
@Service
public class DasApiCreateSummaryServiceImpl implements DasApiCreateSummaryService {

    private static final QDasApiBasicInfo qDasApiBasicInfo = QDasApiBasicInfo.dasApiBasicInfo;
    private static final QDasApiCreateConfig qDasApiCreateConfig = QDasApiCreateConfig.dasApiCreateConfig;
    private static final QDasApiCreateSqlScript qDasApiCreateSqlScript = QDasApiCreateSqlScript.dasApiCreateSqlScript;

    private final DasApiCreateConfigService dasApiCreateConfigService;
    private final DasApiCreateSqlScriptService dasApiCreateSqlScriptService;

    private final DasApiBasicInfoRepository dasApiBasicinfoRepository;
    private final DasApiCreateConfigRepository dasApiCreateConfigRepository;
    private final DasApiCreateSqlScriptRepository dasApiCreateSqlScriptRepository;

    @Autowired
    public DasApiCreateSummaryServiceImpl(DasApiCreateConfigService dasApiCreateConfigService,
                                          DasApiCreateSqlScriptService dasApiCreateSqlScriptService,
                                          DasApiBasicInfoRepository dasApiBasicinfoRepository,
                                          DasApiCreateConfigRepository dasApiCreateConfigRepository,
                                          DasApiCreateSqlScriptRepository dasApiCreateSqlScriptRepository) {
        this.dasApiCreateConfigService = dasApiCreateConfigService;
        this.dasApiCreateSqlScriptService = dasApiCreateSqlScriptService;
        this.dasApiBasicinfoRepository = dasApiBasicinfoRepository;
        this.dasApiCreateConfigRepository = dasApiCreateConfigRepository;
        this.dasApiCreateSqlScriptRepository = dasApiCreateSqlScriptRepository;
    }

    @Override
    public Object detail(String apiId) {
        Object detailInfo = null;
        // 获取API基础信息
        Optional<DasApiBasicInfo> onDasApiBasicInfo = dasApiBasicinfoRepository.findOne(qDasApiBasicInfo.apiId.eq(apiId));
        if (onDasApiBasicInfo.isEmpty()) {
            throw new BizException("查询不到对应的API基础信息!!!");
        }

        DasApiBasicInfo dasApiBasicInfo = onDasApiBasicInfo.get();
        // 获取新建API配置信息
        Optional<DasApiCreateConfig> onDasApiCreateConfig = dasApiCreateConfigRepository.findOne(qDasApiCreateConfig.apiId.eq(apiId));
        if (onDasApiCreateConfig.isPresent()) {
            detailInfo = dasApiCreateConfigService.detail(dasApiBasicInfo, onDasApiCreateConfig.get());
        } else {
            // 获取新建API SQL脚本方式信息
            Optional<DasApiCreateSqlScript> onDasApiCreateSqlScript = dasApiCreateSqlScriptRepository.findOne(qDasApiCreateSqlScript.apiId.eq(apiId));
            if (onDasApiCreateSqlScript.isEmpty()) {
                //入参定义
                DasApiBasicInfoVO dasApiBasicInfoVO = dasApiCreateSqlScriptService.setDasApiBasicInfoDelInputParam(dasApiBasicInfo);
                return DasApiCreateSqlScriptVO.builder().apiBasicInfoVO(dasApiBasicInfoVO).build();
            }
            detailInfo = dasApiCreateSqlScriptService.detail(dasApiBasicInfo, onDasApiCreateSqlScript.get());
        }

        return detailInfo;
    }

    @Override
    public DebugApiResultVO remoteSearchData(String apiId, List<DebugApiParasVO> debugApiParasVO) {
        DebugApiResultVO debugApiResultVO = null;
        // 查询新建API
        Map<String, Object> createApiMap = searchCreateApi(apiId);

        Set<String> createTypeSet = createApiMap.keySet();
        if (createTypeSet.contains(CreateTypeEnum.CREATE_API_CONFIG_TYPE.getCode())) {
            //配置API
            DasApiCreateConfigVO apiCreateConfigVO = (DasApiCreateConfigVO) createApiMap.get(CreateTypeEnum.CREATE_API_CONFIG_TYPE.getCode());
            //设置请求参数 DEBUG调试参数
            apiCreateConfigVO.setDebugApiParasVOS(debugApiParasVO);
            //调用数据查询
            debugApiResultVO = dasApiCreateConfigService.debugModel(apiCreateConfigVO);
        } else if (createTypeSet.contains(CreateTypeEnum.CREATE_API_SQL_SCRIPT_TYPE.getCode())) {
            //配置API
            DasApiCreateSqlScriptVO apiCreateSqlScriptVO = (DasApiCreateSqlScriptVO) createApiMap.get(CreateTypeEnum.CREATE_API_SQL_SCRIPT_TYPE.getCode());
            //设置请求参数 DEBUG调试参数
            apiCreateSqlScriptVO.setDebugApiParasVOS(debugApiParasVO);
            //调用数据查询
            debugApiResultVO = dasApiCreateSqlScriptService.debugModel(apiCreateSqlScriptVO);
        }
        return debugApiResultVO;
    }

    /**
     * 查询新建API
     *
     * @param apiId
     * @return
     */
    public Map<String, Object> searchCreateApi(String apiId) {
        Map<String, Object> createApiMap = Maps.newHashMap();
        // 获取API基础信息
        Optional<DasApiBasicInfo> onDasApiBasicInfo = dasApiBasicinfoRepository.findOne(qDasApiBasicInfo.apiId.eq(apiId));
        if (onDasApiBasicInfo.isEmpty()) {
            throw new BizException("查询不到对应的API基础信息!!!");
        }

        DasApiBasicInfo dasApiBasicInfo = onDasApiBasicInfo.get();
        // 获取新建API配置信息
        Optional<DasApiCreateConfig> onDasApiCreateConfig = dasApiCreateConfigRepository.findOne(qDasApiCreateConfig.apiId.eq(apiId));
        if (onDasApiCreateConfig.isPresent()) {
            DasApiCreateConfigVO apiCreateConfigVO = dasApiCreateConfigService.detail(dasApiBasicInfo, onDasApiCreateConfig.get());
            createApiMap.put(CreateTypeEnum.CREATE_API_CONFIG_TYPE.getCode(), apiCreateConfigVO);
        } else {
            // 获取新建API SQL脚本方式信息
            Optional<DasApiCreateSqlScript> onDasApiCreateSqlScript = dasApiCreateSqlScriptRepository.findOne(qDasApiCreateSqlScript.apiId.eq(apiId));
            if (onDasApiCreateSqlScript.isEmpty()) {
                //入参定义
                DasApiBasicInfoVO dasApiBasicInfoVO = dasApiCreateSqlScriptService.setDasApiBasicInfoDelInputParam(dasApiBasicInfo);
                DasApiCreateSqlScriptVO apiCreateSqlScriptVO = DasApiCreateSqlScriptVO.builder().apiBasicInfoVO(dasApiBasicInfoVO).build();
                createApiMap.put(CreateTypeEnum.CREATE_API_SQL_SCRIPT_TYPE.getCode(), apiCreateSqlScriptVO);
                return createApiMap;
            }
            DasApiCreateSqlScriptVO apiCreateSqlScriptVO = dasApiCreateSqlScriptService.detail(dasApiBasicInfo, onDasApiCreateSqlScript.get());
            createApiMap.put(CreateTypeEnum.CREATE_API_SQL_SCRIPT_TYPE.getCode(), apiCreateSqlScriptVO);
        }
        return createApiMap;
    }

}