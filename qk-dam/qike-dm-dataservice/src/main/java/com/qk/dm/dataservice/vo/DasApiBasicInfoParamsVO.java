package com.qk.dm.dataservice.vo;

import com.qk.dam.jpa.pojo.Pagination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DasApiBasicInfoParamsVO {

  private Pagination pagination;

  /** API标识ID */
  private String apiId;

  /** API名称 */
  private String apiName;

  /** API目录ID */
  private String apiDirId;
  /** 开始时间 */
  private String beginDay;

  /** 结束时间 */
  private String endDay;
}
