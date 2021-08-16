package com.qk.dm.dataservice.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "qk_das_flow_strategy")
public class DasFlowStrategy implements Serializable {

  private static final long serialVersionUID = 1L;

  /** 主键ID */
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** API关联ID */
  @Column(name = "api_id", nullable = false)
  private String apiId;

  /** 策略名称 */
  @Column(name = "strategy_name", nullable = false)
  private String strategyName;

  /** 时长 */
  @Column(name = "time_value", nullable = false)
  private String timeValue;

  /** 时间单位 */
  @Column(name = "time_unit", nullable = false)
  private String timeUnit;

  /** API流量限制 */
  @Column(name = "api_times", nullable = false)
  private String apiTimes;

  /** 用户流量限制 */
  @Column(name = "user_times")
  private String userTimes;

  /** 应用流量限制 */
  @Column(name = "app_times")
  private String appTimes;

  /** 源IP流量限制 */
  @Column(name = "ip_times")
  private String ipTimes;

  /** 描述 */
  @Column(name = "description")
  private String description;

  /** 创建时间 */
  @Column(name = "gmt_create")
  private Date gmtCreate;

  /** 修改时间 */
  @Column(name = "gmt_modified")
  private Date gmtModified;

  /** 是否删除；0逻辑删除，1物理删除； */
  @Column(name = "del_flag", nullable = false)
  private Integer delFlag;
}
