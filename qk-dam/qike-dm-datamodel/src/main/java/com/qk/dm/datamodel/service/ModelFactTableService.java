package com.qk.dm.datamodel.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datamodel.params.dto.ModelFactTableDTO;
import com.qk.dm.datamodel.params.vo.ModelFactTableVO;

public interface ModelFactTableService {
    void insert(ModelFactTableDTO modelFactTableDTO);

    ModelFactTableVO detail(Long id);

    void update(Long id,ModelFactTableDTO modelFactTableDTO);

    void delete(String ids);

    PageResultVO<ModelFactTableVO> list(ModelFactTableDTO modelFactTableDTO);
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
