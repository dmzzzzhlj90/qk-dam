package com.qk.dm.dataingestion.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisRuleClassVO {

    private Long id;

    /**
     * 规则分类id
     */
    private String dirId;

    /**
     * 规则分类名称
     */
    @NotBlank(message = "规则目录名称不能为空！")
    private String title;

    private String value;
    /**
     * 父id
     */
    private String parentId;
    /**
     * 子节点
     */
    private List<DisRuleClassVO> children;

}
