package com.qk.dm.reptile.repositories;

import com.qk.dm.reptile.entity.RptDimensionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RptDimensionInfoRepository extends JpaRepository<RptDimensionInfo, Long>, JpaSpecificationExecutor<RptDimensionInfo> {

}