package com.qk.dam.metedata.entity;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MtdApi {
  private List<MtdApiDb> entities;

  private List<MtdAttributes> columns;

  private List<MtdTables> tables;
  /**
   * 表的数据量
   */
  private String dataLength;
}
