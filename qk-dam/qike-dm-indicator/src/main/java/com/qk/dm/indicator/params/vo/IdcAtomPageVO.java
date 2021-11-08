package com.qk.dm.indicator.params.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class IdcAtomPageVO implements Serializable {

  private Long id;

  /** 原子指标名称 */
  private String atomIndicatorName;

  /** 原子指标编码 */
  private String atomIndicatorCode;

  /** 主题名称 */
  private String themeCode;

  /** 指标状态 0草稿 1已上线 2已下线 */
  private Integer indicatorStatus;

  /** 修改时间 */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date gmtModified;
}
