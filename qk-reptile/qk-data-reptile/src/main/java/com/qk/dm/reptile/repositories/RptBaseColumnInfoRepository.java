package com.qk.dm.reptile.repositories;

import com.qk.dm.reptile.entity.RptBaseColumnInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RptBaseColumnInfoRepository extends JpaRepository<RptBaseColumnInfo, Long>, JpaSpecificationExecutor<RptBaseColumnInfo> {

}