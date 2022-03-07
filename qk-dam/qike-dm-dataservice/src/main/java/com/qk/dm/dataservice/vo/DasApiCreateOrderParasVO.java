package com.qk.dm.dataservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 新建API_配置方式_排序参数VO
 *
 * @author wjq
 * @date 20210907
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DasApiCreateOrderParasVO {

    /**
     * 序号
     */
    private Integer orderNum;

    /**
     * 参数名称
     */
    private String paraName;

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
