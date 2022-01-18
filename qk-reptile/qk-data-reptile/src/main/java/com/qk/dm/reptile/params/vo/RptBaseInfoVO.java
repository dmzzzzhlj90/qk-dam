package com.qk.dm.reptile.params.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
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
@ContentRowHeight(20)
@HeadRowHeight(20)
@ColumnWidth(25)
public class RptBaseInfoVO {
  @ExcelIgnore
  private Pagination pagination;
  /**
   * 主键id
   */
  @ExcelProperty("ID编号")
  @ExcelIgnore
  private Long id;

  /**
   * 网站名
   */
  @ExcelProperty(value = "网站名",order = 5)
  private String websiteName;

  /**
   * 连接
   */
  @ExcelProperty(value = "链接",order = 6)
  @NotBlank(message = "连接不能为空")
  private String websiteUrl;

  /**
   * 执行周期
   */
  @ExcelIgnore
  @ExcelProperty("执行周期")
  private String runPeriod;

  /**
   * 数据状态（0表示待配、1表示爬虫、2表示历史）
   */
  @ExcelIgnore
  @ExcelProperty("数据状态")
  private Integer status;

  /**
   * 数据启动状态（0表示启动、1表示未启动）
   */
  @ExcelIgnore
  @ExcelProperty("数据启动状态")
  private Integer runStatus;

  /**
   * 配置人id
   */
  @ExcelIgnore
  @ExcelProperty("配置人id")
  private Long configId;

  /**
   * 配置人名称
   */
  @ExcelIgnore
  @ExcelProperty("配置人名称")
  private String configName;

  /**
   * 爬虫接口返回，用于查看日志
   */
  @ExcelIgnore
  @ExcelProperty("爬虫接口返回")
  private String jobId;

  /**
   * 最后运行时间
   */
  @ExcelIgnore
  @ExcelProperty("最后运行时间")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date gmtFunction;

  /**
   * 创建人姓名
   */
  @ExcelProperty(value = "添加人姓名",order = 3)
  private String createUsername;
  /**
   * 创建时间
   */
  @ExcelIgnore
  @ExcelProperty("创建时间")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date gmtCreate;
  /**
   * 分配日期
   */
  @ExcelProperty(value = "分配日期",order = 1)
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date distributionDate;
  /**
   * 交付日期
   */
  @ExcelProperty(value = "交付日期",order = 4)
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date deliveryDate;

  /**
   * 二期站点类型
   */
  @ExcelProperty(value = "二期站点类型",order = 7)
  @NotBlank(message = "二期站点类型不能为空")
  private String secondSiteType;
  /**
   * 列表页地址
   */
  @ExcelProperty(value = "列表页地址",order = 8)
  @NotBlank(message = "列表页地址不能为空")
  private String listPageAddress;
  /**
   * 不同类型混合
   */
  @ExcelProperty(value = "不同类型混合",order = 9)
  private String differentTypeMixed;
  /**
   * 信息发布级别
   */
  @ExcelProperty(value = "信息发布级别",order = 10)
  private String infoReleaseLevel;
  /**
   * 省编码
   */
  @ExcelProperty(value = "省编码",order = 14)
  private String provinceCode;
  /**
   * 市编码
   */
  @ExcelProperty(value = "市编码",order = 15)
  private String cityCode;
  /**
   * 区编码
   */
  @ExcelProperty(value = "区编码",order = 16)
  private String areaCode;

  /**
   * 站点名称（修正）
   */
  @ExcelProperty(value = "站点名称(修正)",order =12 )
  private String websiteNameCorrection;

  /**
   * 站点官网（修正）
   */
  @ExcelProperty(value = "站点官网(修正)",order = 11)
  private String websiteUrlCorrection;

  /**
   * 区域编码
   */
  @ExcelProperty(value = "区域编码",order = 13)
  private String regionCode;

  /**
   * 执行人
   */
  @ExcelProperty(value = "执行人",order = 2)
  private String executor;

  /**
   * 执行人id
   */
  @ExcelIgnore
  private Long executorId;
  /**
   * 是否已经添加了配置
   */
  @ExcelIgnore
  private Boolean configStatus;
  /**
   * 定时间隔
   */
  @ExcelIgnore
  private String timeInterval;
}