package com.qk.dam.datasource.entity;

import com.qk.dam.datasource.enums.ConnTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 数据源连接信息_HIVE
 *
 * @author wjq
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class HiveInfo extends ConnectBasicInfo {
    /**
     * 数据源类型
     */
    private String type = ConnTypeEnum.HIVE.getName();

    /**
     * 连接驱动
     */
    private String driverInfo;
}
