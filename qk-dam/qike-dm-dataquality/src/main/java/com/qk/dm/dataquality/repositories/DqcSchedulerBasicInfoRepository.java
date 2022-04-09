package com.qk.dm.dataquality.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.dataquality.entity.DqcSchedulerBasicInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DqcSchedulerBasicInfoRepository extends BaseRepository<DqcSchedulerBasicInfo, Long> {

    @Modifying
    @Query(" UPDATE DqcSchedulerBasicInfo set processDefinitionCode = :processDefinitionCode where jobId = :jobId ")
    void updateProcessDefinitionIdByJobId(@Param("processDefinitionCode") Long processDefinitionCode, @Param("jobId") String jobId);

}