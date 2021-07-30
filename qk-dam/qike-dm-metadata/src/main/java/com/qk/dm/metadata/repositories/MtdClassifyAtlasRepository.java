package com.qk.dm.metadata.repositories;

import com.qk.dm.metadata.entity.MtdClassifyAtlas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MtdClassifyAtlasRepository extends JpaRepository<MtdClassifyAtlas, Long>, JpaSpecificationExecutor<MtdClassifyAtlas> {

}