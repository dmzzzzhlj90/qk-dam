package com.qk.dm.dataservice.service.imp;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.google.common.collect.Maps;
import com.qk.dm.dataservice.biz.ApiSixProcessService;
import com.qk.dm.dataservice.biz.CreateApiReleaseService;
import com.qk.dm.dataservice.biz.RegisterApiReleaseService;
import com.qk.dm.dataservice.constant.ApiSyncTypeEnum;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    @Transactional(rollbackFor = Exception.class)
    public void syncApiSixRoutes(DasReleaseApiParamsVO dasReleaseApiParamsVO) {
        LOG.info("====================开始发布API!====================");
        //查询API基础信息
        Iterable<DasApiBasicInfo> apiBasicInfos = searchBasicInfos(dasReleaseApiParamsVO);
        //所有apiId集合
        Set<String> apiIdSet = new LinkedHashSet<>();
        //注册API集合
        Map<String, DasApiBasicInfo> registerBasicApiMap = Maps.newHashMap();

        //新建API集合
        Map<String, DasApiBasicInfo> createBasicApiMap = Maps.newHashMap();
        splitRegisterAndCreate(apiBasicInfos, registerBasicApiMap, createBasicApiMap, apiIdSet);

        if (dasReleaseApiParamsVO.getApiSyncType().equalsIgnoreCase(ApiSyncTypeEnum.OFFLINE.getCode())) {
            //下线操作
            dasApiBasicInfoRepository.updateStatusByApiId(SyncStatusEnum.CREATE_NO_UPLOAD.getCode(), apiIdSet);
            LOG.info("====================下线操作成功!====================");
        } else {
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
    }


    @Override
    public void createApiSixConsumerKeyAuthPlugin(ApiSixConsumerInfo apiSixConsumerInfo) {
        apiSixProcessService.createApiSixConsumerKeyAuthPlugin(apiSixConsumerInfo);
    }

    @Override
    public List<Map<String, String>> searchApiSixUpstreamInfo() {
        return apiSixProcessService.searchApiSixUpstreamInfo();
    }

    @Override
    public List<Map<String,String>> searchApiSixServiceInfo() {
        return apiSixProcessService.searchApiSixServiceInfo();
    }

    @Override
    public void clearRouteInfo() {
        apiSixProcessService.clearRouteInfo();
    }

    /**
     * 查询API基础信息
     *
     * @param dasReleaseApiParamsVO
     * @return
     */
    private Iterable<DasApiBasicInfo> searchBasicInfos(DasReleaseApiParamsVO dasReleaseApiParamsVO) {
        Iterable<DasApiBasicInfo> apiBasicInfos = null;
        if (!ObjectUtils.isEmpty(dasReleaseApiParamsVO.getApiIds())) {
            //指定apiId集合
            apiBasicInfos = getDasApiBasicInfosByApiId(dasReleaseApiParamsVO);
        } else {
            //模糊匹配路径
            apiBasicInfos = getDasApiBasicInfosByPath(dasReleaseApiParamsVO);
        }
        return apiBasicInfos;
    }

    /**
     * 指定apiId集合查询数据
     *
     * @param dasReleaseApiParamsVO
     * @return
     */
    private Iterable<DasApiBasicInfo> getDasApiBasicInfosByApiId(DasReleaseApiParamsVO dasReleaseApiParamsVO) {
        Iterable<DasApiBasicInfo> apiBasicInfos;
        if (dasReleaseApiParamsVO.getApiSyncType().equalsIgnoreCase(ApiSyncTypeEnum.OFFLINE.getCode())) {
            //下线操作
            apiBasicInfos = dasApiBasicInfoRepository.findAll(qDasApiBasicInfo.apiId.in(dasReleaseApiParamsVO.getApiIds())
                    .and(qDasApiBasicInfo.status.eq(SyncStatusEnum.CREATE_SUCCESS_UPLOAD.getCode())));
        } else {
            apiBasicInfos = dasApiBasicInfoRepository.findAll(qDasApiBasicInfo.apiId.in(dasReleaseApiParamsVO.getApiIds())
                    .and(qDasApiBasicInfo.status.ne(SyncStatusEnum.CREATE_SUCCESS_UPLOAD.getCode())));
        }
        return apiBasicInfos;
    }

    /**
     * 模糊匹配路径
     *
     * @param dasReleaseApiParamsVO
     * @return
     */
    private Iterable<DasApiBasicInfo> getDasApiBasicInfosByPath(DasReleaseApiParamsVO dasReleaseApiParamsVO) {
        Iterable<DasApiBasicInfo> apiBasicInfos;
        if (dasReleaseApiParamsVO.getApiSyncType().equalsIgnoreCase(ApiSyncTypeEnum.OFFLINE.getCode())) {
            //下线操作
            apiBasicInfos = dasApiBasicInfoRepository.findAll(qDasApiBasicInfo.apiPath.like(dasReleaseApiParamsVO.getNearlyApiPath() + "%")
                    .and(qDasApiBasicInfo.status.eq(SyncStatusEnum.CREATE_SUCCESS_UPLOAD.getCode())));
        } else {
            apiBasicInfos = dasApiBasicInfoRepository.findAll(qDasApiBasicInfo.apiPath.like(dasReleaseApiParamsVO.getNearlyApiPath() + "%")
                    .and(qDasApiBasicInfo.status.ne(SyncStatusEnum.CREATE_SUCCESS_UPLOAD.getCode())));
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
                                        Map<String, DasApiBasicInfo> createBasicApiMap,
                                        Set<String> apiIdSet) {
        apiBasicInfos.forEach(dasApiBasicInfo -> {
            if (dasApiBasicInfo.getApiType().equalsIgnoreCase(ApiTypeEnum.REGISTER_API.getCode())) {
                registerBasicApiMap.put(dasApiBasicInfo.getApiId(), dasApiBasicInfo);
            } else {
                createBasicApiMap.put(dasApiBasicInfo.getApiId(), dasApiBasicInfo);
            }
            apiIdSet.add(dasApiBasicInfo.getApiId());
        });
    }


}
