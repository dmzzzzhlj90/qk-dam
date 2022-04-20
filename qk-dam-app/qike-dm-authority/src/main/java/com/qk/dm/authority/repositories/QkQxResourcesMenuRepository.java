package com.qk.dm.authority.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.authority.entity.QkQxResourcesMenu;

import java.util.List;

public interface QkQxResourcesMenuRepository extends BaseRepository<QkQxResourcesMenu, Long> {
  List<QkQxResourcesMenu> findByPid(Long id);
  List<QkQxResourcesMenu> findByServiceId(String serviceId);
}