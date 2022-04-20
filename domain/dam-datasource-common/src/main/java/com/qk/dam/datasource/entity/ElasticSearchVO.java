package com.qk.dam.datasource.entity;

import com.qk.dam.datasource.enums.ConnTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据源连接信息_ELASTICSEARCH
 *
 * @author wjq
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ElasticSearchVO extends ConnectBasicInfo {
  /** 数据源类型 */
  private final String type = ConnTypeEnum.ELASTICSEARCH.getName();

  /** 连接驱动 */
  //private String driverInfo;
}
