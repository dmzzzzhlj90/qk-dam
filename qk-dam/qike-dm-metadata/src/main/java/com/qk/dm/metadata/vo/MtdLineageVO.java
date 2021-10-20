package com.qk.dm.metadata.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MtdLineageVO {
    private MtdLineageDetailVO currentNode;
    private List<MtdLineageDetailVO> nodes;
    private List<ProcessVO> edges;
}
