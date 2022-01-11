package com.qk.dm.datamodel.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datamodel.params.dto.ModelDimTableDTO;
import com.qk.dm.datamodel.params.vo.ModelDimTableVO;

public interface ModelDimTableService {
    void insert(ModelDimTableDTO modelDimTableDTO);

    ModelDimTableVO detail(Long id);

    void update(Long modelDimId,ModelDimTableDTO modelDimTableDTO);

    void delete(String ids);

    PageResultVO<ModelDimTableVO> list(ModelDimTableDTO modelDimTableDTO);

    /**
     * 预览SQL
     * @param tableId
     * @return
     */
    String previewSql(Long tableId);

}