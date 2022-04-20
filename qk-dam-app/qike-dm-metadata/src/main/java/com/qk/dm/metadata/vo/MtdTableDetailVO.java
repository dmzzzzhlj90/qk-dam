package com.qk.dm.metadata.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Builder
public class MtdTableDetailVO {
  /** 负责人 */
  private String owner;
  /** 类型名称 */
  private String typeName;
  /** 表行数 */
  private String tableRows;
  /** 数据长度 */
  private String dataLength;
  /** 索引长度 */
  private String indexLength;
  /** 备注信息 */
  private String comment;
  /** 限定名 */
  private String qualifiedName;
  /** 描述信息 */
  private String description;
  /** 标签 */
  private String labels;
  /** 分类 */
  private String classification;
  /** 创建时间 */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date createTime;
  /** db信息 */
  private MtdDbInfoVO db;

  /** 参考实体 */
  private List<Map<String, Object>> columns;
}
