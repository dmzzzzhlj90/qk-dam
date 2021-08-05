package com.qk.dm.metadata.vo;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MtdLabelsVO implements Serializable {

  /** 名称 */
  @NotBlank(message = "名称不能为空！")
  private String name;

  /** 描述 */
  private String describe;
}
