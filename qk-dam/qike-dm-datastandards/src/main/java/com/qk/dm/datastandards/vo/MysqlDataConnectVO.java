package com.qk.dm.datastandards.vo;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MysqlDataConnectVO {

  /** 数据库名称 */
  @NotBlank(message = "数据库名称不能为空！")
  private String db;

  /** 表名称 */
  @NotBlank(message = "表名称不能为空！")
  private String table;

  /** 主机host */
  @NotBlank(message = "主机host不能为空！")
  private String host;

  /** 端口port */
  @NotBlank(message = "端口port不能为空！")
  private String port;

  /** 用户名称 */
  @NotBlank(message = "用户名称不能为空！")
  private String username;

  /** 用户密码 */
  @NotBlank(message = "用户密码不能为空！")
  private String password;
}
