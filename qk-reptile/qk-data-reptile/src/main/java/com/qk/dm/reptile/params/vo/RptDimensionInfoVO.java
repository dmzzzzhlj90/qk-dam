package com.qk.dm.reptile.params.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 维度信息目录出参VO
 * @author zys
 * @date 2021/12/8 11:18
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RptDimensionInfoVO {
  /**
   * 主键id（修改接口为必填项）
   */
  private Long id;

  /**
   * 维度目录名称
   */
  private String dimensionName;

  /**
   * 目录父类id
   */
  private Long fid;

  /**
   * 描述
   */
  private String description;

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
  private String createUsername;

  /**
   * 修改人姓名
   */
  private String updateUsername;
  /**
   * 子集目录
   */
  List<RptDimensionInfoVO> childrenList;
}