package com.qk.dm.datamodel.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datamodel.params.dto.ModelDimTableDTO;
import com.qk.dm.datamodel.params.vo.ModelDimTableVO;

public interface ModelDimTableService {
    void insert(ModelDimTableDTO modelDimTableDTO);

    ModelDimTableVO detail(Long id);

    void update(Long id,ModelDimTableDTO modelDimTableDTO);

    void delete(String ids);

    PageResultVO<ModelDimTableVO> listPage(ModelDimTableDTO modelDimTableDTO);

}
