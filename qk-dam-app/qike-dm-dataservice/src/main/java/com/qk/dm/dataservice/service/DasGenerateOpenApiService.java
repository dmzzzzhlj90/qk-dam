package com.qk.dm.dataservice.service;

import org.springframework.stereotype.Service;

/**
 * 根据数据服务API生成OpenApiJson
 *
 * @author wjq
 * @date 2021/8/30 17:47
 * @since 1.0.0
 */
@Service
public interface DasGenerateOpenApiService {

  String generateOpenApiRegister();
}
