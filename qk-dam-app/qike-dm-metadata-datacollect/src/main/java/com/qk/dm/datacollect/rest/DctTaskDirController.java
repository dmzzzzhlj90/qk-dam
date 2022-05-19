package com.qk.dm.datacollect.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.datacollect.service.DctTaskDirService;
import com.qk.dm.datacollect.vo.DctTaskDirTreeVO;
import com.qk.dm.datacollect.vo.DctTaskDirVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 任务目录
 * @author zys
 * @date 2022/4/27 15:31
 * @since 1.0.0
 */
@RestController
@RequestMapping("/dir")
public class DctTaskDirController {
  private final DctTaskDirService dctTaskDirService;

  @Autowired
  public DctTaskDirController(DctTaskDirService dctTaskDirService) {
    this.dctTaskDirService = dctTaskDirService;
  }
  /**
   * 获取任务目录
   *
   * @return DefaultCommonResult<List < DataQualityRuleDirTreeVO>> 返回值
   */
  @GetMapping("/list")
  public DefaultCommonResult<List<DctTaskDirTreeVO>> searchList() {
    return DefaultCommonResult.success(ResultCodeEnum.OK, dctTaskDirService.searchList());
  }

  /**
   * 新增任务目录
   *
   * @param dctTaskDirVO 新增入参
   * @return DefaultCommonResult
   */
  @PostMapping("")
  public DefaultCommonResult insert(@RequestBody DctTaskDirVO dctTaskDirVO) {
    dctTaskDirService.insert(dctTaskDirVO);
    return DefaultCommonResult.success();
  }

  /**
   * 编辑任务目录
   *
   * @param dctTaskDirVO 修改入参
   * @return DefaultCommonResult
   */
  @PutMapping("")
  public DefaultCommonResult update(@RequestBody DctTaskDirVO dctTaskDirVO) {
    dctTaskDirService.update(dctTaskDirVO);
    return DefaultCommonResult.success();
  }


  /**
   * 单个删除—任务目录单子节点级联删除方式
   *
   * @param id 删除入参
   * @return DefaultCommonResult
   */
  @DeleteMapping("/{id}")
  public DefaultCommonResult delete(@PathVariable("id") String id) {
    dctTaskDirService.delete(id);
    return DefaultCommonResult.success();
  }

  /**
   * 批量删除—任务目录单子节点级联删除方式
   *
   * @param ids 批量删除入参
   * @return DefaultCommonResult
   */
  @DeleteMapping("/bulk")
  public DefaultCommonResult deleteBulk(@RequestParam("ids") String ids) {
    dctTaskDirService.deleteBulk(ids);
    return DefaultCommonResult.success();
  }
}