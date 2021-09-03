package com.qk.dm.metadata.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class MtdDbDetailVO {

  /** 负责人 */
  private String owner;
  /** 类型名称 */
  private String typeName;
  /** 备注信息 */
  private String comment;
  /** 限定名 */
  private String qualifiedName;
  /** 描述信息 */
  private String description;
  /** 创建时间 */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date createTime;
  /** 标签 */
  private String labels;
  /** 分类 */
  private String classification;
  /** 参考实体 */
  private List<Map<String, Object>> tables;
}
