package com.qk.dm.datamodel.params.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ModelDimTableDetailVO extends ModelDimTableVO{

    private List<ModelDimTableColumnVO> columnList;
}
