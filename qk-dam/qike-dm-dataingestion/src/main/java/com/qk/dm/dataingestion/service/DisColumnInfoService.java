package com.qk.dm.dataingestion.service;


import com.qk.dm.dataingestion.vo.DisColumnInfoVO;

import java.util.List;

public interface DisColumnInfoService {
    void batchAdd(List<DisColumnInfoVO> disColumnInfoList);

    void delete(List<Long> baseIdList);

    void update(Long baseId,List<DisColumnInfoVO> ddgColumnInfoList);

    List<DisColumnInfoVO> list(Long baseInfoId);
}
