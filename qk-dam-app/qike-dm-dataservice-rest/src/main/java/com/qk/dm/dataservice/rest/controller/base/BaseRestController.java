package com.qk.dm.dataservice.rest.controller.base;

import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.model.HttpDataParamModel;
import com.qk.dm.dataservice.mapping.DataServiceMapping;
import com.qk.dm.dataservice.rest.mapping.DataServiceEnum;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.PathContainer;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author zhudaoming
 */
@RestController
public abstract class BaseRestController {
    private static final PathPatternParser PATH_PATTERN_PARSER = new PathPatternParser();

        private static final Pattern PATTERN_PATH_VAR = Pattern.compile("\\{([^}])*}");

        private static final Hasher HASHER_SHA256 = Hashing.sha256().newHasher();
        private static final Base64.Encoder BASE64 = Base64.getEncoder();
    public static final String TIPS = "数据查询服务返回查询结果！";

    @DataServiceMapping(value = DataServiceEnum.MATCH_ALL,method = RequestMethod.GET)
        public DefaultCommonResult<Object> getHandler(HttpServletRequest request,
                                                                          @RequestParam(required = false) Map<String,Object> param,
                                                                          @RequestBody(required = false) Object bodyData,
                                                                          @RequestHeader(required = false) HttpHeaders headers
        ){
            Object data = restHandler(request, HttpDataParamModel.builder()
                    .headers(headers)
                    .params(param)
                    .body(bodyData)
                    .method(RequestMethod.GET)
                    
                    .build());
            return  DefaultCommonResult.success(ResultCodeEnum.OK,data, TIPS);

        }

        @DataServiceMapping(value = DataServiceEnum.MATCH_ALL,method = RequestMethod.POST)
        public DefaultCommonResult<Object> postHandler(HttpServletRequest request,
                                  @RequestParam(required = false) Map<String,Object> param,
                                  @RequestBody(required = false) Object bodyData,
                                  @RequestHeader(required = false) HttpHeaders headers){
            Object data = restHandler(request, HttpDataParamModel.builder()
                    .headers(headers)
                    .params(param)
                    .body(bodyData)
                    .method(RequestMethod.GET)
                    
                    .build());
            return  DefaultCommonResult.success(ResultCodeEnum.OK,data, TIPS);
        }

        @DataServiceMapping(value = DataServiceEnum.MATCH_ALL,method = RequestMethod.PUT)
        public DefaultCommonResult<Object> putHandler(HttpServletRequest request,
                                 @RequestParam(required = false) Map<String,Object> param,
                                 @RequestBody(required = false) Object bodyData,
                                 @RequestHeader(required = false) HttpHeaders headers
        ){
            Object data = restHandler(request, HttpDataParamModel.builder()
                    .headers(headers)
                    .params(param)
                    .body(bodyData)
                    .method(RequestMethod.GET)
                    
                    .build());
            return  DefaultCommonResult.success(ResultCodeEnum.OK,data, TIPS);
        }

        @DataServiceMapping(value = DataServiceEnum.MATCH_ALL,method = RequestMethod.DELETE)
        public DefaultCommonResult<Object> deleteHandler(HttpServletRequest request,
                                    @RequestParam(required = false) Map<String,Object> param,
                                    @RequestBody(required = false) Object bodyData,
                                    @RequestHeader(required = false) HttpHeaders headers
        ){
            Object data = restHandler(request, HttpDataParamModel.builder()
                    .headers(headers)
                    .params(param)
                    .body(bodyData)
                    .method(RequestMethod.GET)
                    
                    .build());
            return  DefaultCommonResult.success(ResultCodeEnum.OK,data, TIPS);
        }

        @DataServiceMapping(value = DataServiceEnum.MATCH_ALL,method = RequestMethod.PATCH)
        public DefaultCommonResult<Object> patchHandler(HttpServletRequest request,
                                   @RequestParam(required = false) Map<String,Object> param,
                                   @RequestBody(required = false) Object bodyData,
                                   @RequestHeader(required = false) HttpHeaders headers)
        {
            return DefaultCommonResult.success(ResultCodeEnum.OK,restHandler(request, HttpDataParamModel.builder()
                    .headers(headers)
                    .params(param)
                    .body(bodyData)
                    .method(RequestMethod.GET)
                    
                    .build()));
        }

        @DataServiceMapping(value = DataServiceEnum.MATCH_ALL,method = RequestMethod.HEAD)
        public DefaultCommonResult<Object> headHandler(HttpServletRequest request,
                                  @RequestParam(required = false) Map<String,Object> param,
                                  @RequestBody(required = false) Object bodyData,
                                  @RequestHeader(required = false) HttpHeaders headers
        ){
            return DefaultCommonResult.success(ResultCodeEnum.OK,restHandler(request, HttpDataParamModel.builder()
                    .headers(headers)
                    .params(param)
                    .body(bodyData)
                    .method(RequestMethod.GET)
                    
                    .build()));
        }

        @DataServiceMapping(value = DataServiceEnum.MATCH_ALL,method = RequestMethod.OPTIONS)
        public DefaultCommonResult<Object> optionsHandler(HttpServletRequest request,
                                     @RequestParam(required = false) Map<String,Object> param,
                                     @RequestBody(required = false) Object bodyData,
                                     @RequestHeader(required = false) HttpHeaders headers
        )
        {
            return DefaultCommonResult.success(ResultCodeEnum.OK,restHandler(request, HttpDataParamModel.builder()
                    .headers(headers)
                    .params(param)
                    .body(bodyData)
                    .method(RequestMethod.GET)
                    
                    .build()));
        }

    /**
     * 获取path对应的参数变量
     *
     * @param pathPattern path表达式
     * @param request 请求
     * @return Map<String, String> 变量结果
     */
    protected Map<String, String> getUriPathParam(String pathPattern,HttpServletRequest request){
        PathPattern.PathMatchInfo pathMatchInfo = PATH_PATTERN_PARSER.parse(pathPattern)
                .matchAndExtract(PathContainer.parsePath(request.getRequestURI()));
        if (Objects.nonNull(pathMatchInfo)){
            return pathMatchInfo.getUriVariables();
        }
        return Map.of();
    }

    public Boolean matchUriPath(String restPath,String pathPattern){
        return PATH_PATTERN_PARSER.parse(pathPattern).matches(PathContainer.parsePath(restPath));
    }

    protected String uriRouteCode(HttpServletRequest request){
        String rowPath = matchRowPath(request);
        //FIXME 是否使用sha不可逆处理?? return HASHER_SHA256.putString(rowPath, Charsets.UTF_8).hash().toString();
        return new String(BASE64.encode(rowPath.getBytes()));
    }

    private String matchRowPath(HttpServletRequest request) {
        return PATTERN_PATH_VAR.matcher(request.getMethod() + ":" + request.getRequestURI()).replaceAll("{}");
    }

    /**
     * 处理所有rest请求的handler
     *
     * @param request 请求 request
     * @param httpDataParamModel http传递的参数集合
     */

    protected abstract Object restHandler(HttpServletRequest request, HttpDataParamModel httpDataParamModel);
}
