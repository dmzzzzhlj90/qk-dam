package com.qk.dm.metadata.vo;

import java.util.Date;

import com.qk.dam.jpa.pojo.Pagination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wangzp
 * @date 2021/7/30 18:20
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MtdClassifyVO {

  private Pagination pagination;

  private Long id;

  /** 名称 */
  private String name;

  /** 描述 */
  private String description;

  /** 创建时间 */
  private Date gmtCreate;
}
