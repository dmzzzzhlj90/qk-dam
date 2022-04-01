package com.qk.dm.authority.vo.powervo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 资源导入导出VO
 * @author zys
 * @date 2022/3/31 20:57
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ContentRowHeight(20)
@HeadRowHeight(20)
@ColumnWidth(25)
public class ResourceMenuExcelVO {
  /**
   * 主键id
   */
  @ExcelIgnore
  private Long id;

  /**
   * 资源名称
   */
  @ExcelProperty(value = "资源名称",order = 1)
  private String name;

  /**
   * 网址路径
   */
  @ExcelProperty(value = "网址路径",order = 2)
  private String path;

  /**
   * 描述
   */
  @ExcelProperty(value = "描述",order = 3)
  private String description;

  /**
   * 创建人id
   */
  @ExcelIgnore
  private Long createUserid;

  /**
   * 修改人id
   */
  @ExcelIgnore
  private Long updateUserid;

  /**
   * 创建人名称
   */
  @ExcelProperty(value = "创建人名称",order =4)
  private String createName;

  /**
   * 修改人名称
   */
  @ExcelIgnore
  private String updateName;

  /**
   * 创建时间
   */
  @ExcelIgnore
  private Date gmtCreate;

  /**
   * 修改时间
   */
  @ExcelIgnore
  private Date gmtModified;

  /**
   * 父级id
   */
  @ExcelProperty(value = "父级名称",order = 5)
  private String pidName;

  /**
   * 服务的uuid
   */
  @ExcelProperty(value = "服务UUID",order = 6)
  private String serviceId;

  /**
   * 资源（菜单）uuid
   */
  @ExcelIgnore
  private String resourcesId;

  /**
   * 页面
   */
  @ExcelProperty(value = "页面",order = 7)
  private String component;

  /**
   * 子路由
   */
  @ExcelProperty(value = "子路由",order = 8)
  private String route;

  /**
   * 显示icon
   */
  @ExcelProperty(value = "显示icon",order = 9)
  private String icon;

  /**
   * 重定向
   */
  @ExcelProperty(value = "重定向",order = 10)
  private String redirect;

  /**
   * 是否隐藏菜单
   */
  @ExcelProperty(value = "是否隐藏菜单",order = 11)
  private String hideInMenu;

  /**
   * 是否隐藏面包屑
   */
  @ExcelProperty(value = "是否隐藏面包屑",order = 12)
  private String hideInBreadcrumb;

  /**
   * 是否严格匹配
   */
  @ExcelProperty(value = "是否严格匹配",order = 13)
  private String exact;
}