package com.qk.dm.indicator.params.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author shenpj
 * @date 2021/9/1 2:48 下午
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdcFunctionDTO {
    /**
     * 函数名称
     */
    @NotBlank(message = "函数名称不能为空！")
    private String name;

    /**
     * 函数
     */
    @NotBlank(message = "函数定义不能为空！")
    private String function;

    /**
     * 引擎
     */
    private String engine;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 父级id
     */
    private Integer parentId;
}
