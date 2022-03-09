package com.qk.dm.authority.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.authority.service.UserEmpowerService;
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
public class UserEmpowerController {
private final UserEmpowerService userEmpowerService;

  public UserEmpowerController(UserEmpowerService userEmpowerService) {
    this.userEmpowerService = userEmpowerService;
  }

  /**
   *根据用户id查询服务信息
   * @param realm
   * @param userId
   * @return
   */
  @GetMapping("/{userId}")
  public DefaultCommonResult<List<ServiceVO>> queryServicesByUserId(@Valid @NotBlank String realm, @PathVariable String userId){
    return DefaultCommonResult.success(ResultCodeEnum.OK,userEmpowerService.queryServicesByUserId(realm,userId));
  }
  /**
   *根据用户id和服务uuid查询授权信息
   * @param serviceId
   * @param userId
   * @return
   */
  @GetMapping("")
  public DefaultCommonResult<List<String>> queryEmpower(@Valid @NotBlank String realm ,@Valid String serviceId, @Valid @NotBlank String userId){
    return DefaultCommonResult.success(ResultCodeEnum.OK,userEmpowerService.queryEmpower(realm,serviceId,userId));
  }


}