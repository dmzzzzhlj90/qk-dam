package com.qk.dm.datacollect.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 创建任务vo
 * @author zys
 * @date 2022/4/20 10:50
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DctBaseInfoVO {
  /**描述*/
  private String description;

  /** 数据连接名称 */
  private String dataSourceName;

  /** 表名称 */
  private List<String> tableList;

  /** 全选所有的表时候赋值为（all）*/
  private String allNums;

  /**应用名称*/
  private String applicationName;

  /**显示名称*/
  private String displayName;

  /**创建人名称*/
  private String owner;

  /** 数据库名称 */
  private String db;

  /**采集元数据策略（1：仅更新、2：仅添加、3：既更新又添加、4：忽略更新添加）*/
  private String strategy;
}