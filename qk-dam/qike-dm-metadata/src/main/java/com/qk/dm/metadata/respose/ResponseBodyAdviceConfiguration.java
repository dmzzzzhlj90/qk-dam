package com.qk.dm.metadata.respose;


import com.google.gson.Gson;
import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 包装统一JSON返回结果(支持spring原生@ResponseBody的基础上补充返回code和data包装)
 * @author wangzp
 * @date 2021-07-30 15:12
 */
@Configuration
@ControllerAdvice
@ConditionalOnWebApplication
public class ResponseBodyAdviceConfiguration implements ResponseBodyAdvice<Object> {

  @Override
  public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
    return returnType.hasMethodAnnotation(ResponseWrapper.class);
  }

  @Override
  public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                ServerHttpRequest request, ServerHttpResponse response) {
    DefaultCommonResult result = new DefaultCommonResult(ResultCodeEnum.OK, body);
    if(body instanceof DefaultCommonResult) {
      return body;
    }else if(body instanceof String){
      Gson gson = new Gson();
      return gson.toJson(result);
    }
    return result;
  }
}
