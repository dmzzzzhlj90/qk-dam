package com.qk.dm.metadata.vo;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MtdLabelsVO implements Serializable {

  /** 名称 label should contain alphanumeric characters, _ or - */
  @NotBlank(message = "名称不能为空！")
  @Pattern(
      regexp = "^(?=.*?[a-zA-Z])(?=.*?[0-9])(?=.*?[_-])[a-zA-Z0-9_-]+$",
      message = "label should contain alphanumeric characters, _ or -")
  private String name;

  /** 描述 */
  private String description;
}
