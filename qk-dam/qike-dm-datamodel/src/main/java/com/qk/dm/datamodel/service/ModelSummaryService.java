package com.qk.dm.datamodel.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datamodel.params.dto.ModelSummaryDTO;
import com.qk.dm.datamodel.params.vo.ModelSummaryVO;

import java.util.List;

public interface ModelSummaryService {

    void insert(ModelSummaryDTO modelSummaryDTO);

    ModelSummaryVO detail(Long id);

    void update(ModelSummaryDTO modelSummaryDTO);

    void delete(String ids);

    PageResultVO<ModelSummaryVO> list(ModelSummaryDTO modelSummaryDTO);
    /**
     * 发布
     * @param idList
     */
    void publish(List<Long> idList);

    /**
     * 下线
     * @param idList
     */
    void offline(List<Long> idList);

    /**
     * 预览SQL
     * @param tableId
     * @return
     */
    String previewSql(Long tableId);
}
