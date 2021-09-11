package com.qk.dm.datastandards.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataSourceJobVO {

    /**
     * 数据源连接名称
     **/
    @NotBlank(message = "数据源连接名称不能为空！")
    private String name;

    /**
     * 数据源连接类型
     **/
    @NotBlank(message = "数据源连接类型不能为空！")
    private String dataConnectType;

    /**
     * mysql类型数据源连接信息
     **/
    private MysqlDataConnectVO mysqlDataConnect;

}
