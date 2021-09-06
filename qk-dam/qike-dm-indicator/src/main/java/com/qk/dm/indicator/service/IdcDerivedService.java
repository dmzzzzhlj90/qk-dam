package com.qk.dm.indicator.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.indicator.params.dto.IdcDerivedDTO;
import com.qk.dm.indicator.params.dto.IdcDerivedPageDTO;
import com.qk.dm.indicator.params.vo.IdcDerivedVO;

public interface IdcDerivedService {

    void insert(IdcDerivedDTO idcDerivedDTO);

    void update(Long id,IdcDerivedDTO idcDerivedDTO);

    void delete(String ids);

    IdcDerivedVO detail(Long id);

    void publish(Long id);

    void offline(Long id);

    PageResultVO<IdcDerivedVO> findListPage(IdcDerivedPageDTO idcDerivedPageDTO);
}
