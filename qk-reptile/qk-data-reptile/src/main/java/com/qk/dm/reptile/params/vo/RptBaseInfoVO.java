package com.qk.dm.reptile.params.vo;

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
public class RptBaseInfoVO {

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
  @NotBlank(message = "添加人不能为空")
  private String createUsername;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date gmtCreate;


}