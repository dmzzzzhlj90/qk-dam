package com.qk.dm.metadata.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Builder
public class MtdDbDetailVO extends MtdAtlasBaseDetailVO{
  /** 参考实体 */
  private List<Map<String, Object>> tables;

  public List<Map<String, Object>> getTables() {
    return tables;
  }

  public void setTables(List<Map<String, Object>> tables) {
    this.tables = tables;
  }
}
