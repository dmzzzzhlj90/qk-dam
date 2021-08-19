package com.qk.dm.dataservice.controller;

import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataservice.service.DasSyncApiGatewayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 定时调度_同步数据服务API至服务网关
 *
 * @author wjq
 * @date 20210819
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/sync/gate_way")
public class DasSyncApiGatewayController {
  private final DasSyncApiGatewayService dasSyncApiGatewayService;

  @Autowired
  public DasSyncApiGatewayController(DasSyncApiGatewayService dasSyncApiGatewayService) {
    this.dasSyncApiGatewayService = dasSyncApiGatewayService;
  }

  /**
   * 同步数据服务API至API_SIX
   *
   * @return DefaultCommonResult
   */
  @PostMapping("/api_six/routes")
  public DefaultCommonResult syncApiSixRoutes() {
    dasSyncApiGatewayService.syncApiSixRoutes();
    return DefaultCommonResult.success();
  }
}
