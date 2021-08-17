package com.qk.dm.dataservice.vo;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DasApiDirVO {

  /** 主键ID */
  private Integer id;

  /** API目录ID */
  @NotBlank(message = "API目录ID不能为空！")
  private String apiDirId;

  /** API目录名称 */
  @NotBlank(message = "API目录名称不能为空！")
  private String apiDirName;

  /** 父级id */
  @NotBlank(message = "目录父级id不能为空！")
  private String parentId;

  /** API目录层级 */
  @NotBlank(message = "API目录层级不能为空！")
  private String apiDirLevel;

  /** 描述 */
  private String description;
}
