package com.qk.dm.indicator.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.indicator.params.dto.IdcCompositeDTO;
import com.qk.dm.indicator.params.dto.IdcCompositePageDTO;
import com.qk.dm.indicator.params.vo.IdcCompositePageVO;
import com.qk.dm.indicator.params.vo.IdcCompositeVO;

import java.util.List;

/**
 * @author shenpj
 * @date 2021/9/3 4:46 下午
 * @since 1.0.0
 */
public interface IdcCompositeService {

  void insert(IdcCompositeDTO idcCompositeDTO);

  void update(Long id, IdcCompositeDTO idcCompositeDTO);

  void publish(Long id);

  void offline(Long id);

  void delete(String ids);

  IdcCompositeVO detail(Long id);

  List<IdcCompositeVO> getList();

  PageResultVO<IdcCompositePageVO> listByPage(IdcCompositePageDTO idcCompositePageDTO);
}
