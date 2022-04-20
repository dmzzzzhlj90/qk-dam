package com.qk.dm.dataservice.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhudaoming
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataQueryInfoVO {
    private DasApiCreateSqlScriptDefinitionVO dasApiCreateSqlScript;
    private DasApiBasicInfoVO dasApiBasicInfo;
}
