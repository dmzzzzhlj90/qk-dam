package com.qk.dm.authority.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.authority.service.EmpRsApiService;
import com.qk.dm.authority.vo.params.ApiPageResourcesParamVO;
import com.qk.dm.authority.vo.params.ApiResourcesParamVO;
import com.qk.dm.authority.vo.powervo.ResourceApiVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 授权管理-API
 * @author zys
 * @date 2022/2/24 11:27
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api")
public class EmpRsApiController {
  private final EmpRsApiService empRsApiService;

  public EmpRsApiController(EmpRsApiService empRsApiService) {
    this.empRsApiService = empRsApiService;
  }

  /**
   * 新增API
   * @param resourceApiVO 新增资源api信息
   * @return
   */
  @PostMapping("")
  public DefaultCommonResult addApiResource(@Validated @RequestBody ResourceApiVO resourceApiVO){
    empRsApiService.addApiResource(resourceApiVO);
    return DefaultCommonResult.success();
  }

  /**
   * 编辑API
   * @param resourceApiVO 编辑资源api信息
   * @return
   */
  @PutMapping("")
  public DefaultCommonResult updateApiResource(@Validated @RequestBody ResourceApiVO resourceApiVO){
    empRsApiService.updateApiResource(resourceApiVO);
    return DefaultCommonResult.success();
  }

  /**
   * 删除API
   * @param ids api的id的字符串用“,”分隔
   * @return
   */
  @DeleteMapping("/{ids}")
  public DefaultCommonResult deleteApiResource(@NotNull @PathVariable("ids") String ids){
    empRsApiService.deleteApiResource(ids);
    return DefaultCommonResult.success();
  }


  /**
   * 根据服务UUID查询API（不分页-新增授权使用）
   * @param apiResourcesParamVO 查询api条件
   * @return DefaultCommonResult<List<ResourceVO>> api信息
   */
  @PostMapping("/query")
  public DefaultCommonResult<List<ResourceApiVO>> queryApiResource(@RequestBody ApiResourcesParamVO apiResourcesParamVO){
    return DefaultCommonResult.success(ResultCodeEnum.OK,empRsApiService.queryApiResource(apiResourcesParamVO));
  }


  /**
   * 根据服务UUID、api名称查询api信息--分页查询
   * @param apiPageResourcesParamVO 分页查询api信息条件
   * @return DefaultCommonResult<PageResultVO<ResourceVO>> 分页api信息
   */
  @PostMapping("/query/page")
  public DefaultCommonResult<PageResultVO<ResourceApiVO>> queryApiPageEmpower(@RequestBody ApiPageResourcesParamVO apiPageResourcesParamVO){
    return DefaultCommonResult.success(ResultCodeEnum.OK,empRsApiService.queryApiPageEmpower(apiPageResourcesParamVO));
  }


  /**
   * 查询删除的资源是否已经授权
   * @param ids 资源（api）id的字符串用“,”分隔
   * @return DefaultCommonResult<Boolean> 返回值为true表示存在为false表示不存在
   */
  @GetMapping("/query/{ids}")
  public DefaultCommonResult<Boolean> qeryApiRsEmp(@NotNull @PathVariable("ids") String ids){
    return DefaultCommonResult.success(ResultCodeEnum.OK,empRsApiService.qeryApiRsEmp(ids));
  }
}