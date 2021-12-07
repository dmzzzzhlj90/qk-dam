package com.qk.dm.reptile.repositories;

import com.qk.dm.reptile.entity.RptBaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RptBaseInfoRepository extends JpaRepository<RptBaseInfo, Long>, JpaSpecificationExecutor<RptBaseInfo> {

}