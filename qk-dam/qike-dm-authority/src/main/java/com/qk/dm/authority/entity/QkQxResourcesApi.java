package com.qk.dm.authority.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 访问（api）表
 */
@Data
@Entity
@Table(name = "qk_qx_resources_api")
public class QkQxResourcesApi implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 主键id
   */
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * 资源（API）名称
   */
  @Column(name = "name", nullable = false)
  private String name;

  /**
   * 网址路径
   */
  @Column(name = "path", nullable = false)
  private String path;

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
  @Column(name = "create_name")
  private String createName;

  /**
   * 修改人名称
   */
  @Column(name = "update_name")
  private String updateName;

  /**
   * 创建时间
   */
  @CreationTimestamp
  @Column(name = "gmt_create")
  private Date gmtCreate;

  /**
   * 修改时间
   */
  @UpdateTimestamp
  @Column(name = "gmt_modified")
  private Date gmtModified;

  /**
   * 服务的uuid
   */
  @Column(name = "service_id",nullable = false)
  private String serviceId;

  /**
   * api的uuid
   */
  @Column(name = "resources_id")
  private String resourcesId;

}
