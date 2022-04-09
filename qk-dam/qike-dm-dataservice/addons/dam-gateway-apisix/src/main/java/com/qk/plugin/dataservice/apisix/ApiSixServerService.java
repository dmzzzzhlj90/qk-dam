package com.qk.plugin.dataservice.apisix;

import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.commons.util.RestTemplateUtils;
import com.qk.dam.dataservice.spi.server.ServerContext;
import com.qk.dam.dataservice.spi.server.ServerInfo;
import com.qk.dam.dataservice.spi.server.ServerService;
import com.qk.plugin.dataservice.apisix.route.ApiSixResultVO;
import com.qk.plugin.dataservice.apisix.route.constant.ApiSixConstant;
import com.qk.plugin.dataservice.apisix.route.result.Nodes;
import com.qk.plugin.dataservice.apisix.route.result.Value;
import org.springframework.http.*;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wjq
 * @date 2021/8/19 10:38
 * @since 1.0.0
 */
public class ApiSixServerService implements ServerService {

  public static final String NODES = "nodes";
  public static final String ID = "id";
  public static final String NAME = "name";
  public static final String UPSTREAM_ID = "upstream_id";
  private ServerContext serverContext;

    public ApiSixServerService() {
    }

    public ApiSixServerService(ServerContext serverContext) {
        this.serverContext = serverContext;
    }

    @Override
    public List<Map<String, String>> getServerInfo() {
        List<Map<String, String>> serviceInfo = new LinkedList<>();

        Map<String, String> params = serverContext.getParams();
        HttpEntity httpEntity = setHttpEntity(null, params);
        ResponseEntity<ApiSixResultVO> responseEntity =
                RestTemplateUtils.exchange(
                        params.get(ApiSixConstant.API_SIX_ADMIN_SERVER_URL_KEY),
                        HttpMethod.GET,
                        httpEntity,
                        ApiSixResultVO.class);
        if (isEmptyResponseBody(responseEntity.getBody().getNode())) {
            Object nodes = responseEntity.getBody().getNode().get(NODES);
            if (isEmptyResponseBody(nodes)) {
                List<Nodes> nodeList =
                        GsonUtil.fromJsonString(
                                GsonUtil.toJsonString(nodes), new TypeToken<List<Nodes>>() {
                                }.getType());
                if (null != nodeList && nodeList.size() > 0) {
                    for (Nodes node : nodeList) {
                        Value value = node.getValue();
                        Map<String, String> serviceInfoMap = new LinkedHashMap<>();
                        serviceInfoMap.put(ID, value.getId());
                        serviceInfoMap.put(NAME, value.getName());
                        serviceInfoMap.put(UPSTREAM_ID, value.getUpstream_id());
                        serviceInfo.add(serviceInfoMap);
                    }
                }
                return serviceInfo;
            }
        }
        return serviceInfo;
    }

    @Override
    public List apiSixServiceInfoIds() {
        List serviceInfoIds = new ArrayList<>();
        Map<String, String> params = serverContext.getParams();
        HttpEntity httpEntity = setHttpEntity(null, params);
        ResponseEntity<ApiSixResultVO> responseEntity =
                RestTemplateUtils.exchange(
                        params.get(ApiSixConstant.API_SIX_ADMIN_SERVER_URL_KEY),
                        HttpMethod.GET,
                        httpEntity,
                        ApiSixResultVO.class);
        if (isEmptyResponseBody(responseEntity.getBody().getNode())) {
            Object nodes = responseEntity.getBody().getNode().get("nodes");
            if (isEmptyResponseBody(nodes)) {
                List<Nodes> nodeList =
                        GsonUtil.fromJsonString(
                                GsonUtil.toJsonString(nodes), new TypeToken<List<Nodes>>() {
                                }.getType());
                serviceInfoIds =
                        nodeList.stream().map(nodes1 -> nodes1.getValue().getId()).collect(Collectors.toList());
                return serviceInfoIds;
            }
        }
        return serviceInfoIds;
    }

    private boolean isEmptyResponseBody(Object node) {
        return !ObjectUtils.isEmpty(node);
    }

    private HttpEntity setHttpEntity(ServerInfo serverInfo, Map<String, String> params) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add(ApiSixConstant.API_SIX_HEAD_KEY, params.get(ApiSixConstant.API_SIX_HEAD_KEY));
        return new HttpEntity<>(serverInfo, headers);
    }
}
