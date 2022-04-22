package com.qk.dm.dataservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

/**
 * 注册API_明细展示VO
 *
 * @author wjq
 * @date 20210907
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DasApiRegisterVO {
    /**
     * API基础信息
     */
    @Valid
    private DasApiBasicInfoVO apiBasicInfoVO;

    /**
     * 注册API定义信息
     */
    @Valid
    private DasApiRegisterDefinitionVO apiRegisterDefinitionVO;

}
