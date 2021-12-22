package com.qk.dm.dataquality.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.dataquality.entity.DqcSchedulerRules;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DqcSchedulerRulesRepository extends BaseRepository<DqcSchedulerRules, Long> {

    @Query(value = "select tables from qk_dqc_scheduler_rules",nativeQuery = true)
    List<String> findAllByTables();

    @Query(value = "select fields from qk_dqc_scheduler_rules",nativeQuery = true)
    List<String> findAllByFields();
}