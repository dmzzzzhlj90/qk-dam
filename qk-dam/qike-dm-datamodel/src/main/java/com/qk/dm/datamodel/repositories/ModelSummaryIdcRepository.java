package com.qk.dm.datamodel.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.datamodel.entity.ModelSummaryIdc;

import java.util.List;

public interface ModelSummaryIdcRepository extends BaseRepository<ModelSummaryIdc, Long> {

    void deleteBySummaryId(Long summaryId);

    List<ModelSummaryIdc> findAllBySummaryId(Long summaryId);

}