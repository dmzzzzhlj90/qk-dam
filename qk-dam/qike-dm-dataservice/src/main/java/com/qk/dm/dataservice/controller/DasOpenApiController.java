package com.qk.dm.dataservice.controller;

import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataservice.service.DasOpenApiService;
import lombok.extern.slf4j.Slf4j;
import org.openapi4j.core.exception.ResolutionException;
import org.openapi4j.core.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

/**
 * OPEN-API同步操作
 *
 * @author wjq
 * @date 20210830
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/open/api/")
public class DasOpenApiController {
    private final DasOpenApiService dasOpenApiService;

    @Autowired
    public DasOpenApiController(DasOpenApiService dasOpenApiService) {
        this.dasOpenApiService = dasOpenApiService;
    }

    /**
     * 同步Swagger注册Api
     *
     * @return DefaultCommonResult
     */
    @PostMapping("/register")
    public DefaultCommonResult syncRegister()  {
        dasOpenApiService.syncRegister();
        return DefaultCommonResult.success();
    }

    /**
     * 同步torNa
     *
     * @return DefaultCommonResult
     */
    @PostMapping("/send/torNa/")
    public DefaultCommonResult sendApiToTorNaRest()  {
        dasOpenApiService.sendApiToTorNaRest();
        return DefaultCommonResult.success();
    }


}
