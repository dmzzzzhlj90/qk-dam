package com.qk.dm.indicator.params.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class IdcCompositePageVO implements Serializable {

  private Long id;

  /** 复合指标名称 */
  private String compositeIndicatorName;

  /** 复合指标编码 */
  private String compositeIndicatorCode;

  /** 主题名称 */
  private String themeCode;

  /** 指标状态 0草稿 1已上线 2已下线 */
  private Integer indicatorStatus;

  /** 修改时间 */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date gmtModified;
}
