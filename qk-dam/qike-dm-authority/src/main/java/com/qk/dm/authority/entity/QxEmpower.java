package com.qk.dm.authority.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 授权表
 */
@Data
@Entity
@Table(name = "qk_qx_empower")
public class QxEmpower implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 主键id
   */
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * 被授权主体类型
   */
  @Column(name = "empoer_type")
  private String empoerType;

  /**
   * 被授权主体名称
   */
  @Column(name = "empoer_name")
  private String empoerName;

  /**
   * 被授权主体id
   */
  @Column(name = "empoer_id")
  private String empoerId;

  /**
   * 授权类型(默认0标识拒绝,1标识允许)
   */
  @Column(name = "type")
  private Integer type;

  /**
   * 被授权类型（默认0表示API,1标识资源）
   */
  @Column(name = "power_type")
  private Integer powerType;

  /**
   * 授权资源id（根据“，”隔开）
   */
  @Column(name = "resource_sign")
  private String resourceSign;

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
   * 服务id
   */
  @Column(name = "service_id")
  private Long serviceId;

  /**
   * 授权信息uuid
   */
  @Column(name = "empower_id")
  private String empowerId;

  /**
   * 当授权主体为角色时候，存角色对应的客户端名称
   */
  @Column(name = "client_name")
  private String clientName;
}
