package com.qk.dm.metadata.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MtdLabelsVO implements Serializable {

  /** 名称 label should contain alphanumeric characters, _ or - */
  @NotBlank(message = "名称不能为空！")
  private String name;

  /** 描述 */
  private String description;
}
