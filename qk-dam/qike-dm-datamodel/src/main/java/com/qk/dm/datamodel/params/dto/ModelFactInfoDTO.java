package com.qk.dm.datamodel.params.dto;

import lombok.Data;

import java.util.List;

@Data
public class ModelFactInfoDTO {

    private ModelFactTableDTO modelFactTableBase;

    private List<ModelFactColumnDTO> modelFactColumnList;
}
