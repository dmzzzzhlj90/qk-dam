package com.qk.dm.reptile.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.reptile.params.dto.RptDimensionInfoColumnDTO;
import com.qk.dm.reptile.params.dto.RptDimensionInfoColumnParamDTO;
import com.qk.dm.reptile.params.vo.RptDimensionInfoColumnVO;
import com.qk.dm.reptile.service.RptDimensionInfoColumnService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

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
   * 数据采集-维度新增
   * @param rptDimensionInfoColumnDTO
   * @return
   */
  @PostMapping
  public DefaultCommonResult addColumn(@RequestBody RptDimensionInfoColumnDTO rptDimensionInfoColumnDTO) {
    rptDimensionInfoColumnService.addRptDimensionInfoColumn(rptDimensionInfoColumnDTO);
    return DefaultCommonResult.success();
  }

  /**
   * 数据采集-维度删除
   * @param ids
   * @return
   */
  @DeleteMapping
  public DefaultCommonResult deleteColumn(@NotBlank @RequestBody List<Long> ids) {
    rptDimensionInfoColumnService.deleteRptDimensionInfoColumn(ids);
    return DefaultCommonResult.success();
  }

  /**
   * 数据采集-维度修改
   * @param rptDimensionInfoColumnDTO
   * @return
   */
  @PutMapping
  public DefaultCommonResult updateColumn(@RequestBody RptDimensionInfoColumnDTO rptDimensionInfoColumnDTO) {
    rptDimensionInfoColumnService.updateRptDimensionInfoColumn(rptDimensionInfoColumnDTO);
    return DefaultCommonResult.success();
  }

  /**
   * 数据采集-维度查询
   *
   * @return DefaultCommonResult<List<RptDimensionInfoColumnVO>>
   */
  @PostMapping("/query")
  public DefaultCommonResult<PageResultVO<RptDimensionInfoColumnVO>> queryColumn(@RequestBody
      RptDimensionInfoColumnParamDTO rptDimensionInfoColumnParamDTO) {
    return DefaultCommonResult.success(ResultCodeEnum.OK, rptDimensionInfoColumnService.qyeryRptDimensionInfoColumn(rptDimensionInfoColumnParamDTO));
}

  /**
   * 数据采集-根据维度名称查询维度信息
   *
   * @return DefaultCommonResult<Map<String,String>>
   */
  @GetMapping("/{dimensionColumnName}")
  public DefaultCommonResult<Map<String,String>> queryColumnByDirName(@NotBlank @PathVariable("dimensionColumnName")String dimensionColumnName) {
    return DefaultCommonResult.success(ResultCodeEnum.OK, rptDimensionInfoColumnService.queryColumnByDirName(dimensionColumnName));
  }
}