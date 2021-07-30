package com.qk.dm.metadata.repositories;

import com.qk.dm.metadata.entity.MtdClassify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MtdClassifyRepository extends JpaRepository<MtdClassify, Long>, JpaSpecificationExecutor<MtdClassify> {

}