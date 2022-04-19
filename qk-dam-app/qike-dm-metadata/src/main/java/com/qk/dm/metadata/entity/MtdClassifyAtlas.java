package com.qk.dm.metadata.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
@Table(name = "qk_mtd_classify_atlas")
public class MtdClassifyAtlas implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** 元数据标识 */
  @Column(name = "guid", nullable = false)
  private String guid;

  /** 分类 */
  @Column(name = "classify", nullable = false)
  private String classify;

  /** 创建时间 */
  @Column(name = "gmt_create")
  @CreationTimestamp
  private Date gmtCreate;

  /** 修改时间 */
  @Column(name = "gmt_modified")
  @UpdateTimestamp
  private Date gmtModified;

  /** 是否删除；0逻辑删除，1物理删除； */
  @Column(name = "del_flag", nullable = false)
  private Integer delFlag = 0;

  /** 同步状态 -1删除；0未同步；1已同步 */
  @Column(name = "synch_status", nullable = false)
  private Integer synchStatus = 0;
}
