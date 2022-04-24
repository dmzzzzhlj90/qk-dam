package com.qk.dm.dataservice.rest.controller.base;

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

  @DataServiceMapping(value = DataServiceEnum.MATCH_ALL, method = RequestMethod.GET)
  public DefaultCommonResult<Object> getHandler(
      HttpServletRequest request,
      @RequestParam(required = false) Map<String, Object> param,
      @RequestBody(required = false) Object bodyData,
      @RequestHeader(required = false) HttpHeaders headers) {
    DataQueryInfoVO queryInfoVO = matchDataQueryInfo(request);

    Object data =
        restHandler(
            queryInfoVO,
            HttpDataParamModel.builder()
                .uriPathParam(
                    getUriPathParam(queryInfoVO.getDasApiBasicInfo().getApiPath(), request))
                .headers(headers)
                .params(param)
                .body(bodyData)
                .method(RequestMethod.GET)
                .build());
    return DefaultCommonResult.success(ResultCodeEnum.OK, data, TIPS);
  }

  @DataServiceMapping(value = DataServiceEnum.MATCH_ALL, method = RequestMethod.POST)
  public DefaultCommonResult<Object> postHandler(
      HttpServletRequest request,
      @RequestParam(required = false) Map<String, Object> param,
      @RequestBody(required = false) Object bodyData,
      @RequestHeader(required = false) HttpHeaders headers) {
    DataQueryInfoVO queryInfoVO = matchDataQueryInfo(request);
    Object data =
        restHandler(
            queryInfoVO,
            HttpDataParamModel.builder()
                .uriPathParam(
                    getUriPathParam(queryInfoVO.getDasApiBasicInfo().getApiPath(), request))
                .headers(headers)
                .params(param)
                .body(bodyData)
                .method(RequestMethod.GET)
                .build());
    return DefaultCommonResult.success(ResultCodeEnum.OK, data, TIPS);
  }

  @DataServiceMapping(value = DataServiceEnum.MATCH_ALL, method = RequestMethod.PUT)
  public DefaultCommonResult<Object> putHandler(
      HttpServletRequest request,
      @RequestParam(required = false) Map<String, Object> param,
      @RequestBody(required = false) Object bodyData,
      @RequestHeader(required = false) HttpHeaders headers) {
    DataQueryInfoVO queryInfoVO = matchDataQueryInfo(request);
    Object data =
        restHandler(
            queryInfoVO,
            HttpDataParamModel.builder()
                .uriPathParam(
                    getUriPathParam(queryInfoVO.getDasApiBasicInfo().getApiPath(), request))
                .headers(headers)
                .params(param)
                .body(bodyData)
                .method(RequestMethod.GET)
                .build());
    return DefaultCommonResult.success(ResultCodeEnum.OK, data, TIPS);
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
   * @param queryInfoVO 请求 queryInfoVO
   * @param httpDataParamModel http传递的参数集合
   */
  protected abstract Object restHandler(
      DataQueryInfoVO queryInfoVO, HttpDataParamModel httpDataParamModel);
}
