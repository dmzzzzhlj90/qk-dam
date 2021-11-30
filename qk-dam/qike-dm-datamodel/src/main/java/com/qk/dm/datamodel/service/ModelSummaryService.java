package com.qk.dm.datamodel.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datamodel.params.dto.ModelSummaryDTO;
import com.qk.dm.datamodel.params.dto.ModelSummaryInfoDTO;
import com.qk.dm.datamodel.params.vo.ModelSummaryVO;

public interface ModelSummaryService {

    void insert(ModelSummaryInfoDTO modelSummaryInfoDTO);

    ModelSummaryVO detail(Long id);

    void update(Long id,ModelSummaryInfoDTO modelSummaryInfoDTO);

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
