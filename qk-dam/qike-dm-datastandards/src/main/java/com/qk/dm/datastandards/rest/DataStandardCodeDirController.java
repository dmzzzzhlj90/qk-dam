package com.qk.dm.datastandards.rest;

import com.qk.dam.authorization.Auth;
import com.qk.dam.authorization.BizResource;
import com.qk.dam.authorization.RestActionType;
import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.datastandards.service.DataStandardCodeDirService;
import com.qk.dm.datastandards.vo.DataStandardCodeTreeVO;
import com.qk.dm.datastandards.vo.DsdCodeDirVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据标准__码表目录接口
 *
 * @author wjq
 * @date 20210604
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/code/dir")
public class DataStandardCodeDirController {
  private final DataStandardCodeDirService dataStandardCodeDirService;

  @Autowired
  public DataStandardCodeDirController(DataStandardCodeDirService dataStandardCodeDirService) {
    this.dataStandardCodeDirService = dataStandardCodeDirService;
  }

  /**
   * 获取数据标准码表分类目录树
   *
   * @return DefaultCommonResult<List < DataStandardCodeTreeVO>>
   */
  @GetMapping("/list")
  @Auth(bizType = BizResource.DSD_CODE_DIR, actionType = RestActionType.LIST)
  public DefaultCommonResult<List<DataStandardCodeTreeVO>> searchList() {
    return DefaultCommonResult.success(ResultCodeEnum.OK, dataStandardCodeDirService.getTree());
  }

  /**
   * 新增码表分类目录
   *
   * @param dsdCodeDirVO 数据标准码表分类目录VO
   * @return DefaultCommonResult
   */
  @PostMapping("")
  @Auth(bizType = BizResource.DSD_CODE_DIR, actionType = RestActionType.CREATE)
  public DefaultCommonResult insert(@RequestBody DsdCodeDirVO dsdCodeDirVO) {
    dataStandardCodeDirService.addDsdDir(dsdCodeDirVO);
    return DefaultCommonResult.success();
  }

  /**
   * 编辑码表分类目录
   *
   * @param dsdCodeDirVO
   * @return DefaultCommonResult
   */
  @PutMapping("")
  @Auth(bizType = BizResource.DSD_CODE_DIR, actionType = RestActionType.UPDATE)
  public DefaultCommonResult update(@RequestBody DsdCodeDirVO dsdCodeDirVO) {
    dataStandardCodeDirService.updateDsdDir(dsdCodeDirVO);
    return DefaultCommonResult.success();
  }

  /**
   * 码表目录单叶子节点删除方式
   *
   * @param id
   * @return DefaultCommonResult
   */
  @DeleteMapping("/{id}")
  @Auth(bizType = BizResource.DSD_CODE_DIR, actionType = RestActionType.DELETE)
  public DefaultCommonResult delete(@PathVariable("id") Integer id) {
    dataStandardCodeDirService.deleteDsdDir(id);
    return DefaultCommonResult.success();
  }

  /**
   * 码表目录支持根节点删除关联删除叶子节点方式
   *
   * @param id
   * @return DefaultCommonResult
   */
  @DeleteMapping("/root/{id}")
  @Auth(bizType = BizResource.DSD_CODE_DIR, actionType = RestActionType.DELETE)
  public DefaultCommonResult deleteBulk(@PathVariable("id") Integer id) {
    dataStandardCodeDirService.deleteDsdDirRoot(id);
    return DefaultCommonResult.success();
  }

  /**
   * 码表目录删除-判断目录中是否存在数据
   * @param id
   * @return
   */
  @DeleteMapping("/delete/judge/{id}")
  public DefaultCommonResult deleteJudgeDsdDir(@PathVariable("id") Integer id) {
    Boolean resut  = dataStandardCodeDirService.deleteJudgeDsdDir(id);
    return new DefaultCommonResult(ResultCodeEnum.OK,resut);
  }

}
