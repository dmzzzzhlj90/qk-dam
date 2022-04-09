package com.qk.dm.authority.vo.powervo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @author zys
 * @date 2022/3/31 17:19
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResourceMenuQueryVO {
  /**
   * 主键id(修改时为必填参数)
   */
  private Long id;


  /**
   * 资源名称（name -> title）
   */
  private String title;

  /**
   * 资源uuid
   */
  private String value;

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
  @ExcelIgnore
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date gmtCreate;

  /**
   * 修改时间
   */
  @ExcelIgnore
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date gmtModified;

  /**
   * 父级id
   */
  private Long pid;

  /**
   * 服务的uuid
   */
  private String serviceId;

  /**
   * 资源（菜单）uuid
   */
  private String resourcesId;

  /**
   * 页面
   */
  private String component;

  /**
   * 子路由
   */
  //private String route;

  /**
   * 显示icon
   */
  private String icon;

  /**
   * 重定向
   */
  private String redirect;

  /**
   * 是否隐藏菜单
   */
  private Boolean hideInMenu;

  /**
   * 是否隐藏面包屑
   */
  private Boolean hideInBreadcrumb;

  /**
   * 是否严格匹配
   */
  private Boolean exact;

  /**
   * 子节点
   */
  private List<ResourceMenuQueryVO> children;
}