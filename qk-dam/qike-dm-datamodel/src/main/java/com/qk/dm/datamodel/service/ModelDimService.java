package com.qk.dm.datamodel.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datamodel.params.dto.ModelDimDTO;
import com.qk.dm.datamodel.params.dto.ModelDimQueryDTO;
import com.qk.dm.datamodel.params.vo.ModelDimDetailVO;
import com.qk.dm.datamodel.params.vo.ModelDimVO;

import java.util.List;

public interface ModelDimService {
    void insert(ModelDimDTO modelDimDTO);

    ModelDimDetailVO detail(Long id);

    void update(ModelDimDTO modelDimDTO);

    void delete(String ids);

    PageResultVO<ModelDimVO> list(ModelDimQueryDTO modelDimQueryDTO);

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
}
