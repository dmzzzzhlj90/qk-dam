package com.qk.dm.datamodel.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datamodel.params.dto.ModelDimDTO;
import com.qk.dm.datamodel.params.vo.ModelDimVO;

public interface ModelDimService {
    void insert(ModelDimDTO modelDimDTO);

    ModelDimVO detail(Long id);

    void update(Long id,ModelDimDTO modelDimDTO);

    void delete(String ids);

    PageResultVO<ModelDimVO> listPage(ModelDimDTO modelDimDTO);
}
