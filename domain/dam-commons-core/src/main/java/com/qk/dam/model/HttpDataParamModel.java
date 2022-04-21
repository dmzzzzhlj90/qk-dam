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
    Object body;
    HttpHeaders headers;
    RequestMethod method;

}