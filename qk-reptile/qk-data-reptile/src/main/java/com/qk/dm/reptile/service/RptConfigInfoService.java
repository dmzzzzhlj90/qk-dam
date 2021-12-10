package com.qk.dm.reptile.service;

import com.qk.dm.reptile.entity.RptConfigInfo;
import com.qk.dm.reptile.params.dto.RptConfigInfoDTO;
import com.qk.dm.reptile.params.vo.RptConfigInfoVO;

import java.util.List;

public interface RptConfigInfoService {

    Long insert(RptConfigInfoDTO rptConfigInfoDTO);

    void update(Long id, RptConfigInfoDTO rptConfigInfoDTO);

    RptConfigInfoVO detail(Long id);

    void delete(String ids);

    List<RptConfigInfoVO> list(Long baseInfoId);
}
