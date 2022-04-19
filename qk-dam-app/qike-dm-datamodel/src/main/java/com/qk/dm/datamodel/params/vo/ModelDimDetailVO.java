package com.qk.dm.datamodel.params.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ModelDimDetailVO extends ModelDimVO{

    /**
     * 维度字段信息
     */
    private List<ModelDimColumnVO> columnList;
}
