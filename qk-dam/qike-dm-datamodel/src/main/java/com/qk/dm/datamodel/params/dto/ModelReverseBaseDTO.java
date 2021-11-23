package com.qk.dm.datamodel.params.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 逆向数据库如惨DTO
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
   * 所属层级id
   */
  @NotBlank(message = "所属层级id")
  private Long modelId;

  /**
   * 所属主题id
   */
  @NotBlank(message = "所属主题id")
  private Long themeId;

  /**
   * 主题
   */
  @NotBlank(message = "主题")
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
   * 更新已有表（0表示更新，1表示不更新）
   */
  @NotBlank(message = "更新已有表")
  private Integer replace;

  /**
   * 需要逆向的表名集合
   */
  @NotBlank(message = "逆向表明集合不能为空")
  private List<String> tableList;

  /**
   * 0草稿 1已发布2 已下线
   */
  @NotBlank(message = "状态")
  private Integer status;

  /**
   * 数据库和系统定义的sql是否同步，0表示为同步，1表示同步
   */
  @NotBlank(message = "数据库和系统定义的sql是否同步")
  private Integer syncStatus;
}