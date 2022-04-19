package com.qk.dm.authority.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.authority.entity.QkQxResourcesEmpower;

import java.util.List;

public interface QkQxResourcesEmpowerRepository extends BaseRepository<QkQxResourcesEmpower, Long>{
  void deleteALLByResourceUuid(String resourceId);

  void deleteAllByEmpowerUuid(String empowerId);

   List<QkQxResourcesEmpower> findByEmpowerUuid(String empowerId);

  List<QkQxResourcesEmpower> findByResourceUuid(String resourceId);


}