package com.qk.dm.datamodel.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datamodel.params.dto.ModelFactTableDTO;
import com.qk.dm.datamodel.params.vo.ModelFactTableVO;

import java.util.List;

public interface ModelFactTableService {
    void insert(ModelFactTableDTO modelFactTableDTO);

    ModelFactTableVO detail(Long id);

    void update(ModelFactTableDTO modelFactTableDTO);

    void delete(String ids);

    PageResultVO<ModelFactTableVO> list(ModelFactTableDTO modelFactTableDTO);
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
