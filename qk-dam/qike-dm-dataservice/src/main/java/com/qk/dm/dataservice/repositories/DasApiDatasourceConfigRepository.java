package com.qk.dm.dataservice.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.dataservice.entity.DasApiDatasourceConfig;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DasApiDatasourceConfigRepository
    extends BaseRepository<DasApiDatasourceConfig, Long> {

  @Modifying
  @Query(" DELETE from DasApiDatasourceConfig where apiId = :apiId ")
  void deleteByApiId(@Param("apiId") String apiId);
}
