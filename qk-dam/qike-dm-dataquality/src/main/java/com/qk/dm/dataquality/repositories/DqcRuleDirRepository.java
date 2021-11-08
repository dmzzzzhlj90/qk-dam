package com.qk.dm.dataquality.repositories;

import com.qk.dm.dataquality.entity.DqcRuleDir;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DqcRuleDirRepository extends JpaRepository<DqcRuleDir, Long>, JpaSpecificationExecutor<DqcRuleDir> {

}