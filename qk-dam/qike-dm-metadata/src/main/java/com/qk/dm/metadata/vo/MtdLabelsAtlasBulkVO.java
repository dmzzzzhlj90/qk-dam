package com.qk.dm.metadata.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

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
