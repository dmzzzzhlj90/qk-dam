package com.qk.dam.metedata.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MtdApi {
  private List<MtdApiDb> entities;

  private List<MtdAttributes> columns;

  private List<MtdTables> tables;

}
