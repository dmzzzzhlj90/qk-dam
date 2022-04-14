package com.qk.dm.dataingestion.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.dataingestion.entity.DisRuleClassification;

import java.util.List;

public interface DisRuleClassificationRepository extends BaseRepository<DisRuleClassification, Long> {
  List<DisRuleClassification> findAllByParentId(Long parentId);
}