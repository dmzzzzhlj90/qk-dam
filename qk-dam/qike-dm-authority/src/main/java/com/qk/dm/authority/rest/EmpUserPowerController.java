package com.qk.dm.authority.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.authority.service.EmpUserPowerService;
import com.qk.dm.authority.vo.powervo.ServiceVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
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
   * @param realm 域名称
   * @param userId 用户id
   * @param clientId 客户端id
   * @return DefaultCommonResult<List<ServiceVO>> 服务信息
   */
  @GetMapping("/{userId}")
  public DefaultCommonResult<List<ServiceVO>> queryServicesByUserId(@Valid @NotBlank String realm, @PathVariable String userId ,@Valid @NotBlank String clientId){
    return DefaultCommonResult.success(ResultCodeEnum.OK,empUserPowerService.queryServicesByUserId(realm,userId,clientId));
  }

  /**
   *
   * @param realm 域名称
   * @param serviceId 服务id
   * @param userId 用户id
   * @param clientId 客户端id
   * @return DefaultCommonResult<List<String>> 用户指定服务的授权url
   */
  @GetMapping("")
  public DefaultCommonResult<List<String>> queryEmpower(@Valid @NotBlank String realm ,@Valid String serviceId, @Valid @NotBlank String userId,@Valid @NotBlank String clientId){
    return DefaultCommonResult.success(ResultCodeEnum.OK,empUserPowerService.queryEmpower(realm,serviceId,userId,clientId));
  }


}