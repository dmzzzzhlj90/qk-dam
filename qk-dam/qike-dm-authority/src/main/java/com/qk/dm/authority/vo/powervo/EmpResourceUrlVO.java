package com.qk.dm.authority.vo.powervo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 根据用户id查询授权菜单VO
 * @author zys
 * @date 2022/3/30 17:27
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpResourceUrlVO {
  /**
   * 主键id(修改时为必填参数)
   */
  private Long id;

  /**
   * 资源名称
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
   * 父级id
   */
  private Long pid;

  /**
   * 页面
   */
  private String component;

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
   * 子菜单
   */
  private List<EmpResourceUrlVO> route;
}