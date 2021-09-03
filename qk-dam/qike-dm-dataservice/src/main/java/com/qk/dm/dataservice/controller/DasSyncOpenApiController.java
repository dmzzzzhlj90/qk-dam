package com.qk.dm.dataservice.controller;

import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataservice.service.DasSyncOpenApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * OPEN-API同步操作
 *
 * @author wjq
 * @date 20210830
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/sync/open/api/")
public class DasSyncOpenApiController {
    private final DasSyncOpenApiService dasSyncOpenApiService;

    @Autowired
    public DasSyncOpenApiController(DasSyncOpenApiService dasSyncOpenApiService) {
        this.dasSyncOpenApiService = dasSyncOpenApiService;
    }

    /**
     * 同步Swagger注册Api
     *
     * @return DefaultCommonResult
     */
    @GetMapping("/register")
    public DefaultCommonResult syncRegister() {
        dasSyncOpenApiService.syncRegister();
        return DefaultCommonResult.success();
    }

}