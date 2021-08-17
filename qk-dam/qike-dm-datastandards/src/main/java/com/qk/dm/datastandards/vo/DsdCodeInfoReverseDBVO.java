package com.qk.dm.datastandards.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DsdCodeInfoReverseDBVO {

  /** 所属码表目录 */
  private String codeDirId;

  /** 所属码表层级目录 */
  private String codeDirLevel;

  /** 数据源连接id */
  //  private String connId;

  /** 数据表 */
  //  private List<String> tableNames;

  /** 更新已有表: 0 :不更新, 1: 更新 */
  private String isUpdate;

  /** 逆向表数据: 0 :不逆向, 1: 覆盖 */
  private String isReverseData;

  /** 数据源连接信息,TODO 暂时使用接口传参的方式 */
  private DataSourceJobVO dataSourceJobVO;
}
