package com.qk.dm.authority.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 资源和API表
 */
@Data
@Entity
@Table(name = "qk_qx_resources")
public class QxResources implements Serializable {

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
  @Column(name = "name")
  private String name;

  /**
   * 网址路径
   */
  @Column(name = "path")
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
   * 父级id（API类型fid默认为-1）
   */
  @Column(name = "pid")
  private Long pid;

  /**
   * 服务UUID
   */
  @Column(name = "service_id")
  private String serviceId;

  /**
   * 0标识API，1表示资源
   */
  @Column(name = "type")
  private Integer type;

  /**
   * 资源uuid
   */
  @Column(name = "resources_id")
  private String resourcesid;

}
