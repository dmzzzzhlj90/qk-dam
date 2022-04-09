package com.qk.plugin.dataservice.apisix.route;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
  private Map<String, Object> node;
}
