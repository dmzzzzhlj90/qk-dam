package com.qk.dm.dataservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * @author zhudaoming
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataQueryInfoVO {
    @Valid
    private DasApiCreateMybatisSqlScriptDefinitionVO apiCreateDefinitionVO;
    @Valid
    private DasApiBasicInfoVO apiBasicInfoVO;

    /**
     * DEBUG调试参数
     */
    private List<DebugApiParasVO> debugApiParasVOS;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DataQueryInfoVO)) {
            return false;
        }
        DataQueryInfoVO that = (DataQueryInfoVO) o;
        return getApiCreateDefinitionVO().getApiId().equals(that.getApiCreateDefinitionVO().getApiId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getApiCreateDefinitionVO().getApiId());
    }
}
