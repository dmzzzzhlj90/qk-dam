package com.qk.dm.dataservice.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.dataservice.entity.DasApiCreateSqlScript;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DasApiCreateSqlScriptRepository
    extends BaseRepository<DasApiCreateSqlScript, Long> {

  @Modifying
  @Query(" DELETE from DasApiCreateSqlScript where apiId = :apiId ")
  void deleteByApiId(@Param("apiId") String apiId);
}
