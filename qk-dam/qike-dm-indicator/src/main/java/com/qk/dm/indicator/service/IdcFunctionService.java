package com.qk.dm.indicator.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.indicator.vo.dto.IdcFunctionDTO;
import com.qk.dm.indicator.vo.dto.IdcFunctionPageDTO;
import com.qk.dm.indicator.vo.IdcFunctionVO;

import java.util.List;
import java.util.Map;

/**
 * @author shenpj
 * @date 2021/9/1 2:48 下午
 * @since 1.0.0
 */
public interface IdcFunctionService {
  void insert(IdcFunctionDTO idcFunctionDTO);

  void update(Long id, IdcFunctionDTO mtdLabelsVO);

  void delete(String ids);

  IdcFunctionVO detail(Long id);

  Map<String, List<IdcFunctionVO>> getList(String engine);

  PageResultVO<IdcFunctionVO> listByPage(IdcFunctionPageDTO idcFunctionPageDTO);
}
