package com.qk.dm.authority.constant;

/**
 * 权限管理常量
 * @author zys
 * @date 2022/2/24 17:23
 * @since 1.0.0
 */
public class QxConstant {
  /**
   * 父类id
   */
  public static final Long PID = -1L;

  /**
   * 主目录id设置
   */
  public static final Long DIRID = 0L;
  /**
   * 主目录uuid设置
   */
  public static final String RESOURCEID = "0";

  /**
   * 授权页面查询访问资源(查询api资源)
   */
  public static final int API_TYPE = 0;
  /**
   * 授权页面查询资源
   */
  public static final int RESOURCE_TYPE = 1;

  /**
   * 导入文件名称包含内同
   */
  public static  final String EXCEL_NAME="资源基本信息";
  /**
   * 导入用户文件名称包含内同
   */
  public static  final String USER_EXCEL_NAME="用户基本信息";
  /**
   * 文件上传限制大小
   */
  public final static Integer FILE_SIZE = 10;

  /**
   * 文件上传限制单位（B,K,M,G）
   */
  public final static String FILE_UNIT = "M";

  /**
   * 定义Guava存储大小
   */
  public final static  Integer GUAVA_CAPACITY = 10000000;
  /**
   * 一级资源导出目录父级名称
   */
  public final static  String PID_NAME = "一级资源";
  /**
   * 一条授权信息对应一条关系数据
   */
  public final static  Integer RS_EMP=1;
}