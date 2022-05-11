package com.qk.dm.dataservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
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
    private DasApiCreateMybatisSqlScriptDefinitionVO dasApiCreateMybatisSqlScript;
    @Valid
    private DasApiBasicInfoVO dasApiBasicInfo;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DataQueryInfoVO)) {
            return false;
        }
        DataQueryInfoVO that = (DataQueryInfoVO) o;
        return getDasApiCreateMybatisSqlScript().getApiId().equals(that.getDasApiCreateMybatisSqlScript().getApiId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDasApiCreateMybatisSqlScript().getApiId());
    }
}
