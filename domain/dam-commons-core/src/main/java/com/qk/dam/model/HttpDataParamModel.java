package com.qk.dam.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * @author zhudaoming
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HttpDataParamModel {
    String apiId;
    Map<String,Object> params;
    Map<String,String> uriPathParam;
    Object body;
    HttpHeaders headers;
    RequestMethod method;

    @Override
    public String toString() {
        return "{" +
                "apiId='" + apiId + '\'' +
                (ObjectUtils.isEmpty(params)?", params=" + params:"")+
                (ObjectUtils.isEmpty(uriPathParam)?", uriPathParam=" + uriPathParam:"")+
                (ObjectUtils.isEmpty(body)?", body=" + body:"")+
                (ObjectUtils.isEmpty(method)?", method=" + method:"")+
                '}';
    }
}