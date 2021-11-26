package com.qk.dm.dataquality.params.dto;

import com.qk.dam.jpa.pojo.Pagination;
import lombok.Data;

/**
 * @author shenpj
 * @date 2021/11/25 3:24 下午
 * @since 1.0.0
 */
@Data
public class DqcRuleTemplatePageDTO {

  Pagination pagination;

  /** 模板名称 */
  private String tempName;

  /** 分类目录 */
  private String dirId;

  /** 质量维度 1-完整性 2-唯一性 3-及时性 4-有效性 5-准确性 6-一致性 */
  private Integer dimensionId;

  /** 适用引擎 1-hive, 2-mysql, 适用多个以逗号分隔 */
  private String engineType;
}
