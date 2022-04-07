package com.qk.dm.dataingestion.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.dataingestion.entity.DisColumnInfo;

import java.util.List;

public interface DisColumnInfoRepository extends BaseRepository<DisColumnInfo, Long> {


    void deleteByBaseInfoId(Long baseInfoId);

    List<DisColumnInfo> findByBaseInfoId(Long baseInfoId);
}