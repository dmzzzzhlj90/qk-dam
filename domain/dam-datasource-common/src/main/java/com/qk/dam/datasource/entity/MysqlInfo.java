package com.qk.dam.datasource.entity;

import com.qk.dam.datasource.enums.ConnTypeEnum;
import lombok.*;

/**
 * 数据源连接信息_MYSQL
 *
 * @author wjq
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MysqlInfo extends ConnectBasicInfo {
  /** 数据源类型 */
  private final String type = ConnTypeEnum.MYSQL.getName();


}
