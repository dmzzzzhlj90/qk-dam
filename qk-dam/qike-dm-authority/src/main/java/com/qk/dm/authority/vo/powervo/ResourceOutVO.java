package com.qk.dm.authority.vo.powervo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 资源节点输出VO
 * @author zys
 * @date 2022/2/24 17:11
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceOutVO {
  /**
   * 主键id
   */
  private Long id;

  /**
   * 资源（API）名称
   */
  private String name;

  /**
   * 网址路径
   */
  private String path;

  /**
   * 描述
   */
  private String description;

  /**
   * 创建人id
   */
  private Long createUserid;

  /**
   * 修改人id
   */
  private Long updateUserid;

  /**
   * 创建人名称
   */
  private String createName;

  /**
   * 修改人名称
   */
  private String updateName;

  /**
   * 创建时间
   */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date gmtCreate;

  /**
   * 修改时间
   */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date gmtModified;

  /**
   * 父级id（API类型fid默认为-1）
   */
  private Long pid;

  /**
   * 服务id
   */
  private String serviceId;

  /**
   * 0标识API，1表示资源
   */
  private Integer type;

  /**
   * 资源uuid
   */
  private String resourcesid;

  /**
   * 子节点
   */
  private List<ResourceOutVO> childrenList;
}