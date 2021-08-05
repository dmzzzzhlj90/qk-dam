package com.qk.dm.datastandards.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.datastandards.entity.DsdCodeInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DsdCodeInfoRepository extends BaseRepository<DsdCodeInfo, Long> {

  @Modifying
  @Query(" UPDATE DsdCodeInfo set codeDirLevel = :codeDirLevel where codeDirId = :codeDirId ")
  void updateCodeDirLevelByCodeDirId(
      @Param("codeDirLevel") String codeDirLevel, @Param("codeDirId") String codeDirId);
}
