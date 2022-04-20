package com.qk.dm.dataservice.rest.model;


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
public class HttpDataParam {
    Map<String,Object> requestParam;
    Object requestBodyData;
    HttpHeaders headerParam;
    RequestMethod requestMethod;
    String uriRouteCode;
}
