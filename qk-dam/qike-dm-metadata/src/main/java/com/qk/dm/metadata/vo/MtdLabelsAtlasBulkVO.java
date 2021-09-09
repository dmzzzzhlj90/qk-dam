package com.qk.dm.metadata.vo;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MtdLabelsAtlasBulkVO implements Serializable {

  /** 元数据标识 */
  @NotNull(message = "元数据标识不能为空！")
  String[] guids;

  /** 元数据标签 */
  @NotBlank(message = "元数据标签不能为空！")
  String labels;
}
