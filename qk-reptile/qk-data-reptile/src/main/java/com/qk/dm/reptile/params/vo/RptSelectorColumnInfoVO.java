package com.qk.dm.reptile.params.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 选择器字段信息
 * @author wangzp
 * @date 2021/12/10 14:24
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RptSelectorColumnInfoVO {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 配置表id
     */
    private Long configId;

    /**
     * 手动执行
     */
    private String manualExecution;

    /**
     * 正则
     */
    private Long regular;

    /**
     * 选择器类型
     */
    private Long selector;

    /**
     * 维度字段名称
     */
    private String columnName;
}
