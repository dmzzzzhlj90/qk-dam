package com.qk.dm.dataquality.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 数据质量_规则分类目录树VO
 *
 * @author wjq
 * @date 2021/11/08
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DqcRuleDirTreeVO {

  private String dirId;

  private String title;

  private String value;

  private String parentId;

  private List<DqcRuleDirTreeVO> children;

}
