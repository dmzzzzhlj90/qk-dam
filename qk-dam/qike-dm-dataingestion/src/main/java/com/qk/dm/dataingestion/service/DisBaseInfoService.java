package com.qk.dm.dataingestion.service;


import com.qk.dm.dataingestion.vo.DisMigrationBaseInfoVO;

import java.util.List;

public interface DisBaseInfoService {
    Long add(DisMigrationBaseInfoVO disMigrationBaseInfoVO);

    void delete(List<Long> ids);

    void update(DisMigrationBaseInfoVO disMigrationBaseInfoVO);

    DisMigrationBaseInfoVO detail(Long id);

    Boolean exists(DisMigrationBaseInfoVO disMigrationBaseInfoVO);

    Boolean sourceExists(DisMigrationBaseInfoVO disMigrationBaseInfoVO);

    Boolean targetExists(DisMigrationBaseInfoVO disMigrationBaseInfoVO);
}
