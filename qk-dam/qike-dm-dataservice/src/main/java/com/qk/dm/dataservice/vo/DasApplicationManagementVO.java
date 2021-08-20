package com.qk.dm.dataservice.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author zys
 * @date 2021/8/17 15:04
 * @since 1.0.0 数据服务-应用管理VO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DasApplicationManagementVO {
  /** 主键ID */
  private Long id;

  /** 应用ID */
  private String appId;

  /** 应用名称 */
  private String appName;

  /** 应用类型 */
  private String appType;

  /** API关联ID */
  private String apiId;

  /** 描述 */
  private String description;

  /** 创建时间 */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date gmtCreate;

  /** 修改时间 */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date gmtModified;

  /** 是否删除；0逻辑删除，1物理删除； */
  private Integer delFlag;
}
