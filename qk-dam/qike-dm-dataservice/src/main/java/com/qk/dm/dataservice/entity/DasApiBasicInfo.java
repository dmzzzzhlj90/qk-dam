package com.qk.dm.dataservice.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "qk_das_api_basic_info")
public class DasApiBasicInfo implements Serializable {

  private static final long serialVersionUID = 1L;

  /** 主键ID */
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** API标识ID */
  @Column(name = "api_id", nullable = false)
  private String apiId;

  /** API目录ID */
  @Column(name = "das_dir_id", nullable = false)
  private String dasDirId;

  /** API目录层级 */
  @Column(name = "api_dir_level", nullable = false)
  private String apiDirLevel;

  /** API名称 */
  @Column(name = "api_name", nullable = false)
  private String apiName;

  /** 请求Path */
  @Column(name = "api_path", nullable = false)
  private String apiPath;

  /** 请求协议 */
  @Column(name = "protocol_type", nullable = false)
  private String protocolType;

  /** 请求方法 */
  @Column(name = "request_type", nullable = false)
  private String requestType;

  /** API类型 */
  @Column(name = "api_type", nullable = false)
  private String apiType;

  /** 入参定义 */
  @Column(name = "def_input_param")
  private String defInputParam;

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
