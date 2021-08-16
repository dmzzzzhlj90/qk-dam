package com.qk.dm.dataservice.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "qk_das_application_management")
public class DasApplicationManagement implements Serializable {

  private static final long serialVersionUID = 1L;

  /** 主键ID */
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** 应用ID */
  @Column(name = "app_id", nullable = false)
  private String appId;

  /** 应用名称 */
  @Column(name = "app_name", nullable = false)
  private String appName;

  /** 应用类型 */
  @Column(name = "app_type", nullable = false)
  private String appType;

  /** API关联ID */
  @Column(name = "api_id", nullable = false)
  private String apiId;

  /** 描述 */
  @Column(name = "description")
  private String description;

  /** 创建时间 */
  @Column(name = "gmt_create")
  private Date gmtCreate;

  /** 修改时间 */
  @Column(name = "gmt_modified")
  private Date gmtModified;

  /** 是否删除；0逻辑删除，1物理删除； */
  @Column(name = "del_flag", nullable = false)
  private Integer delFlag;
}
