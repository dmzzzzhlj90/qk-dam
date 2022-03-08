package com.qk.dm.authority.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "qk_qx_resources_empower")
public class QkQxResourcesEmpower implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 主键id
   */
  @Id
  @Column(name = "id", nullable = false)
  private Long id;

  /**
   * 资源id
   */
  @Column(name = "resource_id")
  private Long resourceId;

  /**
   * 授权id
   */
  @Column(name = "empower_id")
  private Long empowerId;

  /**
   * 创建时间
   */
  @CreationTimestamp
  @Column(name = "gmt_create")
  private Date gmtCreate;

}
