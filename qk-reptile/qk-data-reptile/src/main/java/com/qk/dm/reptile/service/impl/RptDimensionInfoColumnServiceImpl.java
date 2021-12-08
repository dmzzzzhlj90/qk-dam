package com.qk.dm.reptile.service.impl;

import com.qk.dm.reptile.params.dto.RptDimensionInfoColumnDTO;
import com.qk.dm.reptile.params.vo.RptDimensionInfoColumnVO;
import com.qk.dm.reptile.service.RptDimensionInfoColumnService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 维度信息server
 * @author zys
 * @date 2021/12/8 14:55
 * @since 1.0.0
 */
@Service
public class RptDimensionInfoColumnServiceImpl implements
    RptDimensionInfoColumnService {
  /**
   * 新增维度数据
   * @param rptDimensionInfoColumnDTO
   */
  @Override
  public void addRptDimensionInfoColumn(
      RptDimensionInfoColumnDTO rptDimensionInfoColumnDTO) {

  }

  /**
   * 删除维度数据
   * @param id
   */
  @Override
  public void deleteRptDimensionInfoColumn(List<Long> id) {

  }

  /**
   * 修改维度数据
   * @param rptDimensionInfoColumnDTO
   */
  @Override
  public void updateRptDimensionInfoColumn(
      RptDimensionInfoColumnDTO rptDimensionInfoColumnDTO) {

  }

  /**
   * 查询维度数据
   * @return
   */
  @Override
  public List<RptDimensionInfoColumnVO> qyeryRptDimensionInfoColumn() {
    return null;
  }

  /**
   * 根据目录id和条件分页查询维度字段信息
   * @param id
   * @return
   */
  @Override
  public List<RptDimensionInfoColumnVO> qyeryRptDimensionInfoColumnById(
      Long id) {
    return null;
  }
}