package com.qk.dm.metadata.service;

import com.qk.dm.metadata.vo.MtdLineageParamsVO;
import com.qk.dm.metadata.vo.MtdLineageVO;
import java.util.Map;

/**
 * @author wangzp
 * @date 2021/10/15 15:14
 * @since 1.0.0
 */
public interface MetaDataLineageService {
  /**
   * 获取元数据血缘
   *
   * @param mtdLineageParamsVO
   * @return
   */
  MtdLineageVO getLineageInfo(MtdLineageParamsVO mtdLineageParamsVO);

  /**
   * 获取元数据过程 input output
   *
   * @param guid
   * @return
   */
  Map<String, Object> relationShip(String guid);
}
