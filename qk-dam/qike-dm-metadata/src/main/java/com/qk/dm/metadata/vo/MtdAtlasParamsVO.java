package com.qk.dm.metadata.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MtdAtlasParamsVO {

    private String query;

    private String typeName;

    private String classification;

    private int limit = 20;

    private int offse = 0;
}
