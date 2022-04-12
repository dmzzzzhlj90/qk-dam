package com.qk.dm.dataingestion.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.dataingestion.entity.DisSchedulerConfig;


public interface DisSchedulerConfigRepository extends BaseRepository<DisSchedulerConfig, Long> {

     void deleteByBaseInfoId(Long baseInfoId);

     DisSchedulerConfig findByBaseInfoId(Long baseInfoId);
}