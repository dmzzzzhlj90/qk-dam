package com.qk.dm.datamodel.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.datamodel.entity.ModelSql;

public interface ModelSqlRepository extends BaseRepository<ModelSql, Long> {
   ModelSql findByTableId(Long tableId);

   void deleteByTableId(Long tableId);

   ModelSql findByTableIdAndType(Long id, int type);

}