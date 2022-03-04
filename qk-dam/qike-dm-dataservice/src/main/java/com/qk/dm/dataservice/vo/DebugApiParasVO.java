package com.qk.dm.dataservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * API调试请求参数VO
 *
 * @author wjq
 * @date 2022/03/04
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DebugApiParasVO {

    /**
     * 参数名称
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
     * 值
     */
    private String value;

    /**
     * 是否传递
     */
    private boolean usePara;

}
