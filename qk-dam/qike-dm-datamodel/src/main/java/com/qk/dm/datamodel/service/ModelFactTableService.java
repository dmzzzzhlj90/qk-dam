package com.qk.dm.datamodel.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datamodel.params.dto.ModelFactQueryDTO;
import com.qk.dm.datamodel.params.dto.ModelFactTableDTO;
import com.qk.dm.datamodel.params.vo.ModelFactTableVO;

import java.util.List;

public interface ModelFactTableService {
    void insert(ModelFactTableDTO modelFactTableDTO);

    ModelFactTableVO detail(Long id);

    void update(ModelFactTableDTO modelFactTableDTO);

    void delete(String ids);

    PageResultVO<ModelFactTableVO> list(ModelFactQueryDTO modelFactQueryDTO);
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
