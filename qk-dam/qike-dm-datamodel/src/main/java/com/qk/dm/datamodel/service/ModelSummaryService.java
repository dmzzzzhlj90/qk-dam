package com.qk.dm.datamodel.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datamodel.params.dto.ModelSummaryDTO;
import com.qk.dm.datamodel.params.vo.ModelSummaryVO;

public interface ModelSummaryService {

    void insert(ModelSummaryDTO modelSummaryDTO);

    ModelSummaryVO detail(Long id);

    void update(Long id,ModelSummaryDTO modelSummaryDTO);

    void delete(String ids);

    PageResultVO<ModelSummaryVO> listPage(ModelSummaryDTO modelSummaryDTO);
}
