package com.qk.dam.model;

import com.qk.dam.jpa.pojo.Pagination;
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
    Map<String, Object> params;
    Map<String, String> uriPathParam;
    Object body;
    HttpHeaders headers;
    RequestMethod method;
    int cacheLevel;
    boolean pageFlag;
    Pagination pagination;


    @Override
    public String toString() {
        return "{" +
                "apiId='" + apiId + '\'' +
                (ObjectUtils.isEmpty(params) ? ", params=" + params : "") +
                (ObjectUtils.isEmpty(uriPathParam) ? ", uriPathParam=" + uriPathParam : "") +
                (ObjectUtils.isEmpty(body) ? ", body=" + body : "") +
                (ObjectUtils.isEmpty(method) ? ", method=" + method : "") +
                "cacheLevel='" + cacheLevel +
                "pageFlag='" + pageFlag +
                (ObjectUtils.isEmpty(pagination) ? ", pagination=" + pagination : "") +
                '}';
    }
}