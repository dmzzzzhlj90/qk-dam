package com.qk.dm.authority.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.authority.service.EmpPowerService;
import com.qk.dm.authority.vo.params.EmpowerParamVO;
import com.qk.dm.authority.vo.params.EmpowerQueryVO;
import com.qk.dm.authority.vo.powervo.EmpowerAllVO;
import com.qk.dm.authority.vo.powervo.EmpowerVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * 授权管理-授权信息
 * @author zys
 * @date 2022/2/24 11:29
 * @since 1.0.0
 */
@RestController
@RequestMapping("/empower")
public class EmpPowerController {
  private final EmpPowerService empPowerService;

  public EmpPowerController(EmpPowerService empPowerService) {
    this.empPowerService = empPowerService;
  }

  /**
   * 新增授权
   * @param empowerVO 新增授权信息
   * @return
   */
  @PostMapping
  public DefaultCommonResult addEmpower(@Validated @RequestBody EmpowerVO empowerVO){
    empPowerService.addEmpower(empowerVO);
    return DefaultCommonResult.success();
  }

  /**
   * 授权详情
   * @param id 授权信息id
   * @return DefaultCommonResult<EmpowerVO> 授权详情
   */
  @GetMapping("/{id}")
  public DefaultCommonResult<EmpowerVO> EmpowerDetails(@NotNull @PathVariable("id") Long id){
    return DefaultCommonResult.success(ResultCodeEnum.OK,empPowerService.EmpowerDetails(id));
  }

  /**
   * 编辑授权
   * @param empowerVO 修改授权信息
   * @return
   */
  @PutMapping("")
  public DefaultCommonResult updateEmpower(@Validated @RequestBody EmpowerVO empowerVO){
    empPowerService.updateEmpower(empowerVO);
    return DefaultCommonResult.success();
  }

  /**
   * 删除授权
   * @param id 被删授权信息id
   * @return
   */
  @DeleteMapping("/{id}")
  public DefaultCommonResult deleteEmpower(@NotNull @PathVariable("id") Long id){
    empPowerService.deleteEmpower(id);
    return DefaultCommonResult.success();
  }


  /**
   * 查询授权信息
   * @param empowerParamVO 查询授权信息参数
   * @return DefaultCommonResult<PageResultVO<EmpowerVO>> 授权信息
   */
  @PostMapping("/query")
  public DefaultCommonResult<PageResultVO<EmpowerVO>> queryEmpower(@RequestBody EmpowerParamVO empowerParamVO){
    return DefaultCommonResult.success(ResultCodeEnum.OK,empPowerService.queryEmpower(empowerParamVO));
  }

  /**
   *  用户、分组、角色(id)授权信息查询
   * @param empowerQueryVO （用户、分组、角色）id和分页信息查询授权信息
   * @return DefaultCommonResult<List<EmpowerAllVO>> 查询授权信息
   */
  @PostMapping("/allempowers")
  public DefaultCommonResult<PageResultVO<EmpowerAllVO>> queryAllEmpower(@Validated @RequestBody
      EmpowerQueryVO empowerQueryVO){
    return DefaultCommonResult.success(ResultCodeEnum.OK,empPowerService.queryAllEmpower(empowerQueryVO));
  }

  /**
   *  用户、分组、角色(id)授权信息删除
   * @param empid （用户、分组、角色）id删除授权信息
   * @return DefaultCommonResult<List<EmpowerAllVO>> 查询授权信息
   */
 /* @DeleteMapping("/delete/emp")
  public DefaultCommonResult deleteEmpPower( @Valid @NotBlank String empid){
    empPowerService.deleteEmpPower(empid);
    return DefaultCommonResult.success();
  }*/
}