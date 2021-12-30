package com.qk.dm.reptile.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.reptile.entity.RptDict;

import java.util.List;

public interface RptDictRepository extends BaseRepository<RptDict, Long> {

    List<RptDict> findAllByPid(Long pid);

}