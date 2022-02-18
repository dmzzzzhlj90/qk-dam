package com.qk.dm.dataservice.vo;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wjq
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DasApiCreateConfigVO {
  /** API基础信息 */
  private DasApiBasicInfoVO dasApiBasicInfoVO;

  /** 主键ID */
  private Long id;

  /** API基础信息ID */
  private String apiId;

  /** 取数据方式 */
  private String accessMethod;

  /** 数据源类型 */
  private String dataSourceType;

  /** 数据源连接 */
  private String dataSourceConnect;

  /** 数据库 */
  private String dataBase;

  /** 数据表 */
  private String tableName;

  /** 请求参数 */
  private List<DasApiCreateRequestParasVO> apiCreateRequestParasVOS;

  /** 响应参数 */
  private List<DasApiCreateResponseParasVO> apiCreateResponseParasVOS;

  /** 排序参数 */
  private List<DasApiCreateOrderParasVO> apiCreateOrderParasVOS;

  /** 描述 */
  private String description;
}
