package com.qk.dm.datastandards.vo;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DsdDirVO {

  /** 主键ID */
  private Integer id;

  /** 数据标准分类编号 */
  //    @NotBlank(message = "数据标准分类ID不能为空！")
  private String dirDsdId;

  /** 数据标准分类名称 */
  @NotBlank(message = "数据标准分类名称不能为空！")
  private String dirDsdName;

  /** 父级id */
  @NotBlank(message = "目录父级id不能为空！")
  private String parentId;

  /** 目录节点层级 */
  @NotBlank(message = "目录节点层级不能为空！")
  private String dsdDirLevel;

  /** 描述 */
  private String description;
}
