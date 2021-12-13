package com.qk.dm.datastandards.rest;

//import com.qk.dam.authorization.Auth;
//import com.qk.dam.authorization.BizResource;
//import com.qk.dam.authorization.RestActionType;
import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datastandards.service.DataStandardBasicInfoService;
import com.qk.dm.datastandards.vo.CodeTableFieldsVO;
import com.qk.dm.datastandards.vo.DsdBasicInfoParamsVO;
import com.qk.dm.datastandards.vo.DsdBasicInfoVO;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 数据标准__标准基本信息接口
 *
 * @author wjq
 * @date 20210604
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/basic/info")
public class DataStandardBasicInfoController {
  private final DataStandardBasicInfoService dataStandardBasicInfoService;

  @Autowired
  public DataStandardBasicInfoController(
      DataStandardBasicInfoService dataStandardBasicInfoService) {
    this.dataStandardBasicInfoService = dataStandardBasicInfoService;
  }

  /**
   * 统一查询标准信息入口
   *
   * @param basicInfoParamsVO
   * @return DefaultCommonResult<PageResultVO < DsdBasicinfoVO>>
   */
  @PostMapping(value = "/list")
//  @Auth(bizType = BizResource.DSD_BASIC_INFO, actionType = RestActionType.LIST)
  public DefaultCommonResult<PageResultVO<DsdBasicInfoVO>> searchList(
      @RequestBody DsdBasicInfoParamsVO basicInfoParamsVO) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, dataStandardBasicInfoService.getDsdBasicInfo(basicInfoParamsVO));
  }

  /**
   * 新增标准信息
   *
   * @param dsdBasicinfoVO
   * @return DefaultCommonResult
   */
  @PostMapping("")
//  @Auth(bizType = BizResource.DSD_BASIC_INFO, actionType = RestActionType.CREATE)
  public DefaultCommonResult insert(@RequestBody @Validated DsdBasicInfoVO dsdBasicinfoVO) {
    dataStandardBasicInfoService.addDsdBasicinfo(dsdBasicinfoVO);
    return DefaultCommonResult.success();
  }

  /**
   * 编辑标准信息
   *
   * @param dsdBasicinfoVO
   * @return DefaultCommonResult
   */
  @PutMapping("")
//  @Auth(bizType = BizResource.DSD_BASIC_INFO, actionType = RestActionType.UPDATE)
  public DefaultCommonResult update(@RequestBody @Validated DsdBasicInfoVO dsdBasicinfoVO) {
    dataStandardBasicInfoService.updateDsdBasicinfo(dsdBasicinfoVO);
    return DefaultCommonResult.success();
  }

  /**
   * 删除标准信息
   *
   * @param id
   * @return DefaultCommonResult
   */
  @DeleteMapping("/{id}")
//  @Auth(bizType = BizResource.DSD_BASIC_INFO, actionType = RestActionType.DELETE)
  public DefaultCommonResult delete(@PathVariable("id") Integer id) {
    dataStandardBasicInfoService.deleteDsdBasicinfo(id);
    return DefaultCommonResult.success();
  }

  /**
   * 批量删除标准信息
   *
   * @param ids
   * @return DefaultCommonResult
   */
  @DeleteMapping("/bulk/{ids}")
//  @Auth(bizType = BizResource.DSD_BASIC_INFO, actionType = RestActionType.DELETE)
  public DefaultCommonResult deleteBulk(@PathVariable("ids") String ids) {
    dataStandardBasicInfoService.bulkDeleteDsdBasicInfo(ids);
    return DefaultCommonResult.success();
  }

  /**
   * 引用码表字段
   *
   * @param codeDirId
   * @return DefaultCommonResult
   */
  @GetMapping("/search/code/field/{codeDirId}")
//  @Auth(bizType = BizResource.DSD_BASIC_INFO_CODE, actionType = RestActionType.GET)
  public DefaultCommonResult<List<CodeTableFieldsVO>> getCodeFieldByCodeDirId(
      @PathVariable("codeDirId") String codeDirId) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, dataStandardBasicInfoService.getCodeFieldByCodeDirId(codeDirId));
  }
}
