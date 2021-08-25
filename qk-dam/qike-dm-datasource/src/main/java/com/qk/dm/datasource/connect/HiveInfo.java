package com.qk.dm.datasource.connect;

import com.qk.dam.commons.enums.ConnTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author zys
 * @date 2021/8/23 17:33
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class HiveInfo extends DSConnectBasicInfo {
  /** 数据源类型 */
  private String type = ConnTypeEnum.HIVE.getName();

  /** 连接驱动 */
  private String driverInfo;
}
