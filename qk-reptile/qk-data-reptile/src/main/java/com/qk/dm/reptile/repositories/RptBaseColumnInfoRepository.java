package com.qk.dm.reptile.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.reptile.entity.RptBaseColumnInfo;

import java.util.List;

public interface RptBaseColumnInfoRepository extends BaseRepository<RptBaseColumnInfo, Long> {
    List<RptBaseColumnInfo> findAllByBaseInfoId(Long baseInfoId);
}