package com.qk.dm.datamodel.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datamodel.params.dto.ModelSummaryDTO;
import com.qk.dm.datamodel.params.vo.ModelSummaryVO;

public interface ModelSummaryService {

    void insert(ModelSummaryDTO modelSummaryDTO);

    ModelSummaryVO detail(Long id);

    void update(Long id,ModelSummaryDTO modelSummaryDTO);

    void delete(String ids);

    PageResultVO<ModelSummaryVO> list(ModelSummaryDTO modelSummaryDTO);
    /**
     * 发布
     * @param ids
     */
    void publish(String ids);

    /**
     * 下线
     * @param ids
     */
    void offline(String ids);

    /**
     * 预览SQL
     * @param tableId
     * @return
     */
    String previewSql(Long tableId);
}
