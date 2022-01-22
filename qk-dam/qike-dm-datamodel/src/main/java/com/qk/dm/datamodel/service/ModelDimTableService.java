package com.qk.dm.datamodel.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datamodel.params.dto.ModelDimTableDTO;
import com.qk.dm.datamodel.params.dto.ModelDimTableQueryDTO;
import com.qk.dm.datamodel.params.vo.ModelDimTableDetailVO;
import com.qk.dm.datamodel.params.vo.ModelDimTableVO;

public interface ModelDimTableService {
    void insert(ModelDimTableDTO modelDimTableDTO);

    ModelDimTableDetailVO detail(Long id);

    void updateDim(Long modelDimId,ModelDimTableDTO modelDimTableDTO);

    void update(Long id,ModelDimTableDTO modelDimTableDTO);

    void delete(String ids);

    PageResultVO<ModelDimTableVO> list(ModelDimTableQueryDTO modelDimTableQueryDTO);

    /**
     * 预览SQL
     * @param tableId
     * @return
     */
    String previewSql(Long tableId);

}
