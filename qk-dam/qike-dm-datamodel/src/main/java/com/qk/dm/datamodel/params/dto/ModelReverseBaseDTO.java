package com.qk.dm.datamodel.params.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 逆向数据库入参DTO
 * @author zys
 * @date 2021/11/23 14:48
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModelReverseBaseDTO {
  /**
   * 主题名称
   */
  @NotBlank(message = "主题名称")
  private String theme;

  /**
   * 数据连接
   */
  @NotBlank(message = "数据连接")
  private String dataConnection;

  /**
   * 数据库名称
   */
  @NotBlank(message = "数据库名称")
  private String databaseName;

  /**
   *数据源连接名称
   */
  @NotBlank(message = "数据源连接名称")
  private String dataSourceName;

  /**
   * 数据源连接id
   */
  @NotNull(message = "数据源连接id")
  private Integer dataSourceId;

  /**
   * 所属主题id
   */
  @NotNull(message = "所属主题id")
  private Long themeId;

  /**
   * 所属层级id
   */
  @NotNull(message = "所属层级id")
  private Long modelId;

  /**
   * 更新已有表（0表示更新，1表示不更新）
   */
  @NotNull(message = "更新已有表")
  private Integer replace;

  /**
   * 需要逆向的表名集合
   */
  @NotEmpty(message = "逆向表明集合不能为空")
  private List<String> tableList;
}