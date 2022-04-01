package com.qk.dm.authority.vo.powervo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author zys
 * @date 2022/3/31 17:19
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResourceMenuVO {
  /**
   * 主键id(修改时为必填参数)
   */
  private Long id;

  /**
   * 资源名称
   */
  @NotBlank(message = "资源名称不能为空")
  private String name;

  /**
   * 网址路径
   */
  @NotBlank(message = "网址路径不能为空")
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
  @NotNull(message = "父级id不能为空")
  private Long pid;

  /**
   * 服务的uuid
   */
  @NotBlank(message = "服务的uuid不能为空")
  private String serviceId;

  /**
   * 资源（菜单）uuid
   */
  private String resourcesId;

  /**
   * 页面
   */
  @NotBlank(message = "页面不能为空")
  private String component;

  /**
   * 子路由
   */
  private String route;

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
  private String hideInMenu;

  /**
   * 是否隐藏面包学
   */
  private String hideInBreadcrumb;

  /**
   * 是否严格匹配
   */
  private String exact;
}