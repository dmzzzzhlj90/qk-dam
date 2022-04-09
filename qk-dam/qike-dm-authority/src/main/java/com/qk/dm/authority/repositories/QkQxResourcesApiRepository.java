package com.qk.dm.authority.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.authority.entity.QkQxResourcesApi;

import java.util.List;

public interface QkQxResourcesApiRepository extends BaseRepository<QkQxResourcesApi, Long> {
  List<QkQxResourcesApi> findByServiceId(String serviceId);
}