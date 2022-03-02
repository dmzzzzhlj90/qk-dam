package com.qk.dm.dataservice.service.imp;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.google.common.collect.Maps;
import com.qk.dm.dataservice.biz.ApiSixProcessService;
import com.qk.dm.dataservice.biz.CreateApiReleaseService;
import com.qk.dm.dataservice.biz.RegisterApiReleaseService;
import com.qk.dm.dataservice.constant.ApiTypeEnum;
import com.qk.dm.dataservice.constant.SyncStatusEnum;
import com.qk.dm.dataservice.entity.DasApiBasicInfo;
import com.qk.dm.dataservice.entity.QDasApiBasicInfo;
import com.qk.dm.dataservice.repositories.DasApiBasicInfoRepository;
import com.qk.dm.dataservice.service.DasReleaseApiService;
import com.qk.dm.dataservice.vo.DasReleaseApiParamsVO;
import com.qk.plugin.dataservice.apisix.consumer.ApiSixConsumerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;

/**
 * API发布_同步数据服务API至服务网关
 *
 * @author wjq
 * @date 20210819
 * @since 1.0.0
 */
@Service
public class DasReleaseApiServiceImpl implements DasReleaseApiService {
    private static final Log LOG = LogFactory.get("API发布_同步数据服务API至服务网关");

    private static final QDasApiBasicInfo qDasApiBasicInfo = QDasApiBasicInfo.dasApiBasicInfo;

    private final DasApiBasicInfoRepository dasApiBasicInfoRepository;
    private final ApiSixProcessService apiSixProcessService;
    private final RegisterApiReleaseService registerApiReleaseService;
    private final CreateApiReleaseService createApiReleaseService;


    @Autowired
    public DasReleaseApiServiceImpl(DasApiBasicInfoRepository dasApiBasicInfoRepository,
                                    ApiSixProcessService apiSixProcessService,
                                    RegisterApiReleaseService registerApiReleaseService,
                                    CreateApiReleaseService createApiReleaseService) {
        this.dasApiBasicInfoRepository = dasApiBasicInfoRepository;
        this.apiSixProcessService = apiSixProcessService;
        this.registerApiReleaseService = registerApiReleaseService;
        this.createApiReleaseService = createApiReleaseService;
    }

    @Override
    public void syncApiSixRoutes(DasReleaseApiParamsVO dasReleaseApiParamsVO) {
        LOG.info("====================开始发布API!====================");
        //查询API基础信息
        Iterable<DasApiBasicInfo> apiBasicInfos = searchBasicInfos(dasReleaseApiParamsVO.getNearlyApiPath(), dasReleaseApiParamsVO.getApiIds());
        //注册API集合
        Map<String, DasApiBasicInfo> registerBasicApiMap = Maps.newHashMap();
        //新建API集合
        Map<String, DasApiBasicInfo> createBasicApiMap = Maps.newHashMap();
        splitRegisterAndCreate(apiBasicInfos, registerBasicApiMap, createBasicApiMap);

        //校验API_SIX上游和服务信息
        apiSixProcessService.checkUpstreamAndService();
        //注册API发布
        LOG.info("====================开始同步数据服务注册API至网关ApiSix!====================");
        //同步注册API
        registerApiReleaseService.syncRegisterApi(registerBasicApiMap, dasReleaseApiParamsVO);
        LOG.info("====================数据服务注册API增量同步已经完成!====================");

        //新建API发布
        LOG.info("====================开始同步数据服务新建API至网关ApiSix!====================");
        //新建注册API
        createApiReleaseService.syncCreateApi(createBasicApiMap, dasReleaseApiParamsVO);
        LOG.info("====================数据服务新建API增量同步已经完成!====================");
        LOG.info("====================已完成发布API!====================");
    }


    @Override
    public void createApiSixConsumerKeyAuthPlugin(ApiSixConsumerInfo apiSixConsumerInfo) {
        apiSixProcessService.createApiSixConsumerKeyAuthPlugin(apiSixConsumerInfo);
    }

    @Override
    public List searchApiSixUpstreamInfo() {
        return apiSixProcessService.searchApiSixUpstreamInfo();
    }

    @Override
    public List searchApiSixServiceInfo() {
        return apiSixProcessService.searchApiSixServiceInfo();
    }

    @Override
    public void clearRouteInfo() {
        apiSixProcessService.clearRouteInfo();
    }

    /**
     * 查询API基础信息
     *
     * @param nearlyApiPath
     * @param apiIds
     * @return
     */
    private Iterable<DasApiBasicInfo> searchBasicInfos(String nearlyApiPath, List<String> apiIds) {
        Iterable<DasApiBasicInfo> apiBasicInfos = null;
        if (!ObjectUtils.isEmpty(apiIds)) {
            apiBasicInfos = dasApiBasicInfoRepository.findAll(qDasApiBasicInfo.apiId.in(apiIds)
                    .and(qDasApiBasicInfo.status.ne(SyncStatusEnum.CREATE_SUCCESS_UPLOAD.getCode())));
        } else {
            apiBasicInfos = dasApiBasicInfoRepository.findAll(qDasApiBasicInfo.apiPath.like(nearlyApiPath + "%"));
        }
        return apiBasicInfos;
    }

    /**
     * 分别获取注册API和新建API集合
     *
     * @param apiBasicInfos
     * @param registerBasicApiMap
     * @param createBasicApiMap
     */
    private void splitRegisterAndCreate(Iterable<DasApiBasicInfo> apiBasicInfos,
                                        Map<String, DasApiBasicInfo> registerBasicApiMap,
                                        Map<String, DasApiBasicInfo> createBasicApiMap) {
        apiBasicInfos.forEach(dasApiBasicInfo -> {
            if (dasApiBasicInfo.getApiType().equalsIgnoreCase(ApiTypeEnum.REGISTER_API.getCode())) {
                registerBasicApiMap.put(dasApiBasicInfo.getApiId(), dasApiBasicInfo);
            } else {
                createBasicApiMap.put(dasApiBasicInfo.getApiId(), dasApiBasicInfo);
            }
        });
    }


}
