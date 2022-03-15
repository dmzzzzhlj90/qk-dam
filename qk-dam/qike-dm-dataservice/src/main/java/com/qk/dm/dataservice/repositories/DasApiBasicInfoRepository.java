package com.qk.dm.dataservice.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.dataservice.entity.DasApiBasicInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface DasApiBasicInfoRepository extends BaseRepository<DasApiBasicInfo, Long> {

    @Modifying
    @Query(" update DasApiBasicInfo set status=:status  where apiId in (:apiIdSet) ")
    void updateStatusByApiId(@Param("status") String status, @Param("apiIdSet") Set<String> apiIdSet);


}