package com.qk.dm.dataservice.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DasApiBasicInfoVO {

  /** 主键ID */
  private Long id;

  /** API标识ID */
  private String apiId;

  /** API目录ID */
  private String dasDirId;

  /** API目录层级 */
  private String apiDirLevel;

  /** API名称 */
  private String apiName;

  /** 请求Path */
  private String apiPath;

  /** 请求协议 */
  private String protocolType;

  /** 请求方法 */
  private String requestType;

  /** API类型 */
  private String apiType;

  /** 入参定义 */
  private List<DasApiBasicInfoRequestParasVO> dasApiBasicInfoRequestParasVO;

  /** 描述 */
  private String description;

  /** 修改时间 */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date gmtModified;
}
