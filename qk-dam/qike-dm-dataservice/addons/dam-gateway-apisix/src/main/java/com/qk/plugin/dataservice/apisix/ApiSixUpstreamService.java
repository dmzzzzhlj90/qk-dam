package com.qk.plugin.dataservice.apisix;

import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.commons.util.RestTemplateUtils;
import com.qk.dam.dataservice.spi.upstream.UpstreamContext;
import com.qk.dam.dataservice.spi.upstream.UpstreamInfo;
import com.qk.dam.dataservice.spi.upstream.UpstreamService;
import com.qk.plugin.dataservice.apisix.route.ApiSixResultVO;
import com.qk.plugin.dataservice.apisix.route.constant.ApiSixConstant;
import com.qk.plugin.dataservice.apisix.route.result.Nodes;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.*;
import org.springframework.util.ObjectUtils;

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
    List<Nodes> nodeList = null;
    Map<String, String> params = upstreamContext.getParams();
    HttpEntity<UpstreamInfo> httpEntity = setHttpEntity(null, params);
    ResponseEntity<ApiSixResultVO> responseEntity =
        RestTemplateUtils.exchange(
            params.get(ApiSixConstant.API_SIX_ADMIN_UPSTREAM_URL_KEY),
            HttpMethod.GET,
            httpEntity,
            ApiSixResultVO.class);
    if (isEmptyResponseEntityBody(responseEntity.getBody().getNode())) {
      Object nodes = responseEntity.getBody().getNode().get("nodes");
      if (isEmptyResponseEntityBody(nodes)) {
        nodeList =
            GsonUtil.fromJsonString(
                GsonUtil.toJsonString(nodes), new TypeToken<List<Nodes>>() {}.getType());
        return nodeList;
      }
    }
    return nodeList;
  }

  @Override
  public List apiSixUpstreamInfoIds() {
    List upstreamInfoIds = null;
    Map<String, String> params = upstreamContext.getParams();
    HttpEntity<UpstreamInfo> httpEntity = setHttpEntity(null, params);
    ResponseEntity<ApiSixResultVO> responseEntity =
        RestTemplateUtils.exchange(
            params.get(ApiSixConstant.API_SIX_ADMIN_UPSTREAM_URL_KEY),
            HttpMethod.GET,
            httpEntity,
            ApiSixResultVO.class);
    if (isEmptyResponseEntityBody(responseEntity.getBody().getNode())) {
      Object nodes = responseEntity.getBody().getNode().get("nodes");
      if (isEmptyResponseEntityBody(nodes)) {
        List<Nodes> nodeList =
            GsonUtil.fromJsonString(
                GsonUtil.toJsonString(nodes), new TypeToken<List<Nodes>>() {}.getType());
        upstreamInfoIds =
            nodeList.stream().map(nodes1 -> nodes1.getValue().getId()).collect(Collectors.toList());
        return upstreamInfoIds;
      }
    }
    return upstreamInfoIds;
  }

  private boolean isEmptyResponseEntityBody(Object node) {
    return !ObjectUtils.isEmpty(node);
  }

  private HttpEntity<UpstreamInfo> setHttpEntity(
      UpstreamInfo upstreamInfo, Map<String, String> params) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());
    headers.add("Accept", MediaType.APPLICATION_JSON.toString());
    headers.add(ApiSixConstant.API_SIX_HEAD_KEY, params.get(ApiSixConstant.API_SIX_HEAD_KEY));
    return new HttpEntity<>(upstreamInfo, headers);
  }
}
