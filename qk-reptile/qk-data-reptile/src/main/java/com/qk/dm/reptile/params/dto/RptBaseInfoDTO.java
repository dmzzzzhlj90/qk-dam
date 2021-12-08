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
import java.util.List;

/**
 * 配置信息入参DTO
 * @author zys
 * @date 2021/12/8 10:26
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RptBaseInfoDTO {

  private Pagination pagination;
  /**
   * 主键id（修改接口为必填项）
   */
  private Long id;
  /**
   * raw参数
   */
  private String raw;

  /**
   * x-www-form-urlencoded参数
   */
  private String formUrlencoded;

  /**
   * from-data参数
   */
  private String fromData;

  /**
   * cookies参数
   */
  private String cookies;

  /**
   * headers参数
   */
  private String headers;

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
   * 描述
   */
  private String description;

  /**
   * 维度目录id
   */
  private Long dimensionId;

  /**
   * 维度目录名称
   */
  private String dimensionName;

  /**
   * 是否启动动态加载js(0表示未启动、1表示启动)
   */
  private Integer startoverJs;

  /**
   * 是否启动代理IP(0表示未启动、1表示启动)
   */
  private Integer startoverIp;

  /**
   * 配置人名称
   */
  private String configName;

  /**
   * 配置人id
   */
  private Long configId;

  /**
   * 请求路径
   */
  private String requestUrl;

  /**
   * 请求方式
   */
  private String requestType;

  /**
   * 连接
   */
  @NotBlank(message = "连接不能为空")
  private String conn;

  /**
   * 网站名
   */
  @NotBlank(message = "网站名不能为空")
  private String cnName;

  /**
   * 最后运行时间
   */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date gmtFunction;

  /**
   * 修改时间
   */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date gmtModified;

  /**
   * 创建时间
   */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date gmtCreate;

  /**
   * 创建人id
   */
  private Long createUserid;

  /**
   * 修改人id
   */
  private Long updateUserid;

  /**
   * 创建人姓名
   */
  @NotBlank(message = "添加人不能为空")
  private String createUsername;

  /**
   * 修改人姓名
   */
  private String updateUsername;
  /**
   * 配置信息字段信息
   */
  List<RptBaseInfoColumnDTO> columnDTOList;
}