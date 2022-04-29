package com.qk.dam.datasource.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据源连接信息_ORACLE
 *
 * @author wjq
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OracleInfo extends ConnectBasicInfo {
  /** 数据源类型 */
  //private final String type = ConnTypeEnum.ORACLE.getName();

}
