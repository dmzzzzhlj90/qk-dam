package com.qk.dm.datamodel.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.datamodel.entity.ModelPhysicalRelation;

import java.util.List;

public interface ModelPhysicalRelationRepository extends BaseRepository<ModelPhysicalRelation, Long> {

   List<ModelPhysicalRelation> findAllByTableId(Long tableId);

   Integer deleteByTableId(Long tableId);
}