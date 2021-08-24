package com.qk.dm.datasource.datasourinfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author zys
 * @date 2021/8/23 17:02
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class MysqlInfo extends BaseDataSourceTypeInfo {
  /** 数据源类型 */
  private String type = "db-mysql";
  /** 服务器 */
  private String server;
  /** 端口 */
  private String port;
  /** 用户名 */
  private String username;
  /** 密码 */
  private String password;
}
