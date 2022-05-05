package com.qk.dm.dataingestion.service;


import com.qk.dm.dataingestion.vo.DisSchedulerConfigVO;

import java.util.List;

public interface DisSchedulerConfigService {
    void add(DisSchedulerConfigVO disSchedulerConfigVO);

    void delete(List<Long> baseId);

    void update(Long baseInfoId,DisSchedulerConfigVO disSchedulerConfigVO);

    void update(Long baseInfoId,Integer schedulerId);

    DisSchedulerConfigVO detail(Long baseId);
}
