package com.qk.dm.datamodel.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.datamodel.entity.ModelDimTableColumn;

import java.util.List;

public interface ModelDimTableColumnRepository extends BaseRepository<ModelDimTableColumn,Long> {

       void deleteByDimTableId(Long dimTableId);

       List<ModelDimTableColumn> findAllByDimTableId(Long dimTableId);
}