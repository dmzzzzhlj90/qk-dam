package com.qk.dm.metadata.vo;

import java.util.List;

public class MtdApiVO {
  private List<MtdApiDbVO> entities;

  private List<MtdAttributesVO> columns;

  private List<MtdTablesVO> tables;

  public List<MtdApiDbVO> getEntities() {
    return entities;
  }

  public void setEntities(List<MtdApiDbVO> entities) {
    this.entities = entities;
  }

  public List<MtdAttributesVO> getColumns() {
    return columns;
  }

  public void setColumns(List<MtdAttributesVO> columns) {
    this.columns = columns;
  }

  public List<MtdTablesVO> getTables() {
    return tables;
  }

  public void setTables(List<MtdTablesVO> tables) {
    this.tables = tables;
  }
}
