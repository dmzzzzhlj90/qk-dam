package com.qk.dm.datasource.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dam.jpa.pojo.Pagination;
import com.qk.dm.datasource.service.DsDirectoryService;
import com.qk.dm.datasource.vo.DsDirectoryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据源管理__应用系统录入接口
 *
 * @author zys
 * @date 20210729
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/directory")
public class DsDirectoryController {
  private final DsDirectoryService dsDirectoryService;

  @Autowired
  public DsDirectoryController(DsDirectoryService dsDirectoryService) {
    this.dsDirectoryService = dsDirectoryService;
  }

  /**
   * 数据源管理—查询应用系统
   *
   * @param pagination 分页信息
   * @return DefaultCommonResult<PageResultVO<DsDirectoryVO>> 引用系统信息
   */
  @PostMapping(value = "/query")
  public DefaultCommonResult<PageResultVO<DsDirectoryVO>> getDsDirectory(
      @RequestBody Pagination pagination) {
    // 默认设置id为查询排序
    pagination.setSortField("id");
    return DefaultCommonResult.success(ResultCodeEnum.OK, dsDirectoryService.getSysDirectory(pagination));
  }

  /**
   * 数据管理—新增应用系统新
   *
   * @param dsDirectoryVO 应用系统VO
   * @return DefaultCommonResult
   */
  @PostMapping(value = "/add")
  public DefaultCommonResult addDsDirentory(@RequestBody @Validated DsDirectoryVO dsDirectoryVO) {
    dsDirectoryService.addSysDirectory(dsDirectoryVO);
    return DefaultCommonResult.success();
  }

  /**
   * 数据源管理—删除应用系统信息
   *
   * @param id 应用系统id
   * @return DefaultCommonResult
   */
  @DeleteMapping("/delete/{id}")
  public DefaultCommonResult deleteDsDirectory(@PathVariable("id") String id) {
    dsDirectoryService.deleteDsDirectory(id);
    return DefaultCommonResult.success();
  }

  /**
   * 数据源管理—修改应用系统信息
   *
   * @param dsDirectoryVO 应用系统VO
   * @return DefaultCommonResult 成功
   */
  @PutMapping
  public DefaultCommonResult updateDsDirectory(@RequestBody @Validated DsDirectoryVO dsDirectoryVO) {
    dsDirectoryService.updateDsDirectory(dsDirectoryVO);
    return DefaultCommonResult.success();
  }

  /**
   * 数据源管理—查询应用系统名称
   *
   * @return
   */
  @GetMapping("/sysname")
  public DefaultCommonResult<List<String>> getSysName() {
    return DefaultCommonResult.success(ResultCodeEnum.OK, dsDirectoryService.getSysName());
  }
}
