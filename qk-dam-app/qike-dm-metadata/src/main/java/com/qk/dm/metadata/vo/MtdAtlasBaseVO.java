package com.qk.dm.metadata.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/** atlas 基础元数据，用于列表展示 */
@Data
@Builder
public class MtdAtlasBaseVO {

  /** 数据唯一标识 */
  private String guid;
  /** 元数据类别 如mysql_table mysql_db hive_table等 */
  private String typeName;
  /** 展示的名字 */
  private String displayName;
  /** 限定名称 */
  private String qualifiedName;
  /** 创建时间 */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date createTime;
}
