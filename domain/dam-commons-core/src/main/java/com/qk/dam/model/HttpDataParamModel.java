package com.qk.dam.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
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
                ", params=" + params +
                ", uriPathParam=" + uriPathParam +
                ", body=" + body +
                ", method=" + method +
                '}';
    }
}