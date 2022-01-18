package com.qk.dm.reptile.params.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RptSelectorVO {
    /**
     * 是的还有下一层
     */
    private Boolean next;
    /**
     * 下一层的配置id
     */
    private Long configId;
    /**
     * 选择器列表
     */
    private List<RptSelectorColumnInfoVO> selectorList;

    private List<String> columnCodeList;
}
