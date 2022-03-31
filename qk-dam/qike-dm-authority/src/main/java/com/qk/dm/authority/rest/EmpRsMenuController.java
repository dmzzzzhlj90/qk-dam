package com.qk.dm.authority.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.authority.service.EmpRsMenuService;
import com.qk.dm.authority.vo.params.ResourceParamVO;
import com.qk.dm.authority.vo.powervo.ResourceMenuQueryVO;
import com.qk.dm.authority.vo.powervo.ResourceMenuVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 授权管理-资源
 * @author zys
 * @date 2022/2/24 11:27
 * @since 1.0.0
 */
@RestController
@RequestMapping("/resource")
public class EmpRsMenuController {
  private final EmpRsMenuService empRsMenuService;

  public EmpRsMenuController(EmpRsMenuService empRsMenuService) {
    this.empRsMenuService = empRsMenuService;
  }

  /**
   * 新增资源
   * @param resourceMenuVO 新增资源信息
   * @return
   */
  @PostMapping("")
  public DefaultCommonResult addResourceMenu(@Validated @RequestBody ResourceMenuVO resourceMenuVO){
    empRsMenuService.addResourceMenu(resourceMenuVO);
    return DefaultCommonResult.success();
  }

  /**
   * 编辑资源
   * @param resourceMenuVO 编辑资源信息
   * @return
   */
  @PutMapping("")
  public DefaultCommonResult updateResourceMenu(@Validated @RequestBody ResourceMenuVO resourceMenuVO){
    empRsMenuService.updateResourceMenu(resourceMenuVO);
    return DefaultCommonResult.success();
  }

  /**
   * 删除资源
   * @param id 资源id
   * @return
   */
  @DeleteMapping("/{id}")
  public DefaultCommonResult deleteResourceMenu(@NotNull @PathVariable("id") Long id){
    empRsMenuService.deleteResourceMenu(id);
    return DefaultCommonResult.success();
  }

  /**
   * 根据服务UUID查询资源
   * @param resourceParamVO 查询资源条件
   * @return DefaultCommonResult<List<ResourceOutVO>> 资源信息
   */
  @PostMapping("/rse")
  public DefaultCommonResult<List<ResourceMenuQueryVO>> queryResourceMenu(@RequestBody ResourceParamVO resourceParamVO){
    return DefaultCommonResult.success(ResultCodeEnum.OK,empRsMenuService.queryResourceMenu(resourceParamVO));
  }


  /**
   * 查询删除的资源是否已经授权
   * @param id 资源id
   * @return DefaultCommonResult<Boolean> 返回值为true表示存在为false表示不存在
   */
  @GetMapping("/query/{id}")
  public DefaultCommonResult<Boolean> qeryRsMenuEmp(@NotNull @PathVariable("id") Long id){
    return DefaultCommonResult.success(ResultCodeEnum.OK,empRsMenuService.qeryRsMenuEmp(id));
  }
}