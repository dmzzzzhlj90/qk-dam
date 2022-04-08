package com.qk.dm.datamodel.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.datamodel.entity.ModelDimTable;

import java.util.List;

public interface ModelDimTableRepository extends BaseRepository<ModelDimTable, Long> {

    void deleteByModelDimId(Long modelDimId);

    List<ModelDimTable> findAllByModelDimIdIn(List<Long> dims);

    List<ModelDimTable> findAllByModelDimId(Long modelDimId);
}