package com.qk.dm.metadata.vo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Builder
public class MtdLabelsAtlasVO implements Serializable {

  /** 元数据标识 */
  @NotBlank(message = "元数据标识不能为空！")
  private String guid;

  /** 元数据标签 */
  private String labels;

  public MtdLabelsAtlasVO(String guid, String labels) {
    this.guid = guid;
    this.labels = labels;
  }
}
