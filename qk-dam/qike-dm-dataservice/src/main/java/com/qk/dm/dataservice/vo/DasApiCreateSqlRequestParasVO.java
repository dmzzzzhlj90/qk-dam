package com.qk.dm.dataservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 新建API_SQL脚本方式_请求参数VO
 *
 * @author wjq
 * @date 20220224
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DasApiCreateSqlRequestParasVO {

    /**
     * 变量
     */
    private String variable;

    /**
     * 请求参数
     */
    private String paraName;

    /**
     * 参数类型
     */
    private String paraType;

    /**
     * 是否必填
     */
    private String necessary;

    /**
     * 示例值
     */
    private String exampleValue;

    /**
     * 描述
     */
    private String description;

}
