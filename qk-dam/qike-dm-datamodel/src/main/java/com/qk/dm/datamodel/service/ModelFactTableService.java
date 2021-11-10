package com.qk.dm.datamodel.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datamodel.params.dto.ModelFactInfoDTO;
import com.qk.dm.datamodel.params.dto.ModelFactTableDTO;
import com.qk.dm.datamodel.params.vo.ModelFactTableVO;

public interface ModelFactTableService {
    void insert(ModelFactInfoDTO modelFactInfoDTO);

    ModelFactTableVO detail(Long id);

    void update(Long id,ModelFactTableDTO modelFactTableDTO);

    void delete(String ids);

    PageResultVO<ModelFactTableVO> listPage(ModelFactTableDTO modelFactTableDTO);
}
