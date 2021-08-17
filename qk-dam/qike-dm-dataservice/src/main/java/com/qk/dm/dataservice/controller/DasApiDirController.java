package com.qk.dm.dataservice.controller;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataservice.service.DasApiDirService;
import com.qk.dm.dataservice.vo.DasApiDirTreeVO;
import com.qk.dm.dataservice.vo.DasApiDirVO;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 数据服务_API目录
 *
 * @author wjq
 * @date 20210816
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/dir")
public class DasApiDirController {
  private final DasApiDirService dasApiDirService;

  @Autowired
  public DasApiDirController(DasApiDirService dasApiDirService) {
    this.dasApiDirService = dasApiDirService;
  }

  /**
   * 获取API目录树
   *
   * @return DefaultCommonResult<List < DaasApiTreeVO>>
   */
  @GetMapping("/tree")
  public DefaultCommonResult<List<DasApiDirTreeVO>> getDasApiDirTree() {
    return DefaultCommonResult.success(ResultCodeEnum.OK, dasApiDirService.getTree());
  }

  /**
   * 新增API目录
   *
   * @param daasApiDirVO
   * @return DefaultCommonResult
   */
  @PostMapping("/add")
  public DefaultCommonResult addDasApiDir(@RequestBody DasApiDirVO daasApiDirVO) {
    dasApiDirService.addDasApiDir(daasApiDirVO);
    return DefaultCommonResult.success();
  }

  /**
   * 编辑API分类目录
   *
   * @param daasApiDirVO
   * @return DefaultCommonResult
   */
  @PutMapping("/update")
  public DefaultCommonResult updateDasApiDir(@RequestBody DasApiDirVO daasApiDirVO) {
    dasApiDirService.updateDasApiDir(daasApiDirVO);
    return DefaultCommonResult.success();
  }

  /**
   * 标准目录单子节点删除方式
   *
   * @param id
   * @return DefaultCommonResult
   */
  @DeleteMapping("/delete/{id}")
  public DefaultCommonResult deleteDasApiDir(@PathVariable("id") String id) {
    dasApiDirService.deleteDasApiDir(Long.valueOf(id));
    return DefaultCommonResult.success();
  }

  /**
   * 标准目录支持根节点关联删除子节点方式
   *
   * @param id
   * @return DefaultCommonResult
   */
  @DeleteMapping("/delete/root/{id}")
  public DefaultCommonResult deleteDasApiDirRoot(@PathVariable("id") String id) {
    dasApiDirService.deleteDasApiDirRoot(Long.valueOf(id));
    return DefaultCommonResult.success();
  }
}
