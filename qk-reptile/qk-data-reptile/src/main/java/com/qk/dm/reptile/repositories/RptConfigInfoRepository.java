package com.qk.dm.reptile.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.reptile.entity.RptConfigInfo;

import java.util.List;

public interface RptConfigInfoRepository extends BaseRepository<RptConfigInfo, Long>{
    List<RptConfigInfo> findAllByBaseInfoId(Long baseInfoId);
}