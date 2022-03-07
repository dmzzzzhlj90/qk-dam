package com.qk.dm.authority.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.authority.service.PowerResourcesService;
import com.qk.dm.authority.vo.params.ApiResourcesParamVO;
import com.qk.dm.authority.vo.params.PowerResourcesParamVO;
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
public class ResourcesController {
  private final PowerResourcesService powerResourcesService;

  public ResourcesController(PowerResourcesService powerResourcesService) {
    this.powerResourcesService = powerResourcesService;
  }

  /**
   * 新增资源（API）
   * @param resourceVO
   * @return
   */
  @PostMapping
  public DefaultCommonResult addResource(@Validated @RequestBody ResourceVO resourceVO){
    powerResourcesService.addResource(resourceVO);
    return DefaultCommonResult.success();
  }

  /**
   * 编辑资源
   * @param resourceVO
   * @return
   */
  @PutMapping("")
  public DefaultCommonResult updateResource(@Validated @RequestBody ResourceVO resourceVO){
    powerResourcesService.updateResource(resourceVO);
    return DefaultCommonResult.success();
  }

  /**
   * 删除资源
   * @param id
   * @return
   */
  @DeleteMapping("/{id}")
  public DefaultCommonResult deleteResource(@NotNull @PathVariable("id") Long id){
    powerResourcesService.deleteResource(id);
    return DefaultCommonResult.success();
  }

  /**
   * 根据服务id查询资源
   * @param resourceParamVO
   * @return
   */
  @PostMapping("/query")
  public DefaultCommonResult<List<ResourceOutVO>> queryResource(@RequestBody ResourceParamVO resourceParamVO){
    return DefaultCommonResult.success(ResultCodeEnum.OK,powerResourcesService.queryResource(resourceParamVO));
  }

  /**
   * 根据服务id查询API
   * @param apiResourcesParamVO
   * @return
   */
  @PostMapping("/api")
  public DefaultCommonResult<List<ResourceVO>> queryResourceApi(@RequestBody ApiResourcesParamVO apiResourcesParamVO){
    return DefaultCommonResult.success(ResultCodeEnum.OK,powerResourcesService.queryResourceApi(apiResourcesParamVO));
  }

  /**
   * 根据服务id和资源标识查询资源信息
   * @param powerResourcesParamVO
   * @return
   */
  @PostMapping("/authorized")
  public DefaultCommonResult<List<ResourceVO>> queryauthorized(@RequestBody PowerResourcesParamVO powerResourcesParamVO){
    return DefaultCommonResult.success(ResultCodeEnum.OK,powerResourcesService.queryauthorized(powerResourcesParamVO));
  }

}