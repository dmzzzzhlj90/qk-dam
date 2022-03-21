package com.qk.dm.dataservice.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.dataservice.entity.DasApiRegister;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DasApiRegisterRepository extends BaseRepository<DasApiRegister, Long> {

  @Modifying
  @Query(" DELETE from DasApiRegister where apiId = :apiId ")
  void deleteByApiId(@Param("apiId") String apiId);
}