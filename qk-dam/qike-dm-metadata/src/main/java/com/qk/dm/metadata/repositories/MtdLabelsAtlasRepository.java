package com.qk.dm.metadata.repositories;

import com.qk.dm.metadata.entity.MtdLabelsAtlas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MtdLabelsAtlasRepository extends JpaRepository<MtdLabelsAtlas, Long>, JpaSpecificationExecutor<MtdLabelsAtlas> {

}