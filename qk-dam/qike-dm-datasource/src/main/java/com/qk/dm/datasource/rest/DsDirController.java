package com.qk.dm.datasource.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.datasource.service.DsDirService;
import com.qk.dm.datasource.vo.DsDirReturnVO;
import com.qk.dm.datasource.vo.DsDirVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据源管理—数据源连接目录
 *
 * @author zys
 * @date 2021/7/30 15:20
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/datasorce/dir")
public class DsDirController {
  private final DsDirService dsDirService;

  @Autowired
  public DsDirController(DsDirService dsDirService) {
    this.dsDirService = dsDirService;
  }

  /**
   * 数据源分类目录-查询
   * @return DefaultCommonResult<List<DsDirReturnVO>>
   */
  @PostMapping("/query")
  public DefaultCommonResult<List<DsDirReturnVO>> getDsDir() {
    return DefaultCommonResult.success(ResultCodeEnum.OK, dsDirService.getDsDir());
  }

  /**
   * 数据源分类目录-查询（带数据源连接信息）
   * @return DefaultCommonResult<List<DsDirReturnVO>>
   */
  @PostMapping("/query/dsdir/datasource")
  public DefaultCommonResult<List<DsDirReturnVO>> getDsDirDataSource() {
    return DefaultCommonResult.success(ResultCodeEnum.OK, dsDirService.getDsDirDataSource());
  }

  /**
   * 数据源分类目录-新增
   *
   * @param dsDirVO 数据标准分类目录VO
   * @return DefaultCommonResult
   */
  @PostMapping("/add")
  public DefaultCommonResult addDsDir(@RequestBody DsDirVO dsDirVO) {
    dsDirService.addDsDir(dsDirVO);
    return DefaultCommonResult.success();
  }

  /**
   * 数据源目录支持根节点关联删除子节点方式-删除
   *
   * @param id 目录id
   * @return DefaultCommonResult
   */
  @DeleteMapping("/delete/{id}")
  public DefaultCommonResult deleteDsDir(@PathVariable("id") Integer id) {
    dsDirService.deleteDsDir(id);
    return DefaultCommonResult.success();
  }

  /**
   *  数据源分类目录-修改
   * @param dsDirVO
   * @return
   */
  @PutMapping
  public DefaultCommonResult updateDsDir(@RequestBody DsDirVO dsDirVO){
    dsDirService.updateDsDir(dsDirVO);
    return DefaultCommonResult.success();
  }
}
