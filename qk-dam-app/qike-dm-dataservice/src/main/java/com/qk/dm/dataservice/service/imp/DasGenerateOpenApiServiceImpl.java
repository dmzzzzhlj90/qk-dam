package com.qk.dm.dataservice.service.imp;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.openapi.OpenapiBuilder;
import com.qk.dm.dataservice.biz.CreateConfigApiOpenApiBuilder;
import com.qk.dm.dataservice.biz.CreateSqlScriptApiOpenApiBuilder;
import com.qk.dm.dataservice.biz.RegisterApiOpenApiBuilder;
import com.qk.dm.dataservice.config.OpenApiConnectInfo;
import com.qk.dm.dataservice.constant.ApiTypeEnum;
import com.qk.dm.dataservice.constant.DasConstant;
import com.qk.dm.dataservice.entity.DasApiDir;
import com.qk.dm.dataservice.service.*;
import com.qk.dm.dataservice.vo.DasApiBasicInfoVO;
import com.qk.dm.dataservice.vo.DasApiCreateConfigDefinitionVO;
import com.qk.dm.dataservice.vo.DasApiCreateSqlScriptDefinitionVO;
import com.qk.dm.dataservice.vo.DasApiRegisterDefinitionVO;
import org.apache.commons.compress.utils.Lists;
import org.openapi4j.parser.model.SerializationFlag;
import org.openapi4j.parser.model.v3.OpenApi3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 根据数据服务API生成OpenApiJson
 *
 * @author wjq
 * @date 2021/8/30 17:49
 * @since 1.0.0
 */
@Service
public class DasGenerateOpenApiServiceImpl implements DasGenerateOpenApiService {
    private static final Log LOG = LogFactory.get("数据服务_根据数据服务API生成OpenApiJson操作");

    private final DasApiDirService dasApiDirService;
    private final DasApiBasicInfoService dasApiBasicInfoService;
    private final DasApiCreateConfigService dasApiCreateConfigService;
    private final DasApiCreateSqlScriptService dasApiCreateSqlScriptService;
    private final DasApiRegisterService dasApiRegisterService;

    private final CreateConfigApiOpenApiBuilder createConfigApiOpenApiBuilder;
    private final CreateSqlScriptApiOpenApiBuilder createSqlScriptApiOpenApiBuilder;
    private final RegisterApiOpenApiBuilder registerApiOpenApiBuilder;

    private final OpenApiConnectInfo openApiConnectInfo;

    @Autowired
    public DasGenerateOpenApiServiceImpl(DasApiDirService dasApiDirService,
                                         DasApiBasicInfoService dasApiBasicInfoService,
                                         DasApiCreateConfigService dasApiCreateConfigService,
                                         DasApiCreateSqlScriptService dasApiCreateSqlScriptService,
                                         DasApiRegisterService dasApiRegisterService,
                                         CreateConfigApiOpenApiBuilder createConfigApiOpenApiBuilder,
                                         CreateSqlScriptApiOpenApiBuilder createSqlScriptApiOpenApiBuilder,
                                         RegisterApiOpenApiBuilder registerApiOpenApiBuilder,
                                         OpenApiConnectInfo openApiConnectInfo) {
        this.dasApiDirService = dasApiDirService;
        this.dasApiBasicInfoService = dasApiBasicInfoService;
        this.dasApiCreateConfigService = dasApiCreateConfigService;
        this.dasApiCreateSqlScriptService = dasApiCreateSqlScriptService;
        this.dasApiRegisterService = dasApiRegisterService;
        this.createConfigApiOpenApiBuilder = createConfigApiOpenApiBuilder;
        this.createSqlScriptApiOpenApiBuilder = createSqlScriptApiOpenApiBuilder;
        this.registerApiOpenApiBuilder = registerApiOpenApiBuilder;
        this.openApiConnectInfo = openApiConnectInfo;
    }

    @Override
    public String generateOpenApi(String dirId) {
        //根据目录分类,进行不同API的测试接口生成
        LOG.info("开始执行生成OpenApiJson操作!");
        String openApi3Json = null;
        try {
            // 获取API分类目录信息
            DasApiDir apiDirInfo = getApiDirInfo(dirId);
            // 获取API基础信息
            List<DasApiBasicInfoVO> apiBasicInfoVOList = dasApiBasicInfoService.findAllByApiDirId(apiDirInfo.getDirId());
            LOG.info("获取到API信息个数为: 【{}】", apiBasicInfoVOList.size());
            if (!ObjectUtils.isEmpty(apiBasicInfoVOList)) {
                // 构建OpenApi3
                OpenapiBuilder openapiBuilder = getOpenApiBuilder("数据服务-" + apiDirInfo.getDirName());
                openApiBuilder(apiBasicInfoVOList, openapiBuilder);
                // 构建完成,获取OpenApi3
                OpenApi3 openApi3 = openapiBuilder.getOpenApi3();
                LOG.info("成功获取OpenApi3!");
                EnumSet<SerializationFlag> enumSet = EnumSet.of(SerializationFlag.OUT_AS_JSON);
                openApi3Json = openApi3.toString(enumSet);
                LOG.info("成功生成OpenApiJson!");
            } else {
                LOG.info("数据服务注册API信息为空!");
                throw new BizException("数据服务注册API信息为空!!!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("生成OpenApi失败!!!");
        }
        return openApi3Json;
    }

    /**
     * 获取API分类目录信息
     *
     * @param dirId
     * @return
     */
    private DasApiDir getApiDirInfo(String dirId) {
        DasApiDir apiDir = dasApiDirService.searchApiDirInfoByDirId(dirId);
        if (apiDir == null) {
            apiDir = new DasApiDir();
            apiDir.setDirId(DasConstant.TREE_DIR_TOP_PARENT_ID);
            apiDir.setDirName(DasConstant.TREE_DIR_TOP_PARENT_NAME);
        }
        return apiDir;
    }

    private void openApiBuilder(List<DasApiBasicInfoVO> apiBasicInfoVOList, OpenapiBuilder openapiBuilder) {
        if (null != apiBasicInfoVOList && apiBasicInfoVOList.size() > 0) {
            Map<String, List<DasApiBasicInfoVO>> apiTypeMap = apiBasicInfoVOList.stream().collect(Collectors.groupingBy(DasApiBasicInfoVO::getApiType));
            // 新建API
            List<DasApiBasicInfoVO> createApi = apiTypeMap.get(ApiTypeEnum.CREATE_API.getCode());
            Map<String, List<DasApiBasicInfoVO>> createApiMap = createApi.stream().collect(Collectors.groupingBy(DasApiBasicInfoVO::getApiId));
            // 注册API
            List<DasApiBasicInfoVO> registerApi = apiTypeMap.get(ApiTypeEnum.REGISTER_API.getCode());
            Map<String, List<DasApiBasicInfoVO>> registerApiMap = registerApi.stream().collect(Collectors.groupingBy(DasApiBasicInfoVO::getApiId));

            // 新建API配置信息
            List<String> createApiIds = Lists.newArrayList();
            createApiIds.addAll(createApiMap.keySet());
            List<DasApiCreateConfigDefinitionVO> createConfigDefinitions = dasApiCreateConfigService.searchCreateConfigByApiId(createApiIds);
            createConfigApiOpenApiBuilder.openApiBuilder(createConfigDefinitions, createApiMap, openapiBuilder);
            // 新建API脚本信息
            List<DasApiCreateSqlScriptDefinitionVO> createSqlScriptDefinitions = dasApiCreateSqlScriptService.searchCreateSqlScriptByApiId(createApiIds);
            createSqlScriptApiOpenApiBuilder.openApiBuilder(createSqlScriptDefinitions, createApiMap, openapiBuilder);
            // 注册API
            List<String> registerApiIds = Lists.newArrayList();
            registerApiIds.addAll(registerApiMap.keySet());
            List<DasApiRegisterDefinitionVO> registerDefinitions = dasApiRegisterService.searchRegisterByApiId(registerApiIds);
            registerApiOpenApiBuilder.openApiBuilder(registerDefinitions, registerApiMap, openapiBuilder);
        }
    }

    /**
     * 构建OpenApi3
     *
     * @param description
     * @return
     */
    private OpenapiBuilder getOpenApiBuilder(String description) {
        return OpenapiBuilder.builder().build()
                .context(openApiConnectInfo.getVersion(), openApiConnectInfo.getUrl())
                .server(openApiConnectInfo.getServerUrl())
                .info(DasConstant.DAM_DATA_SERVICE, description, openApiConnectInfo.getVersion());
    }


}
