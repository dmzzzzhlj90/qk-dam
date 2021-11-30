package com.qk.dm.datamodel.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.datamodel.entity.ModelFactColumn;

import java.util.List;

public interface ModelFactColumnRepository extends BaseRepository<ModelFactColumn, Long> {
      void deleteByFactId(Long factId);

      List<ModelFactColumn> findAllByFactId(Long factId);
}