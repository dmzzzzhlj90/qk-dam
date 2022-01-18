package com.qk.dm.reptile.params.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qk.dam.jpa.pojo.Pagination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 基础信息
 * @author wangzp
 * @date 2021/12/10 14:16
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RptBaseInfoDTO {

  private Pagination pagination;
  /**
   * 主键id
   */
  private Long id;

  /**
   * 网站名
   */
  @NotBlank(message = "网站名不能为空")
  private String websiteName;

  /**
   * 连接
   */
  @NotBlank(message = "链接不能为空")
  private String websiteUrl;

  /**
   * 执行周期
   */
  private String runPeriod;

  /**
   * 数据状态（0表示待配、1表示爬虫、2表示历史）
   */
  private Integer status;

  /**
   * 数据启动状态（0表示启动、1表示未启动）
   */
  private Integer runStatus;

  /**
   * 配置人id
   */
  private Long configId;

  /**
   * 配置人名称
   */
  private String configName;

  /**
   * 爬虫接口返回，用于查看日志
   */
  private String jobId;

  /**
   * 最后运行时间
   */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date gmtFunction;

  /**
   * 创建人姓名
   */
  private String createUsername;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date gmtCreate;

  /**
   * 分配日期
   */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date distributionDate;
  /**
   * 交付日期
   */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date deliveryDate;
  /**
   * 二期站点类型
   */
  private String secondSiteType;
  /**
   * 列表页地址
   */
  private String listPageAddress;
  /**
   * 不同类型混合
   */
  private String differentTypeMixed;
  /**
   * 信息发布级别
   */
  private String infoReleaseLevel;
  /**
   * 省编码
   */
  private String provinceCode;
  /**
   * 市编码
   */
  private String cityCode;
  /**
   * 区编码
   */
  private String areaCode;

  /**
   * 站点官网（修正）
   */
  private String websiteNameCorrection;

  /**
   * 站点官网（修正）
   */
  private String websiteUrlCorrection;

  /**
   * 区域编码
   */
  private String regionCode;

  /**
   * 执行人
   */
  private String executor;

  /**
   * 执行人id
   */
  private Long executorId;
  /**
   * 定时间隔
   */
  private String timeInterval;
}