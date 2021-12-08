package com.qk.dm.reptile.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.reptile.params.dto.RptDimensionInfoColumnDTO;
import com.qk.dm.reptile.params.vo.RptDimensionInfoColumnVO;
import com.qk.dm.reptile.service.RptDimensionInfoColumnService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 可视化_维度信息
 * @author zys
 * @date 2021/12/8 12:16
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/dimension")
public class RptDimensionColumnInfoController {
private final RptDimensionInfoColumnService rptDimensionInfoColumnService;

  public RptDimensionColumnInfoController(
      RptDimensionInfoColumnService rptDimensionInfoColumnService) {
    this.rptDimensionInfoColumnService = rptDimensionInfoColumnService;
  }

  /**
   * 数据采集-目录信息查询
   *
   * DefaultCommonResult<List<RptDimensionInfoColumnVO>>
   */
  @GetMapping
  public DefaultCommonResult<List<RptDimensionInfoColumnVO>> queryDsDir() {
    return DefaultCommonResult
        .success(ResultCodeEnum.OK, rptDimensionInfoColumnService.qyeryRptDimensionInfoColumn());
  }

  /**
   * 数据采集-目录信息新增
   * @param rptDimensionInfoColumnDTO
   * @return
   */
  @PostMapping
  public DefaultCommonResult addDsDir(@RequestBody RptDimensionInfoColumnDTO rptDimensionInfoColumnDTO) {
    rptDimensionInfoColumnService.addRptDimensionInfoColumn(rptDimensionInfoColumnDTO);
    return DefaultCommonResult.success();
  }

  /**
   * 数据采集-目录信息删除
   * @param id
   * @return
   */
  @DeleteMapping
  public DefaultCommonResult deleteDsDir(@PathVariable("id") Integer id) {
    rptDimensionInfoColumnService.deleteRptDimensionInfoColumn(id);
    return DefaultCommonResult.success();
  }

  /**
   * 数据采集-目录信息修改
   * @param rptDimensionInfoColumnDTO
   * @return
   */
  @PutMapping
  public DefaultCommonResult updateDsDir(@RequestBody RptDimensionInfoColumnDTO rptDimensionInfoColumnDTO) {
    rptDimensionInfoColumnService.updateRptDimensionInfoColumn(rptDimensionInfoColumnDTO);
    return DefaultCommonResult.success();
  }
}