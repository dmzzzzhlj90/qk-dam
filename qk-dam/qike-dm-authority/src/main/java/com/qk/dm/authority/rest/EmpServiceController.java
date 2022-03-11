package com.qk.dm.authority.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.authority.service.EmpSvcService;
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
public class EmpServiceController {
  private final EmpSvcService empSvcService;

  public EmpServiceController(EmpSvcService empSvcService) {
    this.empSvcService = empSvcService;
  }

  /**
   * 新增服务
   * @param serviceVO  服务信息
   * @return
   */
  @PostMapping
  public DefaultCommonResult addService(@Validated @RequestBody ServiceVO serviceVO){
    empSvcService.addService(serviceVO);
    return DefaultCommonResult.success();
  }

  /**
   * 服务详情
   * @param id 服务id
   * @return DefaultCommonResult<ServiceVO> 服务信息详情
   */
  @GetMapping("/{id}")
  public DefaultCommonResult<ServiceVO> ServiceDetails(@NotNull @PathVariable("id") Long id){
    return DefaultCommonResult.success(ResultCodeEnum.OK,empSvcService.ServiceDetails(id));
  }

  /**
   * 编辑服务
   * @param serviceVO 服务信息
   * @return
   */
  @PutMapping("")
  public DefaultCommonResult updateService(@Validated @RequestBody ServiceVO serviceVO){
    empSvcService.updateService(serviceVO);
    return DefaultCommonResult.success();
  }

  /**
   * 删除服务
   * @param id 服务id
   * @return
   */
  @DeleteMapping("/{id}")
  public DefaultCommonResult deleteService(@NotNull @PathVariable("id") Long id){
    empSvcService.deleteService(id);
    return DefaultCommonResult.success();
  }

  /**
   * 查询服务
   * @param serviceParamVO 查询服务信息参数
   * @return DefaultCommonResult<List<ServiceVO>> 查询服务信息
   */
  @PostMapping("/query")
  public DefaultCommonResult<List<ServiceVO>> queryServices(@RequestBody
      ServiceParamVO serviceParamVO){
    return DefaultCommonResult.success(ResultCodeEnum.OK,empSvcService.queryServices(serviceParamVO));
  }

}