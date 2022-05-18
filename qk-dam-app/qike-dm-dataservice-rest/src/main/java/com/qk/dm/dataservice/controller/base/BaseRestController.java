package com.qk.dm.dataservice.controller.base;

import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.Pagination;
import com.qk.dam.model.HttpDataParamModel;
import com.qk.dm.dataservice.vo.DasApiBasicInfoVO;
import com.qk.dm.dataservice.vo.DataQueryInfoVO;
import com.qk.dm.feign.DataQueryInfoFeign;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.PathContainer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author zhudaoming
 */
@RestController
public abstract class BaseRestController {
    public static final String TIPS = "数据查询服务返回查询结果！";
    private static final PathPatternParser PATH_PATTERN_PARSER = new PathPatternParser();
    private static final Pattern PATTERN_PATH_VAR = Pattern.compile("\\{([^}])*}");
    private static final Base64.Encoder BASE64 = Base64.getEncoder();
    private final List<DataQueryInfoVO> dataQueryInfoVOList = new ArrayList<>();
    @Autowired
    private DataQueryInfoFeign dataQueryInfoFeign;

    @PostConstruct
    public void init() {
        DefaultCommonResult<List<DataQueryInfoVO>> listDefaultCommonResult =
                dataQueryInfoFeign.dataQueryInfo();
        dataQueryInfoVOList.addAll(listDefaultCommonResult.getData());
    }

    @Scheduled(cron = "0/50 * * * * ? ")
    public void dataQueryInfo() {
        // 当前存在的所有API
        List<String> apiIds = dataQueryInfoVOList
                .stream()
                .map(dataQueryInfoVO ->
                        dataQueryInfoVO.getApiBasicInfoVO().getApiId())
                .collect(Collectors.toList());
        // 查询到变化的API
        DefaultCommonResult<List<DataQueryInfoVO>> listDefaultCommonResult = dataQueryInfoFeign.dataQueryInfoLast(getDsLastDasCreateApi());
        List<DataQueryInfoVO> queryInfoList = listDefaultCommonResult.getData();

        if (CollectionUtils.isNotEmpty(queryInfoList)) {
            // 存在也变化的API
            List<DataQueryInfoVO> updateApi = queryInfoList
                    .stream()
                    .filter(dataQueryInfoVO ->
                            apiIds.contains(dataQueryInfoVO.getApiBasicInfoVO().getApiId()))
                    .collect(Collectors.toList());
            dataQueryInfoVOList.removeAll(updateApi);
            dataQueryInfoVOList.addAll(queryInfoList);
        }
    }

    public Long getDsLastDasCreateApi() {
        return dataQueryInfoVOList.stream()
                .map(
                        dataQueryInfoVO ->
                                dataQueryInfoVO.getApiCreateDefinitionVO().getGmtModified().getTime())
                .max(Comparator.comparing(Long::longValue))
                .orElse(0L);
    }

    protected HttpDataParamModel restModel(HttpServletRequest request,
                                           Map<String, Object> param,
                                           Object bodyData,
                                           HttpHeaders headers,
                                           RequestMethod method) {
        DataQueryInfoVO queryInfoVO = matchDataQueryInfo(request);

        return HttpDataParamModel.builder()
                .apiId(queryInfoVO.getApiBasicInfoVO().getApiId())
                .uriPathParam(
                        getUriPathParam(queryInfoVO.getApiBasicInfoVO().getApiPath(), request))
                .headers(headers)
                .body(bodyData)
                .params(param)
                .method(method)
                .cacheLevel(queryInfoVO.getApiCreateDefinitionVO().getCacheLevel())
                .pageFlag(queryInfoVO.getApiCreateDefinitionVO().getPageFlag())
                .resultDataType(queryInfoVO.getApiBasicInfoVO().getResultDataType())
                .pagination(pagination(param, queryInfoVO.getApiCreateDefinitionVO().getPageFlag()))
                .build();
    }

    /**
     * 匹配请求的数据查询
     *
     * @param request 请求
     * @return DataQueryInfoVO 查询数据
     */
    private DataQueryInfoVO matchDataQueryInfo(HttpServletRequest request) {
        DataQueryInfoVO queryInfoVO =
                dataQueryInfoVOList.stream()
                        .filter(
                                dataQueryInfoVO -> {
                                    DasApiBasicInfoVO dasApiBasicInfo = dataQueryInfoVO.getApiBasicInfoVO();
                                    return request.getMethod().equals(dasApiBasicInfo.getRequestType())
                                            && matchUriPath(request.getRequestURI(), dasApiBasicInfo.getApiPath());
                                })
                        .findFirst()
                        .orElse(null);
        Objects.requireNonNull(queryInfoVO, "未能查询匹配该路径的请求！");
        return queryInfoVO;
    }

    /**
     * 获取path对应的参数变量
     *
     * @param pathPattern path表达式
     * @param request     请求
     * @return Map<String, String> 变量结果
     */
    protected Map<String, String> getUriPathParam(String pathPattern, HttpServletRequest request) {
        PathPattern.PathMatchInfo pathMatchInfo =
                PATH_PATTERN_PARSER
                        .parse(pathPattern)
                        .matchAndExtract(PathContainer.parsePath(request.getRequestURI()));
        if (Objects.nonNull(pathMatchInfo)) {
            return pathMatchInfo.getUriVariables();
        }
        return Map.of();
    }

    public Boolean matchUriPath(String restPath, String pathPattern) {
        return PATH_PATTERN_PARSER.parse(pathPattern).matches(PathContainer.parsePath(restPath));
    }

    protected String uriRouteCode(HttpServletRequest request) {
        String rowPath = matchRowPath(request);
        // FIXME 是否使用sha不可逆处理?? return HASHER_SHA256.putString(rowPath,
        // Charsets.UTF_8).hash().toString();
        return new String(BASE64.encode(rowPath.getBytes()));
    }

    private String matchRowPath(HttpServletRequest request) {
        return PATTERN_PATH_VAR
                .matcher(request.getMethod() + ":" + request.getRequestURI())
                .replaceAll("{}");
    }

    /**
     * 分页参数设置
     *
     * @param param
     * @param pageFlag
     * @return
     */
    private Pagination pagination(Map<String, Object> param, Boolean pageFlag) {
        Pagination pagination = new Pagination();
        pagination.setSortField("");
        if (pageFlag) {
            String pageNum = param.get(Pagination.PAGE_NUM).toString();
            if (Objects.nonNull(pageNum)) {
                pagination.setPage(Integer.parseInt(pageNum));
            } else {
                pagination.setPage(Pagination.PAGE_DEFAULT_NUM);
            }
            String pageSize = param.get(Pagination.PAGE_SIZE).toString();
            if (Objects.nonNull(pageSize)) {
                pagination.setSize(Integer.parseInt(pageSize));
            } else {
                pagination.setSize(Pagination.PAGE_DEFAULT_SIZE);
            }
        }
        return pagination;
    }

    /**
     * 处理所有rest请求的handler
     *
     * @param httpDataParamModel http传递的参数集合
     */
}
