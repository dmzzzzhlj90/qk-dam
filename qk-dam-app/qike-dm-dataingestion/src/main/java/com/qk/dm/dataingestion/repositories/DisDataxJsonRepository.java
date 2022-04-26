package com.qk.dm.dataingestion.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.dataingestion.entity.DisDataxJson;

public interface DisDataxJsonRepository extends BaseRepository<DisDataxJson, Long> {
    DisDataxJson findByBaseInfoId(Long baseInfoId);
}