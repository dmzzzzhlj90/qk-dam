package com.qk.dm.datamodel.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.datamodel.entity.ModelDimColumn;
import java.util.List;

public interface ModelDimColumnRepository extends BaseRepository<ModelDimColumn, Long> {

    void deleteByDimId(Long dimId);

    List<ModelDimColumn> findAllByDimId(Long dimId);
}