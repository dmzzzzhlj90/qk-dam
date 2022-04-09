package com.qk.dm.metadata.vo;

import java.util.List;
import java.util.Map;
import lombok.Builder;

@Builder
public class MtdDbDetailVO extends MtdAtlasBaseDetailVO {
  /** 参考实体 */
  private List<Map<String, Object>> tables;

  public List<Map<String, Object>> getTables() {
    return tables;
  }

  public void setTables(List<Map<String, Object>> tables) {
    this.tables = tables;
  }
}
