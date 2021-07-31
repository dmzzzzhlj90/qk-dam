package com.qk.dm.datastandards.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.datastandards.entity.DsdCodeInfoExt;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DsdCodeInfoExtRepository extends BaseRepository<DsdCodeInfoExt, Long> {

    @Modifying
    @Query(" DELETE FROM DsdCodeInfoExt WHERE dsdCodeInfoId  = :dsdCodeInfoId ")
    void deleteByDsdCodeInfoId(@Param("dsdCodeInfoId") long dsdCodeInfoId);
}