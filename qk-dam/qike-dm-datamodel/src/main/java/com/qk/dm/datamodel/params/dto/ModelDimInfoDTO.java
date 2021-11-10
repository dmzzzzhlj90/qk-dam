package com.qk.dm.datamodel.params.dto;


import lombok.Data;

import java.util.List;

/**
 * 维度信息
 */
@Data
public class ModelDimInfoDTO {
    /**
     * 维度基础信息
     */
    private ModelDimDTO modelDimBase;
    /**
     * 维度字段信息
     */
    private List<ModelDimColumnDTO> modelDimColumnList;
}
