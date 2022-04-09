package com.qk.dm.dataservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 新建API_脚本方式_排序参数VO
 *
 * @author wjq
 * @date 2022/03/08
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DasApiCreateSqlScriptOrderParasVO {

    /**
     * 变量
     */
    private String variable;

    /**
     * 字段名称
     */
    private String columnName;

    /**
     * 是否可选
     */
    private Boolean optional;

    /**
     * 排序方式
     */
    private String orderType;

    /**
     * 描述
     */
    private String description;

}
