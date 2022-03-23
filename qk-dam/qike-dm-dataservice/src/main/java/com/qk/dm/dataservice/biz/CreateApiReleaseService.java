package com.qk.dm.dataservice.biz;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.dataservice.spi.pojo.RouteData;
import com.qk.dam.dataservice.spi.route.RouteContext;
import com.qk.dm.dataservice.constant.SyncStatusEnum;
import com.qk.dm.dataservice.entity.DasApiBasicInfo;
import com.qk.dm.dataservice.repositories.DasApiBasicInfoRepository;
import com.qk.dm.dataservice.vo.DasReleaseApiParamsVO;
import com.qk.plugin.dataservice.apisix.route.ApiSixRouteInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 新建API发布服务
 *
 * @author wjq
 * @date 2022/3/2
 * @since 1.0.0
 */
@Component
public class CreateApiReleaseService {
    private static final Log LOG = LogFactory.get("新建API发布服务");


    private final DasApiBasicInfoRepository dasApiBasicInfoRepository;
    private final ApiSixProcessService apiSixProcessService;

    @Autowired
    public CreateApiReleaseService(DasApiBasicInfoRepository dasApiBasicInfoRepository,
                                   ApiSixProcessService apiSixProcessService) {
        this.dasApiBasicInfoRepository = dasApiBasicInfoRepository;
        this.apiSixProcessService = apiSixProcessService;
    }


    /**
     * 同步注册API
     *
     * @param createBasicApiMap
     * @param dasReleaseApiParamsVO
     * @return
     */
    public int syncCreateApi(Map<String, DasApiBasicInfo> createBasicApiMap, DasReleaseApiParamsVO dasReleaseApiParamsVO) {
        AtomicInteger successfulNum = new AtomicInteger(0);
        AtomicInteger failNum = new AtomicInteger(0);
        try {
            //设置Route上下文参数
            RouteContext routeContext = apiSixProcessService.buildRouteContext();
            //获取路由ID集合
            List<RouteData> routeInfoList = apiSixProcessService.getRouteInfo(routeContext);
            //发布新建注册API

            for (String apiId : createBasicApiMap.keySet()) {
                DasApiBasicInfo dasApiBasicInfo = createBasicApiMap.get(apiId);
                //检测路由信息是否包含需要发布的URL信息
//                boolean checkUrlFlag = UrlProcessUtils.checkUrlIsContain(routeInfoList, dasApiBasicInfo.getApiPath());
                //更新发布状态 新建API使用统一路径根据apiId进行数据查询
                updateCreateStatus(successfulNum, failNum, routeContext, routeInfoList, dasApiBasicInfo, true);
            }
            LOG.info("成功发布新建Api个数:【{}】", successfulNum);
            LOG.info("失败发布新建Api个数:【{}】", failNum);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException(e.getMessage());
        }
        return (failNum.get()) > 0 ? 0 : 1;
    }

    /**
     * 同步新建API路由信息
     *
     * @param dasApiBasicInfo
     * @param routeContext
     * @param routeInfoList
     * @return
     */
    private boolean syncRegisterRouteInfo(DasApiBasicInfo dasApiBasicInfo,
                                          RouteContext routeContext,
                                          List<RouteData> routeInfoList) {
        boolean syncflag = false;
        try {
            // 根据ApiID清除对应的Route
            apiSixProcessService.deleteRouteByRouteId(routeContext, dasApiBasicInfo.getApiId(), routeInfoList);
            LOG.info("成功清除新建Api,Path为:【{}】", dasApiBasicInfo.getApiPath());
            //保存注册API路由信息
            syncflag = saveRegisterRouteInfo(dasApiBasicInfo, routeContext);
            LOG.info("成功同步新建Api,Path:【{}】", dasApiBasicInfo.getApiPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return syncflag;
    }

    /**
     * 保存注册API路由信息
     *
     * @param dasApiBasicInfo
     * @param routeContext
     * @return
     */
    private boolean saveRegisterRouteInfo(DasApiBasicInfo dasApiBasicInfo,
                                          RouteContext routeContext) {
        boolean flag = false;
        try {
            //构建新建API路由对象信息
            ApiSixRouteInfo apiSixRouteInfo = apiSixProcessService.buildCreateApiSixRouteInfo(dasApiBasicInfo);
            //初始化创建对应API路由信息
            apiSixProcessService.initApiSixGatewayRoute(apiSixRouteInfo, dasApiBasicInfo.getApiId(), routeContext);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 更新新建API发布状态
     *
     * @param successfulNum
     * @param failNum
     * @param routeContext
     * @param routeInfoList
     * @param dasApiBasicInfo
     * @param checkUrlFlag
     */
    private void updateCreateStatus(AtomicInteger successfulNum,
                                    AtomicInteger failNum,
                                    RouteContext routeContext,
                                    List<RouteData> routeInfoList,
                                    DasApiBasicInfo dasApiBasicInfo,
                                    boolean checkUrlFlag) {
        if (checkUrlFlag) {
            //包含,更新发布状态
            updateStatus(dasApiBasicInfo, true, successfulNum, failNum);
        } else {
            //不包含,新增,更新发布状态
            //同步新建API路由信息
            boolean syncFlag = syncRegisterRouteInfo(dasApiBasicInfo, routeContext, routeInfoList);
            updateStatus(dasApiBasicInfo, syncFlag, successfulNum, failNum);
        }
    }

    /**
     * 更新API发布状态
     *
     * @param dasApiBasicInfo
     * @param syncFlag
     * @param successfulNum
     * @param failNum
     */
    private void updateStatus(DasApiBasicInfo dasApiBasicInfo,
                              boolean syncFlag,
                              AtomicInteger successfulNum,
                              AtomicInteger failNum) {
        if (syncFlag) {
            dasApiBasicInfo.setStatus(SyncStatusEnum.SUCCESS_UPLOAD.getCode());
            dasApiBasicInfoRepository.saveAndFlush(dasApiBasicInfo);
            LOG.info("发布同步成功,更新新建API同步状态成功,API路径为:【{}】", dasApiBasicInfo.getApiPath());
            successfulNum.getAndIncrement();
        } else {
            dasApiBasicInfo.setStatus(SyncStatusEnum.FAIL_UPLOAD.getCode());
            dasApiBasicInfoRepository.saveAndFlush(dasApiBasicInfo);
            LOG.info("发布失败,更新新建API同步状态,API路径为:【{}】", dasApiBasicInfo.getApiPath());
            failNum.getAndIncrement();
        }
    }
}
