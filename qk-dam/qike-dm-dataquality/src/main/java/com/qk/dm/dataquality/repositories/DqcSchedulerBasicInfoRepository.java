package com.qk.dm.dataquality.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.dataquality.entity.DqcSchedulerBasicInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DqcSchedulerBasicInfoRepository extends BaseRepository<DqcSchedulerBasicInfo, Long> {


    @Modifying
    @Query(" UPDATE DqcSchedulerBasicInfo set processDefinitionId = :processDefinitionId where jobId = :jobId ")
    void updateProcessDefinitionIdByJobId(@Param("processDefinitionId") Integer processDefinitionId, @Param("jobId") String jobId);

}