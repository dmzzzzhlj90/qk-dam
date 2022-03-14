package com.qk.dm.authority.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.authority.entity.QxResources;

import java.util.List;

public interface QkQxResourcesRepository extends
    BaseRepository<QxResources, Long> {
  List<QxResources> findByPid(Long id);
  List<QxResources> findByServiceId(String serviceId);
}