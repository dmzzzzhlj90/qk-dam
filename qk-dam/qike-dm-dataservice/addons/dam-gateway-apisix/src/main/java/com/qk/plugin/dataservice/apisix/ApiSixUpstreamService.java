package com.qk.plugin.dataservice.apisix;

import com.qk.dam.commons.util.RestTemplateUtils;
import com.qk.dam.dataservice.spi.route.RouteContext;
import com.qk.dam.dataservice.spi.route.RoutesService;
import com.qk.dam.dataservice.spi.upstream.UpstreamContext;
import com.qk.dam.dataservice.spi.upstream.UpstreamInfo;
import com.qk.dam.dataservice.spi.upstream.UpstreamService;
import com.qk.plugin.dataservice.apisix.route.ApiSixResultVO;
import com.qk.plugin.dataservice.apisix.route.ApiSixRouteInfo;
import com.qk.plugin.dataservice.apisix.route.constant.ApiSixConstant;
import com.qk.plugin.dataservice.apisix.route.entity.Upstream;
import com.qk.plugin.dataservice.apisix.route.result.Nodes;
import org.springframework.http.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wjq
 * @date 2021/8/19 10:38
 * @since 1.0.0
 */
public class ApiSixUpstreamService implements UpstreamService {

  private UpstreamContext upstreamContext;

  public ApiSixUpstreamService() {}

  public ApiSixUpstreamService(UpstreamContext upstreamContext) {
    this.upstreamContext = upstreamContext;
  }


  @Override
  public List getUpstreamInfo() {
    Map<String, String> params = upstreamContext.getParams();
    HttpEntity httpEntity = setHttpEntity(null, params);
    ResponseEntity<ApiSixResultVO> responseEntity =
            RestTemplateUtils.exchange(
                    params.get(ApiSixConstant.API_SIX_ADMIN_UPSTREAM_URL_KEY),
                    HttpMethod.GET,
                    httpEntity,
                    ApiSixResultVO.class);
    if (null != responseEntity.getBody().getNode()) {
      List<Nodes> nodes = responseEntity.getBody().getNode().getNodes();
      return nodes;
    }
    return null;
  }

  @Override
  public List apiSixUpstreamInfoIds() {
    List upstreamInfoIds = null;
    Map<String, String> params = upstreamContext.getParams();
    HttpEntity httpEntity = setHttpEntity(null, params);
    ResponseEntity<ApiSixResultVO> responseEntity =
            RestTemplateUtils.exchange(
                    params.get(ApiSixConstant.API_SIX_ADMIN_UPSTREAM_URL_KEY),
                    HttpMethod.GET,
                    httpEntity,
                    ApiSixResultVO.class);
    if (null != responseEntity.getBody().getNode()) {
      List<Nodes> nodes = responseEntity.getBody().getNode().getNodes();
      upstreamInfoIds= nodes.stream().map(nodes1 -> nodes1.getValue().getId()).collect(Collectors.toList());
      return upstreamInfoIds;
    }
    return null;
  }

  private HttpEntity setHttpEntity(UpstreamInfo upstreamInfo, Map<String, String> params) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());
    headers.add("Accept", MediaType.APPLICATION_JSON.toString());
    headers.add(ApiSixConstant.API_SIX_HEAD_KEY, params.get(ApiSixConstant.API_SIX_HEAD_KEY));
    return new HttpEntity<>(upstreamInfo, headers);
  }

}
