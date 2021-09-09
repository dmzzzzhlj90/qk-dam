package com.qk.plugin.dataservice.apisix.route;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.qk.plugin.dataservice.apisix.route.result.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 封装ApiSix_Result接口返回实体对象
 *
 * @author wjq
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiSixResultVO {
    private int count;
    private String action;
    private Map<String,Object> node;
}
