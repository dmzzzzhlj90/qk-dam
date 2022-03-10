package com.qk.dm.authority.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.authority.service.PowerEmpowerService;
import com.qk.dm.authority.vo.params.EmpowerParamVO;
import com.qk.dm.authority.vo.powervo.EmpowerAllVO;
import com.qk.dm.authority.vo.powervo.EmpowerVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 授权管理-授权信息
 * @author zys
 * @date 2022/2/24 11:29
 * @since 1.0.0
 */
@RestController
@RequestMapping("/empower")
public class EmpowerController {
  private final PowerEmpowerService powerEmpowerService;

  public EmpowerController(PowerEmpowerService powerEmpowerService) {
    this.powerEmpowerService = powerEmpowerService;
  }

  /**
   * 新增授权
   * @param empowerVO
   * @return
   */
  @PostMapping
  public DefaultCommonResult addEmpower(@Validated @RequestBody EmpowerVO empowerVO){
    powerEmpowerService.addEmpower(empowerVO);
    return DefaultCommonResult.success();
  }

  /**
   * 授权详情
   * @param id
   * @return
   */
  @GetMapping("/{id}")
  public DefaultCommonResult<EmpowerVO> EmpowerDetails(@NotNull @PathVariable("id") Long id){
    return DefaultCommonResult.success(ResultCodeEnum.OK,powerEmpowerService.EmpowerDetails(id));
  }

  /**
   * 编辑授权
   * @param empowerVO
   * @return
   */
  @PutMapping("")
  public DefaultCommonResult updateEmpower(@Validated @RequestBody EmpowerVO empowerVO){
    powerEmpowerService.updateEmpower(empowerVO);
    return DefaultCommonResult.success();
  }

  /**
   * 删除授权
   * @param id
   * @return
   */
  @DeleteMapping("/{id}")
  public DefaultCommonResult deleteEmpower(@NotNull @PathVariable("id") Long id){
    powerEmpowerService.deleteEmpower(id);
    return DefaultCommonResult.success();
  }

  /**
   * 查询授权信息
   * @return
   */
  @PostMapping("/query")
  public DefaultCommonResult<PageResultVO<EmpowerVO>> queryEmpower(@RequestBody EmpowerParamVO empowerParamVO){
    return DefaultCommonResult.success(ResultCodeEnum.OK,powerEmpowerService.queryEmpower(empowerParamVO));
  }

  /**
   * 用户、分组、角色授权信息查询
   * @return
   */
  @GetMapping("/allempowers")
  public DefaultCommonResult<List<EmpowerAllVO>> queryAllEmpower(@Valid @NotBlank String id){
    return DefaultCommonResult.success(ResultCodeEnum.OK,powerEmpowerService.queryAllEmpower(id));
  }
}