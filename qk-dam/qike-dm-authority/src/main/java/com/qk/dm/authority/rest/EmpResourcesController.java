package com.qk.dm.authority.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.authority.service.EmpRsService;
import com.qk.dm.authority.vo.params.ApiResourcesParamVO;
import com.qk.dm.authority.vo.params.ResourceParamVO;
import com.qk.dm.authority.vo.powervo.ResourceOutVO;
import com.qk.dm.authority.vo.powervo.ResourceVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 授权管理-资源（API）
 * @author zys
 * @date 2022/2/24 11:27
 * @since 1.0.0
 */
@RestController
@RequestMapping("/resource")
public class EmpResourcesController {
  private final EmpRsService empRsService;

  public EmpResourcesController(EmpRsService empRsService) {
    this.empRsService = empRsService;
  }

  /**
   * 新增资源（API）
   * @param resourceVO 新增资源（api）信息
   * @return
   */
  @PostMapping("")
  public DefaultCommonResult addResource(@Validated @RequestBody ResourceVO resourceVO){
    empRsService.addResource(resourceVO);
    return DefaultCommonResult.success();
  }

  /**
   * 编辑资源
   * @param resourceVO 编辑资源（api）信息
   * @return
   */
  @PutMapping("")
  public DefaultCommonResult updateResource(@Validated @RequestBody ResourceVO resourceVO){
    empRsService.updateResource(resourceVO);
    return DefaultCommonResult.success();
  }

  /**
   * 删除资源
   * @param id 资源（api）id
   * @return
   */
  @DeleteMapping("/{id}")
  public DefaultCommonResult deleteResource(@NotNull @PathVariable("id") Long id){
    empRsService.deleteResource(id);
    return DefaultCommonResult.success();
  }

  /**
   * 根据服务id查询资源
   * @param resourceParamVO 查询资源条件
   * @return DefaultCommonResult<List<ResourceOutVO>> 资源信息
   */
  @PostMapping("/rs")
  public DefaultCommonResult<List<ResourceOutVO>> queryResource(@RequestBody ResourceParamVO resourceParamVO){
    return DefaultCommonResult.success(ResultCodeEnum.OK,empRsService.queryResource(resourceParamVO));
  }

  /**
   * 根据服务id查询API
   * @param apiResourcesParamVO 查询api条件
   * @return DefaultCommonResult<List<ResourceVO>> api信息
   */
  @PostMapping("/api")
  public DefaultCommonResult<List<ResourceVO>> queryResourceApi(@RequestBody ApiResourcesParamVO apiResourcesParamVO){
    return DefaultCommonResult.success(ResultCodeEnum.OK,empRsService.queryResourceApi(apiResourcesParamVO));
  }

  /**
   * 根据服务id和资源标识查询资源信息
   * @param powerResourcesParamVO
   * @return
   */
  /*@PostMapping("/authorized")
  public DefaultCommonResult<List<ResourceVO>> queryauthorized(@RequestBody PowerResourcesParamVO powerResourcesParamVO){
    return DefaultCommonResult.success(ResultCodeEnum.OK,powerResourcesService.queryauthorized(powerResourcesParamVO));
  }*/

}