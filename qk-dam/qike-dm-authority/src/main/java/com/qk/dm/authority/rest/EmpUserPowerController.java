package com.qk.dm.authority.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.authority.service.EmpUserPowerService;
import com.qk.dm.authority.vo.params.UserEmpParamVO;
import com.qk.dm.authority.vo.params.UserEmpPowerParamVO;
import com.qk.dm.authority.vo.powervo.ServiceQueryVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户权限
 * @author zys
 * @date 2022/3/9 10:17
 * @since 1.0.0
 */
@RestController
@RequestMapping("/userpower")
public class EmpUserPowerController {
private final EmpUserPowerService empUserPowerService;

  public EmpUserPowerController(EmpUserPowerService empUserPowerService) {
    this.empUserPowerService = empUserPowerService;
  }

  /**
   * 根据用户id查询服务信息
   * @param userEmpParamVO 根据用户id查询服务参数
   * @return DefaultCommonResult<List<ServiceVO>> 服务信息
   */
  @PostMapping("/service")
  public DefaultCommonResult<List<ServiceQueryVO>> queryServicesByUserId(@RequestBody @Validated UserEmpParamVO userEmpParamVO){
    return DefaultCommonResult.success(ResultCodeEnum.OK,empUserPowerService.queryServicesByUserId(userEmpParamVO));
  }

  /**
   * 根据用户id查询用户访问授权url
   * @param userEmpPowerParamVO 查询用户授权url参数
   * @return DefaultCommonResult<List<String>> 用户授权API的url
   */
  @PostMapping("/api")
  public DefaultCommonResult<List<String>> queryEmpowerApi(@RequestBody @Validated
      UserEmpPowerParamVO userEmpPowerParamVO){
    return DefaultCommonResult.success(ResultCodeEnum.OK,empUserPowerService.queryEmpower(userEmpPowerParamVO));
  }

  /**
   * 根据用户id查询用户资源授权url
   * @param userEmpPowerParamVO 查询用户授权url参数
   * @return DefaultCommonResult<List<EmpResourceUrlVO>> 用户授权服务资源的url
   */
 /* @PostMapping("/resource")
public DefaultCommonResult<List<EmpResourceUrlVO>> queryEmpowerResource(@RequestBody @Validated
    UserEmpPowerParamVO userEmpPowerParamVO){
  return DefaultCommonResult.success(ResultCodeEnum.OK,empUserPowerService.queryEmpowerResource(userEmpPowerParamVO));
}*/
}