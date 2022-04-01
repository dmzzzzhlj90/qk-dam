package com.qk.dm.authority.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 权限服务表
 */
@Data
@Entity
@Table(name = "qk_qx_service")
public class QxService implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 主键id
   */
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * 服务名称
   */
  @Column(name = "service_name")
  private String serviceName;

  /**
   * 描述
   */
  @Column(name = "description")
  private String description;

  /**
   * 创建人id
   */
  @Column(name = "create_userid")
  private Long createUserid;

  /**
   * 修改人id
   */
  @Column(name = "update_userid")
  private Long updateUserid;

  /**
   * 创建人名称
   */
  @Column(name = "create_username")
  private String createUsername;

  /**
   * 修改人名称
   */
  @Column(name = "update_username")
  private String updateUsername;

  /**
   * 修改时间
   */
  @UpdateTimestamp
  @Column(name = "gmt_modified")
  private Date gmtModified;

  /**
   * 创建时间
   */
  @CreationTimestamp
  @Column(name = "gmt_create")
  private Date gmtCreate;

  /**
   * 服务名称
   */
  //@Column(name = "service_sign")
  //private String serviceSign;

  /**
   * 区域id（项目id）
   */
  @Column(name = "region_id")
  private String redionid;

  /**
   * 服务uuid
   */
  @Column(name = "service_id")
  private String serviceid;

  /**
   * 微服务线上地址
   */
  @Column(name = "entry")
  private String entry;

  /**
   * 微服务项目名(path)
   */
  @Column(name = "path")
  private String path;

  /**
   * microapp（微应用唯一标识）
   */
  @Column(name = "micro_app")
  private String microApp;
}
