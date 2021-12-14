package com.qk.dm.reptile.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.reptile.params.dto.RptDimensionInfoDTO;
import com.qk.dm.reptile.params.vo.RptDimensionInfoParamsVO;
import com.qk.dm.reptile.params.vo.RptDimensionInfoVO;
import com.qk.dm.reptile.service.RptDimensionInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 可视化_维度信息
 * @author zys
 * @date 2021/12/8 12:14
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/dir")
public class RptDimensionInfoController {
  private final RptDimensionInfoService rptDimensionInfoService;

  public RptDimensionInfoController(
      RptDimensionInfoService rptDimensionInfoService) {
    this.rptDimensionInfoService = rptDimensionInfoService;
  }

  /**
   * 数据采集-目录查询
   *
   * @return DefaultCommonResult<List<RptDimensionInfoVO>>
   */
  @GetMapping
  public DefaultCommonResult<List<RptDimensionInfoVO>> queryDsDir() {
    return DefaultCommonResult.success(ResultCodeEnum.OK, rptDimensionInfoService.qyeryRptDir());
  }

  /**
   * 数据采集-目录新增
   * @param rptDimensionInfoDTO
   * @return
   */
  @PostMapping
  public DefaultCommonResult addDsDir(@RequestBody RptDimensionInfoDTO rptDimensionInfoDTO) {
    rptDimensionInfoService.addRptDir(rptDimensionInfoDTO);
    return DefaultCommonResult.success();
  }

  /**
   * 数据采集-目录删除
   * @param id
   * @return
   */
  @DeleteMapping("/{id}")
  public DefaultCommonResult deleteDsDir(@NotBlank @PathVariable("id")Long id) {
    rptDimensionInfoService.deleteRptDir(id);
    return DefaultCommonResult.success();
  }

  /**
   * 数据采集-目录修改
   * @param rptDimensionInfoDTO
   * @return
   */
  @PutMapping
  public DefaultCommonResult updateDsDir(@RequestBody RptDimensionInfoDTO rptDimensionInfoDTO) {
    rptDimensionInfoService.updateRptDir(rptDimensionInfoDTO);
    return DefaultCommonResult.success();
  }

  /**
   * 数据采集-获取维度目录名称
   * DefaultCommonResult<List<RptDimensionInfoParamsVO>>
   */
  @GetMapping("/dirname")
  public DefaultCommonResult<List<RptDimensionInfoParamsVO>> updateDirName() {
    return DefaultCommonResult.success(ResultCodeEnum.OK, rptDimensionInfoService.getDirName());
  }

}