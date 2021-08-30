package com.qk.dm.dataservice.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.dataservice.entity.DasApiCreateConfig;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DasApiCreateConfigRepository extends BaseRepository<DasApiCreateConfig, Long> {

    @Modifying
    @Query(" DELETE from DasApiCreateConfig where apiId = :apiId ")
    void deleteByApiId(@Param("apiId") String apiId);
}