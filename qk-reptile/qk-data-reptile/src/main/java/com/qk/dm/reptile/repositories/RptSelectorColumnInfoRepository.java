package com.qk.dm.reptile.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.reptile.entity.RptSelectorColumnInfo;

import java.util.List;

public interface RptSelectorColumnInfoRepository extends BaseRepository<RptSelectorColumnInfo, Long>{
    List<RptSelectorColumnInfo> findAllByConfigId(Long configId);

    void deleteAllByConfigId(Long configId);

    List<RptSelectorColumnInfo> findAllByConfigIdIn(List<Long> configIdList);
}