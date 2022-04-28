package com.qk.dm.dataservice.controller.base;

import com.qk.dam.cache.CacheManagerEnum;
import com.qk.dam.cache.RestCache;
import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.model.HttpDataParamModel;
import com.qk.dm.dataservice.mapping.DataServiceMapping;
import com.qk.dm.dataservice.rest.mapping.DataServiceEnum;
import com.qk.dm.dataservice.vo.DasApiBasicInfoVO;
import com.qk.dm.dataservice.vo.DataQueryInfoVO;
import com.qk.dm.feign.DataQueryInfoFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.PathContainer;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Pattern;

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
  @Autowired private DataQueryInfoFeign dataQueryInfoFeign;

  @PostConstruct
  public void init() {
    DefaultCommonResult<List<DataQueryInfoVO>> listDefaultCommonResult =
        dataQueryInfoFeign.dataQueryInfo();
    dataQueryInfoVOList.addAll(listDefaultCommonResult.getData());
  }

  protected HttpDataParamModel restModel(HttpServletRequest request, Map<String, Object> param, Object bodyData, HttpHeaders headers) {
    DataQueryInfoVO queryInfoVO = matchDataQueryInfo(request);

    return HttpDataParamModel.builder()
            .apiId(queryInfoVO.getDasApiBasicInfo().getApiId())
            .uriPathParam(
                    getUriPathParam(queryInfoVO.getDasApiBasicInfo().getApiPath(), request))
            .headers(headers)
            .params(param)
            .body(bodyData)
            .method(RequestMethod.GET)
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
                  DasApiBasicInfoVO dasApiBasicInfo = dataQueryInfoVO.getDasApiBasicInfo();
                  return request.getMethod().equals(dasApiBasicInfo.getRequestType())
                      && matchUriPath(request.getRequestURI(), dasApiBasicInfo.getApiPath());
                })
            .findFirst()
            .orElse(null);
    Objects.requireNonNull(queryInfoVO,"未能查询匹配该路径的请求！");
    return queryInfoVO;
  }

  /**
   * 获取path对应的参数变量
   *
   * @param pathPattern path表达式
   * @param request 请求
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
   * 处理所有rest请求的handler
   *
   * @param httpDataParamModel http传递的参数集合
   */
}
