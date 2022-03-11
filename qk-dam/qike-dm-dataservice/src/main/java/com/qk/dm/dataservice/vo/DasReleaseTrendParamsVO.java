package com.qk.dm.dataservice.vo;

import com.qk.dam.jpa.pojo.Pagination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 首页__发布趋势VO
 *
 * @author wjq
 * @date 20210907
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DasReleaseTrendParamsVO {

  private Pagination pagination;

  /** API标识ID */
  private String apiId;

  /** API名称 */
  private String apiName;

  /** API目录ID */
  private String dirId;

  /** 开始时间 */
  private String beginDay;

  /** 结束时间 */
  private String endDay;
}
