package com.qk.dm.reptile.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.reptile.entity.RptDimensionColumnInfo;

import java.util.List;

public interface RptDimensionColumnInfoRepository extends BaseRepository<RptDimensionColumnInfo, Long> {

  List<RptDimensionColumnInfo> findAllByDimensionId(Long dimensionId);

}