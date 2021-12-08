package com.qk.dm.reptile.service.impl;

import com.qk.dm.reptile.params.dto.RptDimensionInfoDTO;
import com.qk.dm.reptile.params.vo.RptDimensionInfoVO;
import com.qk.dm.reptile.service.RptDimensionInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 维度目录server
 * @author zys
 * @date 2021/12/8 14:39
 * @since 1.0.0
 */@Service
public class RptDimensionInfoServerImpl implements RptDimensionInfoService {
  /**
   * 查询目录
   * @return
   */
  @Override
  public List<RptDimensionInfoVO> qyeryRptDir() {
    return null;
  }

  /**
   * 新增目录
   * @param rptDimensionInfoDTO
   */
  @Override
  public void addRptDir(RptDimensionInfoDTO rptDimensionInfoDTO) {

  }

  /**
   * 删除目录
   * @param id
   */
  @Override
  public void deleteRptDir(Integer id) {

  }

  /**
   * 修改目录
   * @param rptDimensionInfoDTO
   */
  @Override
  public void updateRptDir(RptDimensionInfoDTO rptDimensionInfoDTO) {

  }
}