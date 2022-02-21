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
     * 维度字段名称
     */
    private String columnName;
    /**
     * 维度字段编码
     */
    private String columnCode;

    /**
     * 选择器类型 0代表xpath、1代表正则 ，99代表手动指定
     */
    private Integer selector;

    /**
     * 选择器字段值
     */
    private String selectorVal;

    /**
     * 元素类型 0代表单元素 1代表多元素
     */
    private Integer elementType;

    /**
     * 前缀
     */
    private String  beforePrefix;
    /**
     * 后缀
     */
    private String afterPrefix;
}
