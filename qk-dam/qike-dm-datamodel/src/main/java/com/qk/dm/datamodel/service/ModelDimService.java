package com.qk.dm.datamodel.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datamodel.params.dto.ModelDimDTO;
import com.qk.dm.datamodel.params.dto.ModelDimInfoDTO;
import com.qk.dm.datamodel.params.vo.ModelDimVO;

public interface ModelDimService {
    void insert(ModelDimInfoDTO modelDimInfoDTO);

    ModelDimVO detail(Long id);

    void update(Long id,ModelDimInfoDTO modelDimInfoDTO);

    void delete(String ids);

    PageResultVO<ModelDimVO> list(ModelDimDTO modelDimDTO);

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
