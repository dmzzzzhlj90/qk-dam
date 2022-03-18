package com.qk.dm.dataquality.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.dataquality.entity.DqcSchedulerResult;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DqcSchedulerResultRepository extends BaseRepository<DqcSchedulerResult, Long> {

    @Query(value = " SELECT * FROM qk_dqc_scheduler_result WHERE task_code = ?1  order by gmt_create desc limit 1", nativeQuery = true)
    DqcSchedulerResult findOneByLastTime(@Param("taskCode") Long taskCode);





}