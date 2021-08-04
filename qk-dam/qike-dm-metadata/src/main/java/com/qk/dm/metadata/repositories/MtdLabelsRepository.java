package com.qk.dm.metadata.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.metadata.entity.MtdLabels;

public interface MtdLabelsRepository extends BaseRepository<MtdLabels, Long> {
    /**
     * 根据名称查询
     *
     * @param name
     * @return
     */
    MtdLabels findByName(String name);
}