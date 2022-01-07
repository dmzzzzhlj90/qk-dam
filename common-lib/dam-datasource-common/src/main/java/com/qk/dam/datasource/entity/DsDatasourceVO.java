package com.qk.dam.datasource.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zys
 * @date 20210729
 * @since 1.0.0 数据源管理__数据源连接VO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DsDatasourceVO {
  /** 数据源主键id */
  private Integer id;

  /** 数据源名称（返回值显示） */
  private String dataSourceName;

  /** 所属系统（返回值显示） */
  private String homeSystem;

  /** 数据源连接信息（返回值显示） */
  private Object connectBasicInfo;

  /** 连接方式 （返回值显示） */
  private String linkType;

  /** 标签（名称用逗号隔开） */
  private String tagNames;

  /** 标签id组合（ID用逗号隔开） */
  private String tagIds;

  /** 用途 */
  private String use;

  /** 部署地 */
  private String deployPlace;

  /** 状态设置状态值 */
  private Integer status;

  /** 修改时间 （返回值显示） */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date gmtModified;

  /** 创建时间（返回值显示） */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date gmtCreate;

  /** 创建人id（后期设置为必填）（返回值显示） */
  private String createUserid;

  /** 修改人id */
  private String updateUserid;

  /** 删除标识(0-保留 1-删除，后期设置为非空，默认为0) */
  private Integer delFlag;

  /** 多租户标识 */
  private String versionConsumer;

  /** 目录归属id */
  private String dicId;

  /** 备注 */
  private String remark;
}
