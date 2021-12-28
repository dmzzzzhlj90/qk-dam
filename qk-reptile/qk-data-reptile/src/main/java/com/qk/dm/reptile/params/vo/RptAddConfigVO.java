package com.qk.dm.reptile.params.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RptAddConfigVO {
    private Long configId;
    private Long dimensionId;
    private List<String> columnCodeList;
}
