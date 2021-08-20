package com.qk.dm.dataservice.controller;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataservice.service.DasApiRegisterService;
import com.qk.dm.dataservice.vo.DasApiRegisterVO;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 数据服务_注册API
 *
 * @author wjq
 * @date 20210817
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/register")
public class DasApiRegisterController {
  private final DasApiRegisterService dasApiRegisterService;

  @Autowired
  public DasApiRegisterController(DasApiRegisterService dasApiRegisterService) {
    this.dasApiRegisterService = dasApiRegisterService;
  }

  /**
   * 注册API_详情展示
   *
   * @param apiId
   * @return DefaultCommonResult<PageResultVO < DasApiRegisterVO>>
   */
  @GetMapping(value = "/query/by/{apiId}")
  public DefaultCommonResult<DasApiRegisterVO> getDasApiRegisterInfoByApiId(
      @PathVariable("apiId") String apiId) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, dasApiRegisterService.getDasApiRegisterInfoByApiId(apiId));
  }

  /**
   * 新增API基础信息
   *
   * @param dasApiRegisterVO
   * @return DefaultCommonResult
   */
  @PostMapping("/add")
  public DefaultCommonResult addDasApiRegister(
      @RequestBody @Validated DasApiRegisterVO dasApiRegisterVO) {
    dasApiRegisterService.addDasApiRegister(dasApiRegisterVO);
    return DefaultCommonResult.success();
  }

  /**
   * 编辑API基础信息
   *
   * @param dasApiRegisterVO
   * @return DefaultCommonResult
   */
  @PutMapping("/update")
  public DefaultCommonResult updateDasApiRegister(
      @RequestBody @Validated DasApiRegisterVO dasApiRegisterVO) {
    dasApiRegisterService.updateDasApiRegister(dasApiRegisterVO);
    return DefaultCommonResult.success();
  }

  /**
   * 注册API__后端服务参数表头
   *
   * @return DefaultCommonResult
   */
  @GetMapping("/query/backend/paras/header/infos")
  public DefaultCommonResult<Map<String, String>> getBackendParaHeaderInfo() {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, dasApiRegisterService.getBackendParaHeaderInfo());
  }

  /**
   * 注册API__常量参数表头
   *
   * @return DefaultCommonResult
   */
  @GetMapping("/query/constant/paras/header/infos")
  public DefaultCommonResult<Map<String, String>> getConstantParaHeaderInfo() {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, dasApiRegisterService.getConstantParaHeaderInfo());
  }
}
