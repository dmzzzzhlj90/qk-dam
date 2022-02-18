package com.qk.dm.dataservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wjq
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DasApiRegisterVO {
    /**
     * API基础信息
     */
    private DasApiBasicInfoVO dasApiBasicInfoVO;

    /**
     * 注册API定义信息
     */
    private DasApiRegisterDefinitionVO dasApiRegisterDefinitionVO;

}
