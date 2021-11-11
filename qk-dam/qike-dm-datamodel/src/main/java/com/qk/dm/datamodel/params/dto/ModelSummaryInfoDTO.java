package com.qk.dm.datamodel.params.dto;

import lombok.Data;

import java.util.List;

@Data
public class ModelSummaryInfoDTO {
    private ModelSummaryDTO modelSummaryBase;
    private List<ModelSummaryIdcDTO> modelSummaryIdcList;
}
