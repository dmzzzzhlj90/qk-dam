package com.qk.dm.dataservice.vo;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DasApiDataSourceConfigVO {
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
  private List<DasApiDataSourceConfRequestParasVO> apiDataSourceConfRequestParasVOS;

  /** 响应参数 */
  private List<DasApiDataSourceConfResponseParasVO> apiDataSourceConfResponseParasVOS;

  /** 排序参数 */
  private List<DasApiDataSourceConfOrderParasVO> apiDataSourceConfOrderParasVOS;

  /** 取数脚本 */
  private String sqlPara;

  /** 描述 */
  private String description;
}
