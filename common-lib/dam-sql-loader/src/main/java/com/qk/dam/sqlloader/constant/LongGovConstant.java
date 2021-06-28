package com.qk.dam.sqlloader.constant;

public class LongGovConstant {
  /** 阿里云文件服务器 前置URL地址 */
  public static final String HOST_ALIYUN_OSS = "https://targz.oss-cn-beijing.aliyuncs.com/";

  public static final String LOCAL_FILES_PATH = "/home/rmtFile/";
  public static final String BUCKETNAME = "data-hd-updated-1306026405";
  public static final String HOST_TENGXUN_OSS = "";
  public static final String SQL_INSERT = "insert";
  public static final String SQL_REPLACE = "replace";

  /** 腾讯COS 登录ID_KEY */
  public static final String COS_SECRET_ID = "AKIDjIcfRvA8pFNFqAzcy3qOffOq2xaw2rQi";

  public static final String COS_SECRET_KEY = "3pJWR5SYMtABuNZU8VbQyEExPaVAXdS0";

  /** 腾讯COS 地域信息 */
  public static final String COS_REGION = "ap-beijing";

  public static final String COS_META_CONTENTTYPE = "application/octet-stream"; // COS操作元对象类型
  public static final String COS_META_HEADER_TABLE = "x-cos-meta-table"; // COS元对象Header存储,key为表名称
  public static final String COS_META_HEADER_PICI = "x-cos-meta-pici"; // COS元对象Header存储,key为数据批次

  /** 日期格式 */
  public static final String DATE_TIME_PATTERN = "yyyyMMdd";

  public static final String DATE_TIME_SECOND_PATTERN = "yyyy-MM-dd HH:mm:ss";
  /** 接口调度状态信息 */
  public static final int RESULT_ERROR = 0; // 执行失败

  public static final int RESULT_SUCCESS_EMPTY = 1; // 执行成功,数据为空
  public static final int RESULT_SUCCESS_EXIST = 2; // 执行成功,有可执行数据
}
