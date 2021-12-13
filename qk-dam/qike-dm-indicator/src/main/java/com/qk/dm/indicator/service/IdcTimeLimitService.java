package com.qk.dm.indicator.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.indicator.params.dto.IdcTimeLimitDTO;
import com.qk.dm.indicator.params.dto.IdcTimeLimitPageDTO;
import com.qk.dm.indicator.params.vo.IdcTimeLimitVO;
import java.util.List;

/**
 * @author wangzp
 * @date 2021/9/1 15:51
 * @since 1.0.0
 */
public interface IdcTimeLimitService {

  void insert(IdcTimeLimitDTO idcTimeLimitDTO);

  void update(Long id, IdcTimeLimitDTO idcTimeLimitDTO);

  void delete(String ids);

  IdcTimeLimitVO detail(Long id);

  List<IdcTimeLimitVO> findAll();

  PageResultVO<IdcTimeLimitVO> findListPage(IdcTimeLimitPageDTO idcTimeLimitPageDTO);
}
