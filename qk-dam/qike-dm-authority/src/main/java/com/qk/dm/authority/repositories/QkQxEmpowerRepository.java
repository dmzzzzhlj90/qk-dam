package com.qk.dm.authority.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.authority.entity.QxEmpower;

import java.util.List;

public interface QkQxEmpowerRepository extends BaseRepository<QxEmpower, Long> {
  List<QxEmpower> findByServiceId(String serviceId);

}