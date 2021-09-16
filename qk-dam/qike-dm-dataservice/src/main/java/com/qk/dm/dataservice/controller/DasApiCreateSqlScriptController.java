package com.qk.dm.dataservice.controller;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataservice.service.DasApiCreateSqlScriptService;
import com.qk.dm.dataservice.vo.DasApiCreateSqlScriptVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 数据服务_新建API_脚本方式
 *
 * @author wjq
 * @date 20210830
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/create/sqlScript")
public class DasApiCreateSqlScriptController {
  private final DasApiCreateSqlScriptService dasApiCreateSqlScriptService;

  @Autowired
  public DasApiCreateSqlScriptController(
      DasApiCreateSqlScriptService dasApiCreateSqlScriptService) {
    this.dasApiCreateSqlScriptService = dasApiCreateSqlScriptService;
  }

  /**
   * 新增API脚本方式_详情展示
   *
   * @param apiId
   * @return DefaultCommonResult<PageResultVO < DasApiCreateVO>>
   */
  @GetMapping(value = "/query/{apiId}")
  public DefaultCommonResult<DasApiCreateSqlScriptVO> getDasApiCreateSqlScriptInfoByApiId(
      @PathVariable("apiId") String apiId) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, dasApiCreateSqlScriptService.getDasApiCreateSqlScriptInfoByApiId(apiId));
  }

  /**
   * 新增API脚本方式
   *
   * @param dasApiCreateSqlScriptVO
   * @return DefaultCommonResult
   */
  @PostMapping("/add")
  public DefaultCommonResult addDasApiCreateSqlScript(
      @RequestBody @Validated DasApiCreateSqlScriptVO dasApiCreateSqlScriptVO) {
    dasApiCreateSqlScriptService.addDasApiCreateSqlScript(dasApiCreateSqlScriptVO);
    return DefaultCommonResult.success();
  }

  /**
   * 编辑API脚本方式
   *
   * @param dasApiCreateSqlScriptVO
   * @return DefaultCommonResult
   */
  @PutMapping("/update")
  public DefaultCommonResult updateDasApiCreateSqlScript(
      @RequestBody @Validated DasApiCreateSqlScriptVO dasApiCreateSqlScriptVO) {
    dasApiCreateSqlScriptService.updateDasApiCreateSqlScript(dasApiCreateSqlScriptVO);
    return DefaultCommonResult.success();
  }
}
