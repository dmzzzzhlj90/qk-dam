package com.qk.plugin.dataservice.apisix;

import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.commons.util.RestTemplateUtils;
import com.qk.dam.dataservice.spi.pojo.RouteData;
import com.qk.dam.dataservice.spi.route.RouteContext;
import com.qk.dam.dataservice.spi.route.RouteInfo;
import com.qk.dam.dataservice.spi.route.RoutesService;
import com.qk.plugin.dataservice.apisix.route.ApiSixResultVO;
import com.qk.plugin.dataservice.apisix.route.ApiSixRouteInfo;
import com.qk.plugin.dataservice.apisix.route.constant.ApiSixConstant;
import com.qk.plugin.dataservice.apisix.route.result.Nodes;
import com.qk.plugin.dataservice.apisix.route.result.Value;
import org.springframework.http.*;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wjq
 * @date 2021/8/19 10:38
 * @since 1.0.0
 */
public class ApiSixRoutesService implements RoutesService {

    private RouteContext routeContext;

    public ApiSixRoutesService() {
    }

    public ApiSixRoutesService(RouteContext routeContext) {
        this.routeContext = routeContext;
    }

    @Override
    public void initRouteInfo() {
        ApiSixRouteInfo routeInfo = (ApiSixRouteInfo) routeContext.getRouteInfo();
        HttpEntity httpEntity = setHttpEntity(routeInfo, routeContext.getParams());
        RestTemplateUtils.exchange(
                routeContext.getParams().get(ApiSixConstant.API_SIX_ADMIN_ROUTE_URL_KEY)
                        + routeContext.getParams().get(ApiSixConstant.API_SIX_ROUTE_ID),
                HttpMethod.PUT,
                httpEntity,
                String.class);
    }

    @Override
    public List<RouteData> getRouteInfo() {
        List<RouteData> result = new ArrayList<>();
        Map<String, String> params = routeContext.getParams();
        HttpEntity httpEntity = setHttpEntity(null, params);
        ResponseEntity<ApiSixResultVO> responseEntity =
                RestTemplateUtils.exchange(
                        params.get(ApiSixConstant.API_SIX_ADMIN_ROUTE_URL_KEY),
                        HttpMethod.GET,
                        httpEntity,
                        ApiSixResultVO.class);
        if (isEmptyResponseEntityBody(responseEntity.getBody().getNode())) {
            Object nodes = responseEntity.getBody().getNode().get("nodes");
            if (isEmptyResponseEntityBody(nodes)) {
                List<Nodes> nodeList =
                        GsonUtil.fromJsonString(GsonUtil.toJsonString(nodes), new TypeToken<List<Nodes>>() {
                        }.getType());
                for (Nodes node : nodeList) {
                    Value value = node.getValue();
                    RouteData routeData = RouteData.builder()
                            .id(value.getId())
                            .name(value.getName())
                            .uri(value.getUri())
                            .build();
                    result.add(routeData);
                }
            }
        }
        return result;
    }


    @Override
    public RouteInfo getRouteInfoById() {
        ApiSixRouteInfo routeData = null;
        Map<String, String> params = routeContext.getParams();
        String routeId = params.get(ApiSixConstant.API_SIX_ROUTE_ID);

        HttpEntity httpEntity = setHttpEntity(null, params);
        ResponseEntity<ApiSixResultVO> responseEntity =
                RestTemplateUtils.exchange(
                        params.get(ApiSixConstant.API_SIX_ADMIN_ROUTE_URL_KEY) + routeId,
                        HttpMethod.GET,
                        httpEntity,
                        ApiSixResultVO.class);

        if (isEmptyResponseEntityBody(responseEntity.getBody().getNode())) {
            Object node = responseEntity.getBody().getNode();
            if (isEmptyResponseEntityBody(node)) {
                Nodes nodes = GsonUtil.fromJsonString(
                        GsonUtil.toJsonString(node), new TypeToken<Nodes>() {
                        }.getType());
                Value value = nodes.getValue();
                routeData = ApiSixRouteInfo.builder()
                        .id(value.getId())
                        .name(value.getName())
                        .uri(value.getUri())
                        .methods(value.getMethods())
                        .plugins(value.getPlugins())
                        .upstream_id(value.getUpstream_id())
                        .status(value.getStatus())
                        .build();
            }
        }
        return routeData;
    }

    private boolean isEmptyResponseEntityBody(Object node) {
        return !ObjectUtils.isEmpty(node);
    }

    @Override
    public void clearRoute() {
        List<RouteData> routeInfos = getRouteInfo();
        if (!ObjectUtils.isEmpty(routeInfos)) {
            for (RouteData routeData : routeInfos) {
                deleteRouteByRouteId((routeData.getId()));
            }
        }
    }

    @Override
    public void deleteRouteByRouteId() {
        deleteRouteByRouteId(routeContext.getParams().get(ApiSixConstant.API_SIX_ROUTE_ID));
    }

    @Override
    public void updateRoutePlugins() {
        ApiSixRouteInfo routeInfo = (ApiSixRouteInfo)routeContext.getRouteInfo();
        HttpEntity httpEntity = setHttpEntity(routeInfo, routeContext.getParams());
        RestTemplateUtils.exchange(
                routeContext.getParams().get(ApiSixConstant.API_SIX_ADMIN_ROUTE_URL_KEY) + routeInfo.getId(),
                HttpMethod.PUT,
                httpEntity,
                String.class);
    }

    private void deleteRouteByRouteId(String routeId) {
        HttpEntity httpEntity = setHttpEntity(null, routeContext.getParams());
        RestTemplateUtils.exchange(
                routeContext.getParams().get(ApiSixConstant.API_SIX_ADMIN_ROUTE_URL_KEY) + routeId,
                HttpMethod.DELETE,
                httpEntity,
                String.class);
    }

    private HttpEntity setHttpEntity(ApiSixRouteInfo routeInfo, Map<String, String> params) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add(ApiSixConstant.API_SIX_HEAD_KEY, params.get(ApiSixConstant.API_SIX_HEAD_KEY));
        return new HttpEntity<>(routeInfo, headers);
    }
}
