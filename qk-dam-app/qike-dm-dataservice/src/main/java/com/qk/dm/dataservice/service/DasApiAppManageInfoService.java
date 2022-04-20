package com.qk.dm.dataservice.service;

import com.qk.dam.dataservice.spi.pojo.RouteData;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataservice.vo.DasApiAppManageInfoParamsVO;
import com.qk.dm.dataservice.vo.DasApiAppManageInfoVO;
import com.qk.dm.dataservice.vo.DasApiBasicInfoVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 数据服务_应用管理
 *
 * @author wjq
 * @date 2022/03/18
 * @since 1.0.0
 */
@Service
public interface DasApiAppManageInfoService {

    PageResultVO<DasApiAppManageInfoVO> searchList(DasApiAppManageInfoParamsVO dasApiAppManageParamsVO);

    void insert(DasApiAppManageInfoVO dasApiAppManageInfoVO);

    void update(DasApiAppManageInfoVO dasApiAppManageInfoVO);

    void deleteBulk(String ids);

    List<RouteData> searchApiSixRouteInfo();

    List<Map<String, String>> searchApiSixUpstreamInfo();

    List<Map<String, String>> searchApiSixServiceInfo();

    List<DasApiBasicInfoVO> relationApiList(String apiGroupRoutePath);


}
