package com.qk.dm.dataservice.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @author zhudaoming
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataQueryInfoVO {
    private DasApiCreateSqlScriptDefinitionVO dasApiCreateSqlScript;
    private DasApiBasicInfoVO dasApiBasicInfo;

    @Override
    public boolean equals(Object o) {
        if (this == o){ return true;}
        if (!(o instanceof DataQueryInfoVO)){ return false;}
        DataQueryInfoVO that = (DataQueryInfoVO) o;
        return getDasApiCreateSqlScript().getApiId().equals(that.getDasApiCreateSqlScript().getApiId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDasApiCreateSqlScript().getApiId());
    }
}
