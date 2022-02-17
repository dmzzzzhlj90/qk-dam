package com.qk.dm.dataservice.controller;

//import com.qk.dam.authorization.Auth;
//import com.qk.dam.authorization.BizResource;
//import com.qk.dam.authorization.RestActionType;
import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataservice.service.DasSyncOpenApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * OPEN-API同步至数据服务操作
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
   * 同步Swagger至数据服务注册API
   *
   * @return DefaultCommonResult
   */
  @GetMapping("/register")
//  @Auth(bizType = BizResource.DAS_OPEN_API, actionType = RestActionType.CREATE)
  public DefaultCommonResult syncRegister() {
    return DefaultCommonResult.success(ResultCodeEnum.OK, dasSyncOpenApiService.syncRegister());
  }
}
