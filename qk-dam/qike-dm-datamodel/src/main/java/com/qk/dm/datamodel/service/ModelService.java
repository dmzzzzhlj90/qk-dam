package com.qk.dm.datamodel.service;

import com.qk.dm.datamodel.params.dto.ModelDTO;
import com.qk.dm.datamodel.params.vo.ModelVO;

import java.util.List;

/**
 * 数据模型（逻辑模型、物理模型）
 */
public interface ModelService {
    /**
     * 添加模型
     * @param modelDTO
     */
    void insert(ModelDTO modelDTO);

    /**
     * 获取模型列表
     * @return
     */
    List<ModelVO> getList();
}
