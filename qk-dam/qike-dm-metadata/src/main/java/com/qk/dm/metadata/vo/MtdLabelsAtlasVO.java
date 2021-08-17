package com.qk.dm.metadata.vo;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class MtdLabelsAtlasVO implements Serializable {

  private Long id;

  /** 元数据标识 */
  @NotBlank(message = "元数据标识不能为空！")
  private String guid;

  /** 元数据标签 */
  private String labels;

  public MtdLabelsAtlasVO(Long id, String guid, String labels) {
    this.id = id;
    this.guid = guid;
    this.labels = labels;
  }

  public MtdLabelsAtlasVO(String guid, String labels) {
    this.guid = guid;
    this.labels = labels;
  }
}
