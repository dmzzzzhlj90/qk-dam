package com.qk.dm.datastandards.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.datastandards.entity.DsdBasicinfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface DsdBasicinfoRepository extends BaseRepository<DsdBasicinfo, Integer> {


    @Modifying
    @Query(" UPDATE DsdBasicinfo set dsdLevel = :dsdDirLevel where dsdLevelId = :dirDsdId ")
    void updateDirLevelByDirId(@Param("dsdDirLevel") String dsdDirLevel, @Param("dirDsdId") String dirDsdId);


}
