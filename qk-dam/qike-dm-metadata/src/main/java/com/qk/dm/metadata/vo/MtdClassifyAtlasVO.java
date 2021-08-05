package com.qk.dm.metadata.vo;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wangzp
 * @date 2021/7/31 11:34
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MtdClassifyAtlasVO {

  private Pagination pagination;

  private Long id;

  /** 元数据标识 */
  private String guid;

  /** 分类 */
  private String classify;

  /** 创建时间 */
  private Date gmtCreate;
}
