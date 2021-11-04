package com.qk.dm.metadata.vo;

import javax.validation.constraints.NotBlank;
import lombok.Data;

/** 数据血缘参数 */
@Data
public class MtdLineageParamsVO {
  @NotBlank(message = "guid不能为空！")
  private String guid;

  private String direction;
  private Integer depth;
}
