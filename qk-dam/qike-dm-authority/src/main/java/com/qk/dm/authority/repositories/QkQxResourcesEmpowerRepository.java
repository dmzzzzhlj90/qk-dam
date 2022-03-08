package com.qk.dm.authority.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.authority.entity.QkQxResourcesEmpower;

public interface QkQxResourcesEmpowerRepository extends BaseRepository<QkQxResourcesEmpower, Long>{
  void deleteByResourceId(Long resourceID);

}