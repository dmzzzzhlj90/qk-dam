package com.qk.dm.dataservice.controller;

import com.qk.dm.dataservice.service.DasGenerateOpenApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 根据数据服务API生成OpenApiJson
 *
 * @author wjq
 * @date 20210901
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/generate/open/api/")
public class DasGenerateOpenApiController {
    private final DasGenerateOpenApiService dasGenerateOpenApiService;

    @Autowired
    public DasGenerateOpenApiController(DasGenerateOpenApiService dasGenerateOpenApiService) {
        this.dasGenerateOpenApiService = dasGenerateOpenApiService;
    }


    /**
     * 根据数据服务注册API,生成OpenApiJson
     *
     * @return DefaultCommonResult
     */
    @GetMapping(value = "/register",produces = "application/json;charset=utf-8")
    public String generateOpenApiRegister() {
        return dasGenerateOpenApiService.generateOpenApiRegister();
    }


}
