package com.qk.dm.dataservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * API调试 返回值参数VO
 *
 * @author wjq
 * @date 2022/03/04
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DebugApiResultVO {

    /**
     * 查询结果集
     */
    private Object resultData;

}
