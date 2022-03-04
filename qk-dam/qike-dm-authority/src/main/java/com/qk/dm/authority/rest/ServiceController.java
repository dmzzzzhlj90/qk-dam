package com.qk.dm.authority.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.authority.service.PowerService;
import com.qk.dm.authority.vo.params.ServiceParamVO;
import com.qk.dm.authority.vo.powervo.ServiceVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**授权管理—服务
 * @author zys
 * @date 2022/2/24 11:26
 * @since 1.0.0
 */
@RestController
@RequestMapping("/service")
public class ServiceController {
  private final PowerService powerService;

  public ServiceController(PowerService powerService) {
    this.powerService = powerService;
  }

  /**
   * 新增服务
   * @param serviceVO
   * @return
   */
  @PostMapping
  public DefaultCommonResult addService(@Validated @RequestBody ServiceVO serviceVO){
    powerService.addService(serviceVO);
    return DefaultCommonResult.success();
  }

  /**
   * 服务详情
   * @param id
   * @return
   */
  @GetMapping("/{id}")
  public DefaultCommonResult<ServiceVO> ServiceDetails(@NotNull @PathVariable("id") Long id){
    return DefaultCommonResult.success(ResultCodeEnum.OK,powerService.ServiceDetails(id));
  }

  /**
   * 编辑服务
   * @param serviceVO
   * @return
   */
  @PutMapping("")
  public DefaultCommonResult updateService(@Validated @RequestBody ServiceVO serviceVO){
    powerService.updateService(serviceVO);
    return DefaultCommonResult.success();
  }

  /**
   * 删除服务
   * @param id
   * @return
   */
  @DeleteMapping("/{id}")
  public DefaultCommonResult deleteService(@NotNull @PathVariable("id") Long id){
    powerService.deleteService(id);
    return DefaultCommonResult.success();
  }

  /**
   * 查询服务
   * @return
   */
  @PostMapping("/query")
  public DefaultCommonResult<List<ServiceVO>> queryServices(@RequestBody
      ServiceParamVO serviceParamVO){
    return DefaultCommonResult.success(ResultCodeEnum.OK,powerService.queryServices(serviceParamVO));
  }

}