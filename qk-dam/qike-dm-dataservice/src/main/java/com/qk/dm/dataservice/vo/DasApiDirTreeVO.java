package com.qk.dm.dataservice.vo;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * API目录树结构展示VO
 *
 * @author wjq
 * @date 20210907
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DasApiDirTreeVO {

  private Long id;

  private String dirId;

  private String title;

  private String value;

  private String parentId;

  private List<DasApiDirTreeVO> children;

}
