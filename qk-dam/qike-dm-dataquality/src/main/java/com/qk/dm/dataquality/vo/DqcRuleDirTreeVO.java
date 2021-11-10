package com.qk.dm.dataquality.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 数据质量_规则分类目录TreeVO
 *
 * @author wjq
 * @date 20211108
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DqcRuleDirTreeVO {

  private Integer id;

  private String ruleDirId;

  private String ruleDirName;

  private String parentId;

  private List<DqcRuleDirTreeVO> children;

}
