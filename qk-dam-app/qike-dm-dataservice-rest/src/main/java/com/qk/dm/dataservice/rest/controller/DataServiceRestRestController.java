package com.qk.dm.dataservice.rest.controller;

import com.qk.dm.dataservice.rest.controller.base.BaseRestController;
import com.qk.dm.dataservice.rest.model.HttpDataParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 数据查询服务接口层
 *
 * @author zhudaoming
 */
@Slf4j
@RestController
public class DataServiceRestRestController extends BaseRestController {

    /**
     * 处理所有rest请求的handler
     *
     * @param request 请求 request
     * @param httpDataParam http传递的参数集合
     */
    @Override
    protected void restHandler(HttpServletRequest request, HttpDataParam httpDataParam) {
        //TODO 1.通过app+method 获取所有配置的uri 2.拿到uri后获取参数 3.给配置的参数赋值
        log.info("httpDataParam:{}",httpDataParam);
        log.info("UriPathParam:{}",getUriPathParam("/ac/{a}/{b}",request));
    }


}
