package com.qk.dm.dataservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 发布API参数信息VO
 *
 * @author wjq
 * @date 20220301
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DasReleaseApiParamsVO {

    /**
     * 模糊匹配路径
     */
    private String nearlyApiPath;

    /**
     * API同步方式
     */
    private String apiSyncType;

    /**
     * apiId集合
     */
    private List<String> apiIds;

}
