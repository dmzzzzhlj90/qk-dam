package com.qk.dm.datamodel.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.datamodel.entity.ModelPhysicalColumn;

import java.util.List;

public interface ModelPhysicalColumnRepository extends BaseRepository<ModelPhysicalColumn, Long> {

     List<ModelPhysicalColumn> findAllByTableId(Long tableId);

     Integer deleteByTableId(Long tableId);
}