package com.qk.dm.datamodel.service;

import com.qk.dm.datamodel.params.dto.ModelSummaryIdcDTO;
import com.qk.dm.datamodel.params.vo.ModelSummaryIdcVO;

import java.util.List;

public interface ModelSummaryIdcService {

    void insert(List<ModelSummaryIdcDTO> modelSummaryIdcDTOList);

    ModelSummaryIdcVO detail(Long id);

    void update(Long summaryId,List<ModelSummaryIdcDTO> modelSummaryIdcDTOList);

    void delete(String summaryIds);

    List<ModelSummaryIdcVO> list(Long summaryId);
}
