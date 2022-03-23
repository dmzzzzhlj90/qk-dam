package com.qk.dm.dataservice.biz;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.dataservice.spi.pojo.RouteData;
import com.qk.dam.dataservice.spi.route.RouteContext;
import com.qk.dm.dataservice.constant.SyncStatusEnum;
import com.qk.dm.dataservice.entity.DasApiBasicInfo;
import com.qk.dm.dataservice.entity.DasApiRegister;
import com.qk.dm.dataservice.entity.QDasApiRegister;
import com.qk.dm.dataservice.repositories.DasApiBasicInfoRepository;
import com.qk.dm.dataservice.repositories.DasApiRegisterRepository;
import com.qk.dm.dataservice.utils.UrlProcessUtils;
import com.qk.dm.dataservice.vo.DasReleaseApiParamsVO;
import com.qk.plugin.dataservice.apisix.route.ApiSixRouteInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 注册API发布服务
 *
 * @author wjq
 * @date 2022/3/2
 * @since 1.0.0
 */
@Component
public class RegisterApiReleaseService {
    private static final Log LOG = LogFactory.get("注册API发布服务");

    private static final QDasApiRegister qDasApiRegister = QDasApiRegister.dasApiRegister;


    private final DasApiBasicInfoRepository dasApiBasicInfoRepository;
    private final DasApiRegisterRepository dasApiRegisterRepository;
    private final ApiSixProcessService apiSixProcessService;

    @Autowired
    public RegisterApiReleaseService(DasApiBasicInfoRepository dasApiBasicInfoRepository,
                                     DasApiRegisterRepository dasApiRegisterRepository,
                                     ApiSixProcessService apiSixProcessService) {
        this.dasApiBasicInfoRepository = dasApiBasicInfoRepository;
        this.dasApiRegisterRepository = dasApiRegisterRepository;
        this.apiSixProcessService = apiSixProcessService;
    }


    /**
     * 同步注册API
     *
     * @param registerBasicApiMap
     * @param dasReleaseApiParamsVO
     * @return
     */
    public int syncRegisterApi(Map<String, DasApiBasicInfo> registerBasicApiMap, DasReleaseApiParamsVO dasReleaseApiParamsVO) {
        AtomicInteger successfulNum = new AtomicInteger(0);
        AtomicInteger failNum = new AtomicInteger(0);
        try {
            //设置Route上下文参数
            RouteContext routeContext = apiSixProcessService.buildRouteContext();
            //获取路由ID集合
            List<RouteData> routeInfoList = apiSixProcessService.getRouteInfo(routeContext);
            //根据apiID查询注册API信息
            Iterable<DasApiRegister> dasApiRegisters = dasApiRegisterRepository.findAll(qDasApiRegister.apiId.in(registerBasicApiMap.keySet()));
            //发布同步注册API
            for (DasApiRegister dasApiRegister : dasApiRegisters) {
                DasApiBasicInfo dasApiBasicInfo = registerBasicApiMap.get(dasApiRegister.getApiId());
                //检测路由信息是否包含需要发布的URL信息
                boolean checkUrlFlag = UrlProcessUtils.checkUrlIsContain(routeInfoList, dasApiRegister.getBackendPath());
                //更新发布状态
                updateRegisterStatus(successfulNum, failNum, routeContext, routeInfoList, dasApiRegister, dasApiBasicInfo, checkUrlFlag);
            }
            LOG.info("成功发布注册Api个数:【{}】", successfulNum);
            LOG.info("失败发布注册Api个数:【{}】", failNum);

        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException(e.getMessage());
        }
        return (failNum.get()) > 0 ? 0 : 1;
    }

    /**
     * 同步注册API路由信息
     *
     * @param dasApiBasicInfo
     * @param routeContext
     * @param routeInfoList
     * @param dasApiRegister
     * @return
     */
    private boolean syncRegisterRouteInfo(DasApiBasicInfo dasApiBasicInfo,
                                          RouteContext routeContext,
                                          List<RouteData> routeInfoList,
                                          DasApiRegister dasApiRegister) {
        boolean syncflag = false;
        try {
            // 根据ApiID清除对应的Route
            apiSixProcessService.deleteRouteByRouteId(routeContext, dasApiRegister.getApiId(), routeInfoList);
            LOG.info("成功清除注册Api,Path为:【{}】", dasApiRegister.getBackendPath());
            //保存注册API路由信息
            syncflag = saveRegisterRouteInfo(dasApiBasicInfo, routeContext, dasApiRegister);
            LOG.info("成功同步注册Api,Path:【{}】", dasApiRegister.getBackendPath());
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
     * @param dasApiRegister
     * @return
     */
    private boolean saveRegisterRouteInfo(DasApiBasicInfo dasApiBasicInfo,
                                          RouteContext routeContext,
                                          DasApiRegister dasApiRegister) {
        boolean flag = false;
        try {
            //构建注册API路由对象信息
            ApiSixRouteInfo apiSixRouteInfo = apiSixProcessService.buildRegisterApiSixRouteInfo(dasApiBasicInfo, dasApiRegister);
            //初始化创建对应API路由信息
            apiSixProcessService.initApiSixGatewayRoute(apiSixRouteInfo, dasApiRegister.getApiId(), routeContext);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 更新注册API发布状态
     *
     * @param successfulNum
     * @param failNum
     * @param routeContext
     * @param routeInfoList
     * @param dasApiRegister
     * @param dasApiBasicInfo
     * @param checkUrlFlag
     */
    private void updateRegisterStatus(AtomicInteger successfulNum,
                                      AtomicInteger failNum,
                                      RouteContext routeContext,
                                      List<RouteData> routeInfoList,
                                      DasApiRegister dasApiRegister,
                                      DasApiBasicInfo dasApiBasicInfo,
                                      boolean checkUrlFlag) {
        if (checkUrlFlag) {
            //包含,更新发布状态
            updateStatus(dasApiBasicInfo, dasApiRegister, true, successfulNum, failNum);
        } else {
            //不包含,新增,更新发布状态
            //同步注册API路由信息
            boolean syncFlag = syncRegisterRouteInfo(dasApiBasicInfo, routeContext, routeInfoList, dasApiRegister);
            updateStatus(dasApiBasicInfo, dasApiRegister, syncFlag, successfulNum, failNum);
        }
    }

    /**
     * 更新API发布状态
     *
     * @param dasApiBasicInfo
     * @param dasApiRegister
     * @param syncFlag
     * @param successfulNum
     * @param failNum
     */
    private void updateStatus(DasApiBasicInfo dasApiBasicInfo,
                              DasApiRegister dasApiRegister,
                              boolean syncFlag,
                              AtomicInteger successfulNum,
                              AtomicInteger failNum) {
        if (syncFlag) {
            dasApiBasicInfo.setStatus(SyncStatusEnum.SUCCESS_UPLOAD.getCode());
            dasApiBasicInfoRepository.saveAndFlush(dasApiBasicInfo);
            LOG.info("发布同步成功,更新注册API同步状态成功,API路径为:【{}】", dasApiRegister.getBackendPath());
            successfulNum.getAndIncrement();
        } else {
            dasApiBasicInfo.setStatus(SyncStatusEnum.FAIL_UPLOAD.getCode());
            dasApiBasicInfoRepository.saveAndFlush(dasApiBasicInfo);
            LOG.info("发布失败,更新注册API同步状态,API路径为:【{}】", dasApiRegister.getBackendPath());
            failNum.getAndIncrement();
        }
    }
}
