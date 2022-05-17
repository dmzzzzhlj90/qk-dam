package com.qk.dm.reptile.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.reptile.entity.RptFindSource;

import java.util.List;

public interface RptFindSourceRepository extends BaseRepository<RptFindSource, Long> {

    List<RptFindSource> findAllByStatus(Integer status);

}