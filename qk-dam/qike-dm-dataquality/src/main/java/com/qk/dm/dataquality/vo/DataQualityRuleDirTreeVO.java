package com.qk.dm.dataquality.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 数据质量__规则目录VO
 *
 * @author wjq
 * @date 20211108
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataQualityRuleDirTreeVO {

  private Integer id;

  private String dirDsdId;

  private String dirDsdName;

  private String parentId;

  private List<DataQualityRuleDirTreeVO> children;

}
