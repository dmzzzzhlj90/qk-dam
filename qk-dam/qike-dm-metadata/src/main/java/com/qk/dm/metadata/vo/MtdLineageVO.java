package com.qk.dm.metadata.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MtdLineageVO {
    private MtdLineageDetailVO currentNode;
    private List<MtdLineageDetailVO> nodes;
    private List<ProcessVO> edges;
}
