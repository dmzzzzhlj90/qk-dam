package com.qk.dm.dataservice.service.imp;

import com.google.common.collect.Maps;
import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.enums.DataTypeEnum;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dm.dataservice.constant.CreateTypeEnum;
import com.qk.dm.dataservice.entity.*;
import com.qk.dm.dataservice.repositories.DasApiBasicInfoRepository;
import com.qk.dm.dataservice.repositories.DasApiCreateConfigRepository;
import com.qk.dm.dataservice.repositories.DasApiCreateMybatisSqlScriptRepository;
import com.qk.dm.dataservice.repositories.DasApiCreateSqlScriptRepository;
import com.qk.dm.dataservice.service.DasApiCreateConfigService;
import com.qk.dm.dataservice.service.DasApiCreateSqlScriptService;
import com.qk.dm.dataservice.service.DasApiCreateSummaryService;
import com.qk.dm.dataservice.service.DasDataQueryInfoService;
import com.qk.dm.dataservice.vo.DasApiBasicInfoVO;
import com.qk.dm.dataservice.vo.DasApiCreateConfigVO;
import com.qk.dm.dataservice.vo.DasApiCreateSqlScriptVO;
import com.qk.dm.dataservice.vo.DebugApiParasVO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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
@RequiredArgsConstructor
public class DasApiCreateSummaryServiceImpl implements DasApiCreateSummaryService {

    private static final QDasApiBasicInfo qDasApiBasicInfo = QDasApiBasicInfo.dasApiBasicInfo;
    private static final QDasApiCreateConfig qDasApiCreateConfig = QDasApiCreateConfig.dasApiCreateConfig;
    private static final QDasApiCreateSqlScript qDasApiCreateSqlScript = QDasApiCreateSqlScript.dasApiCreateSqlScript;
    private static final QDasApiCreateMybatisSqlScript qDasApiCreateMybatisSqlScript = QDasApiCreateMybatisSqlScript.dasApiCreateMybatisSqlScript;

    private final DasApiCreateConfigService dasApiCreateConfigService;
    private final DasApiCreateSqlScriptService dasApiCreateSqlScriptService;
    private final DasDataQueryInfoService dasDataQueryInfoService;

    private final DasApiBasicInfoRepository dasApiBasicinfoRepository;
    private final DasApiCreateConfigRepository dasApiCreateConfigRepository;
    private final DasApiCreateSqlScriptRepository dasApiCreateSqlScriptRepository;
    private final DasApiCreateMybatisSqlScriptRepository dasApiCreateMybatisSqlScriptRepository;


    @Override
    public Object detail(String apiId) {
        Object detailInfo;
        // 获取API基础信息
        Optional<DasApiBasicInfo> onDasApiBasicInfo =
                dasApiBasicinfoRepository.findOne(qDasApiBasicInfo.apiId.eq(apiId));
        if (onDasApiBasicInfo.isEmpty()) {
            throw new BizException("查询不到对应的API基础信息!!!");
        }
        DasApiBasicInfo dasApiBasicInfo = onDasApiBasicInfo.get();

        // 获取新建API配置信息
        Optional<DasApiCreateConfig> onCreateConfig =
                dasApiCreateConfigRepository.findOne(qDasApiCreateConfig.apiId.eq(apiId));
        if (onCreateConfig.isPresent()) {
            return dasApiCreateConfigService.detail(dasApiBasicInfo, onCreateConfig.get());
        }
        // 获取新建API SQL脚本方式信息
        Optional<DasApiCreateSqlScript> onCreateSqlScript =
                dasApiCreateSqlScriptRepository.findOne(qDasApiCreateSqlScript.apiId.eq(apiId));
        if (onCreateSqlScript.isPresent()) {
            return dasApiCreateSqlScriptService.detail(dasApiBasicInfo, onCreateSqlScript.get());
        }
        // 获取新建API MYBATIS高级SQL方式信息
        Optional<DasApiCreateMybatisSqlScript> onCreateMybatisSqlScript =
                dasApiCreateMybatisSqlScriptRepository.findOne(qDasApiCreateMybatisSqlScript.apiId.eq(apiId));
        return onCreateMybatisSqlScript.map(apiCreateMybatisSqlScript ->
                dasDataQueryInfoService.detail(dasApiBasicInfo, apiCreateMybatisSqlScript))
                .orElse(null);
    }

    @Override
    public Object remoteSearchData(String apiId, String paramData) {
        Object resData = null;
        //校验转换调试参数
        List<DebugApiParasVO> debugApiParasVO = useDebugApiParasVO(apiId, paramData);
        // 查询新建API
        Map<String, Object> createApiMap = searchCreateApi(apiId);

        Set<String> createTypeSet = createApiMap.keySet();
        if (createTypeSet.contains(CreateTypeEnum.CREATE_API_CONFIG_TYPE.getCode())) {
            //配置API
            DasApiCreateConfigVO apiCreateConfigVO = (DasApiCreateConfigVO) createApiMap.get(CreateTypeEnum.CREATE_API_CONFIG_TYPE.getCode());
            //设置请求参数 DEBUG调试参数
            apiCreateConfigVO.setDebugApiParasVOS(debugApiParasVO);
            //调用数据查询
            resData = dasApiCreateConfigService.debugModel(apiCreateConfigVO);
        } else if (createTypeSet.contains(CreateTypeEnum.CREATE_API_SQL_SCRIPT_TYPE.getCode())) {
            // 取数方式
            DasApiCreateSqlScriptVO apiCreateSqlScriptVO = (DasApiCreateSqlScriptVO) createApiMap.get(CreateTypeEnum.CREATE_API_SQL_SCRIPT_TYPE.getCode());
            //设置请求参数 DEBUG调试参数
            apiCreateSqlScriptVO.setDebugApiParasVOS(debugApiParasVO);
            //调用数据查询
            resData = dasApiCreateSqlScriptService.debugModel(apiCreateSqlScriptVO);
        }
        return resData;
    }

    /**
     * 校验转换调试参数
     *
     * @param apiId
     * @param paramData
     * @return
     */
    private List<DebugApiParasVO> useDebugApiParasVO(String apiId, String paramData) {
        List<DebugApiParasVO> debugApiParasVOList = Lists.newArrayList();
        if (ObjectUtils.isEmpty(apiId)) {
            throw new BizException("请求的apiID参数为空!!!");
        }

        if (ObjectUtils.isEmpty(paramData)) {
            throw new BizException("请求的paramDataD参数为空!!!");
        }

        //转换调试参数
        Map<String, String> params = GsonUtil.fromJsonString(paramData, new TypeToken<Map<String, String>>() {
        }.getType());

        params.keySet().forEach(k -> {
            DebugApiParasVO debugApiParasVO = DebugApiParasVO.builder()
                    .paraName(k)
                    .value(params.get(k))
                    .necessary(true)
                    .paraType(DataTypeEnum.STRING.getCode())
                    .build();
            debugApiParasVOList.add(debugApiParasVO);
        });
        return debugApiParasVOList;
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
