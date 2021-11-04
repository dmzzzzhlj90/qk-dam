package com.qk.dam.datasource.entity;

import com.qk.dam.datasource.enums.ConnTypeEnum;
import lombok.*;

/**
 * 数据源连接信息_ORACLE
 *
 * @author wjq
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OracleInfo extends ConnectBasicInfo {
  /** 数据源类型 */
  private final String type = ConnTypeEnum.ORACLE.getName();

  /** 连接驱动 */
  private String driverInfo;
}
