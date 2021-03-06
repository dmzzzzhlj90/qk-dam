package com.qk.dm.metadata.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wangzp
 * @date 2021/7/30 18:20
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MtdClassifyVO implements Serializable {

  /** 名称 */
  private String name;

  /** 描述 */
  private String description;
}
