package com.qk.dm.metadata.vo;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MtdLineageVO {
  private MtdLineageDetailVO currentNode;
  private List<MtdLineageDetailVO> nodes;
  private List<ProcessVO> edges;
}
