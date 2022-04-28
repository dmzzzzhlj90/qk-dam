package com.qk.dm.dataservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.model.HttpDataParamModel;
import com.qk.dm.dataquery.feign.DataBackendQueryFeign;
import com.qk.dm.dataservice.controller.base.BaseRestController;
import com.qk.dm.dataservice.mapping.DataServiceMapping;
import com.qk.dm.dataservice.rest.mapping.DataServiceEnum;
import com.qk.dm.dataservice.service.DataBackendQuerySerivce;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 数据查询服务接口层
 *
 * @author zhudaoming
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class DataServiceRestRestController extends BaseRestController {

  final DataBackendQuerySerivce dataBackendQuery;

  @DataServiceMapping(value = DataServiceEnum.MATCH_ALL, method = RequestMethod.GET)
  public DefaultCommonResult<Object> getHandler(
          HttpServletRequest request,
          @RequestParam(required = false) Map<String, Object> param,
          @RequestBody(required = false) Object bodyData,
          @RequestHeader(required = false) HttpHeaders headers) {
    HttpDataParamModel httpDataParamModel = restModel(request, param, bodyData, headers);

    return DefaultCommonResult.success(ResultCodeEnum.OK,
            dataBackendQuery.dataBackendQuery(httpDataParamModel)
            , TIPS);
  }

  @DataServiceMapping(value = DataServiceEnum.MATCH_ALL, method = RequestMethod.POST)
  public DefaultCommonResult<Object> postHandler(
          HttpServletRequest request,
          @RequestParam(required = false) Map<String, Object> param,
          @RequestBody(required = false) Object bodyData,
          @RequestHeader(required = false) HttpHeaders headers) {
    HttpDataParamModel httpDataParamModel = restModel(request, param, bodyData, headers);

    return DefaultCommonResult.success(ResultCodeEnum.OK,
            dataBackendQuery.dataBackendQuery(httpDataParamModel)
            , TIPS);
  }

  @DataServiceMapping(value = DataServiceEnum.MATCH_ALL, method = RequestMethod.PUT)
  public DefaultCommonResult<Object> putHandler(
          HttpServletRequest request,
          @RequestParam(required = false) Map<String, Object> param,
          @RequestBody(required = false) Object bodyData,
          @RequestHeader(required = false) HttpHeaders headers) {
    HttpDataParamModel httpDataParamModel = restModel(request, param, bodyData, headers);
    return DefaultCommonResult.success(ResultCodeEnum.OK,
            dataBackendQuery.dataBackendQuery(httpDataParamModel), TIPS);
  }
}
