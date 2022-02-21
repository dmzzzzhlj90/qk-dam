package com.qk.dm.dataservice.controller;

//import com.qk.dam.authorization.Auth;
//import com.qk.dam.authorization.BizResource;
//import com.qk.dam.authorization.RestActionType;
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
  @GetMapping(value = "/{apiId}")
//  @Auth(bizType = BizResource.DAS_API_CREATE_SQL_SCRIPT, actionType = RestActionType.DETAIL)
  public DefaultCommonResult<DasApiCreateSqlScriptVO> detail(@PathVariable("apiId") String apiId) {
    return DefaultCommonResult.success(ResultCodeEnum.OK, dasApiCreateSqlScriptService.detail(apiId));
  }

  /**
   * 新增API脚本方式
   *
   * @param dasApiCreateSqlScriptVO
   * @return DefaultCommonResult
   */
  @PostMapping("")
//  @Auth(bizType = BizResource.DAS_API_CREATE_SQL_SCRIPT, actionType = RestActionType.CREATE)
  public DefaultCommonResult insert(@RequestBody @Validated DasApiCreateSqlScriptVO dasApiCreateSqlScriptVO) {
    dasApiCreateSqlScriptService.insert(dasApiCreateSqlScriptVO);
    return DefaultCommonResult.success();
  }

  /**
   * 编辑API脚本方式
   *
   * @param dasApiCreateSqlScriptVO
   * @return DefaultCommonResult
   */
  @PutMapping("")
//  @Auth(bizType = BizResource.DAS_API_CREATE_SQL_SCRIPT, actionType = RestActionType.UPDATE)
  public DefaultCommonResult update(@RequestBody @Validated DasApiCreateSqlScriptVO dasApiCreateSqlScriptVO) {
    dasApiCreateSqlScriptService.update(dasApiCreateSqlScriptVO);
    return DefaultCommonResult.success();
  }
}
