package com.qk.dm.dataservice.service;

import com.qk.dm.dataservice.vo.DasApiRouteVO;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * 数据服务_API路由Route匹配
 *
 * @author wjq
 * @date 20210907
 * @since 1.0.0
 */
@Service
public interface DasApiRouteService {

  List<DasApiRouteVO> getDasApiRouteInfoAll();

  void addDasApiRoute(DasApiRouteVO dasApiRouteVO);

  void updateDasApiRoute(DasApiRouteVO dasApiRouteVO);

  void bulkDeleteDasApiRoute(List<String> ids);
}
