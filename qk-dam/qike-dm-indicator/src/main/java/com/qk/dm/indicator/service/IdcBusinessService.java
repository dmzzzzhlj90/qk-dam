package com.qk.dm.indicator.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.indicator.params.dto.IdcBusinessDTO;
import com.qk.dm.indicator.params.dto.IdcBusinessPageDTO;
import com.qk.dm.indicator.params.vo.IdcBusinessVO;

/**
 * @author wangzp
 * @date 2021/9/2 14:43
 * @since 1.0.0
 */
public interface IdcBusinessService {
    void insert(IdcBusinessDTO idcBusinessDTO);

    void update(Long id,IdcBusinessDTO idcBusinessDTO);

    void delete(String ids);

    IdcBusinessVO detail(Long id);


    PageResultVO<IdcBusinessVO> findListPage(IdcBusinessPageDTO idcBusinessPageDTO);
}
