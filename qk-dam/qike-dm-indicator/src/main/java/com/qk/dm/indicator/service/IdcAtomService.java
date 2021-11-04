package com.qk.dm.indicator.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.indicator.params.dto.IdcAtomDTO;
import com.qk.dm.indicator.params.dto.IdcAtomPageDTO;
import com.qk.dm.indicator.params.vo.IdcAtomPageVO;
import com.qk.dm.indicator.params.vo.IdcAtomVO;

import java.util.List;

/**
 * @author shenpj
 * @date 2021/9/2 8:39 下午
 * @since 1.0.0
 */
public interface IdcAtomService {
  void insert(IdcAtomDTO idcAtomDTO);

  void update(Long id, IdcAtomDTO idcAtomDTO);

  void publish(Long id);

  void offline(Long id);

  void delete(String ids);

  IdcAtomVO detail(Long id);

  IdcAtomVO getDetailByCode(String code);

  List<IdcAtomVO> getList();

  PageResultVO<IdcAtomPageVO> listByPage(IdcAtomPageDTO idcAtomPageDTO);
}
