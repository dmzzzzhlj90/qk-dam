package com.qk.dm.metadata.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.metadata.entity.MtdClassify;

import java.util.List;

public interface MtdClassifyRepository extends BaseRepository<MtdClassify, Long> {
    List<MtdClassify> findAllBySynchStatusIsNot(Integer status);
}
