package com.qk.dm.dataquality.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.dataquality.entity.DqcSchedulerRules;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface DqcSchedulerRulesRepository extends BaseRepository<DqcSchedulerRules, Long> {

    @Query(value = "select tables from qk_dqc_scheduler_rules",nativeQuery = true)
    List<String> findAllTables();

    @Query(value = "select fields from qk_dqc_scheduler_rules",nativeQuery = true)
    List<String> findAllFields();

    @Query(value = "select tables from qk_dqc_scheduler_rules where task_code in (?1)",nativeQuery = true)
    List<String> findAllTablesByTaskCode(Set<Long> codes);

    @Query(value = "select fields from qk_dqc_scheduler_rules where task_code in (?1)",nativeQuery = true)
    List<String> findAllFieldsByTaskCode(Set<Long> codes);
}