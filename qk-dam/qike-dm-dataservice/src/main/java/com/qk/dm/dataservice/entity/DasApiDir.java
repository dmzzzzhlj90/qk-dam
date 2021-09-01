package com.qk.dm.dataservice.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "qk_das_api_dir")
public class DasApiDir implements Serializable {

  private static final long serialVersionUID = 1L;

  /** 主键ID */
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** API目录ID */
  @Column(name = "api_dir_id", nullable = false)
  private String apiDirId;

  /** API目录名称 */
  @Column(name = "api_dir_name", nullable = false)
  private String apiDirName;

  /** API目录层级 */
  @Column(name = "api_dir_level", nullable = false)
  private String apiDirLevel;

  /** 目录父级ID */
  @Column(name = "parent_id", nullable = false)
  private String parentId;

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