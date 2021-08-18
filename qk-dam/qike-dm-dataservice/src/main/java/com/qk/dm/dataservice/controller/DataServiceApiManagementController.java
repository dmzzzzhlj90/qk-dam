package com.qk.dm.dataservice.controller;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataservice.service.DataServiceApiManagementService;
import com.qk.dm.dataservice.vo.DasApplicationManagementParamsVO;
import com.qk.dm.dataservice.vo.DasApplicationManagementVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 应用管理
 * @author zys
 * @date 2021/8/17 14:49
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/management")
public class DataServiceApiManagementController {
  private final DataServiceApiManagementService dataServiceApiManagementService;

  @Autowired
  public DataServiceApiManagementController(
      DataServiceApiManagementService dataServiceApiManagementService) {
    this.dataServiceApiManagementService = dataServiceApiManagementService;
  }

  /**
   * 新增应用管理信息
   * @param dasApplicationManagementVO
   * @return
   */
  @PostMapping("/add")
  public DefaultCommonResult addDasApiManagement(
      @RequestBody @Validated DasApplicationManagementVO dasApplicationManagementVO) {
    dataServiceApiManagementService.addDasManagement(dasApplicationManagementVO);
    return DefaultCommonResult.success();
  }

  /**
   * 统一查询应用管理系统
   * @param dasApplicationManagementParamsVO
   * @return
   */
  @PostMapping(value = "/query")
  public DefaultCommonResult<PageResultVO<DasApplicationManagementVO>> getDasApiDasAiManagement(
          @RequestBody DasApplicationManagementParamsVO dasApplicationManagementParamsVO) {
    return DefaultCommonResult.success(ResultCodeEnum.OK,dataServiceApiManagementService.getDasApiDasAiManagement(dasApplicationManagementParamsVO));
  }

  /**
   * 修改应用管理信息
   * @param dasApplicationManagementVO
   * @return
   */
  @PostMapping("/update")
  public DefaultCommonResult updateDasApiManagement(
          @RequestBody @Validated DasApplicationManagementVO dasApplicationManagementVO) {
    dataServiceApiManagementService.updateDasManagement(dasApplicationManagementVO);
    return DefaultCommonResult.success();
  }

  /**
   * 删除应用管理信息
   * @param id
   * @return
   */
  @DeleteMapping("/delete/{id}")
  public DefaultCommonResult deleteDasApiManagement(@PathVariable("id") String id) {
    dataServiceApiManagementService.deleteDasApiManagement(Long.valueOf(id));
    return DefaultCommonResult.success();
  }

  /**
   * 批量删除引用管理信息
   * @param ids
   * @return
   */
  @DeleteMapping("/bulk/delete/{ids}")
  public DefaultCommonResult bulkDeleteDasApiManagement(@PathVariable("ids") String ids) {
    dataServiceApiManagementService.bulkDeleteDasApiManagement(ids);
    return DefaultCommonResult.success();
  }

}
