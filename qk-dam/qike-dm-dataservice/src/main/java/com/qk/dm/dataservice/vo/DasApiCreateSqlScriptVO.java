package com.qk.dm.dataservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 新建API_取数SQL方式VO
 *
 * @author wjq
 * @date 20210907
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DasApiCreateSqlScriptVO {

    /**
     * API基础信息
     */
    @Valid
    private DasApiBasicInfoVO apiBasicInfoVO;

    /**
     * 新建API__取数SQL方式__配置信息定义类
     */
    @Valid
    private DasApiCreateSqlScriptDefinitionVO apiCreateDefinitionVO;

    /**
     * DEBUG调试参数
     */
    private List<DebugApiParasVO> debugApiParasVOS;


}
