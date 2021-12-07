package com.qk.dm.reptile.repositories;

import com.qk.dm.reptile.entity.RptDimensionColumnInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RptDimensionColumnInfoRepository extends JpaRepository<RptDimensionColumnInfo, Long>, JpaSpecificationExecutor<RptDimensionColumnInfo> {

}