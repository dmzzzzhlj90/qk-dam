package com.qk.dm.dataservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.List;

/**
 * 新建API_配置方式VO
 *
 * @author wjq
 * @date 20210907
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DasApiCreateConfigVO {

    /**
     * API基础信息
     */
    @Valid
    private DasApiBasicInfoVO apiBasicInfoVO;

    /**
     * 新建API_配置方式_配置信息定义类
     */
    @Valid
    private DasApiCreateConfigDefinitionVO apiCreateDefinitionVO;

    /**
     * DEBUG调试参数
     */
    private List<DebugApiParasVO> debugApiParasVOS;


}
