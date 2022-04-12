package com.qk.dm.dataingestion.service;


import com.qk.dm.dataingestion.vo.DisSchedulerConfigVO;

import java.util.List;

public interface DisSchedulerConfigService {
    void add(DisSchedulerConfigVO disSchedulerConfigVO);

    void delete(List<Long> baseId);

    void update(DisSchedulerConfigVO disSchedulerConfigVO);

    DisSchedulerConfigVO detail(Long baseId);
}
