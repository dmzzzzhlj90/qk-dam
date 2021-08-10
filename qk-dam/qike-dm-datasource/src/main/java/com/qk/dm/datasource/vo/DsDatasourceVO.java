package com.qk.dm.datasource.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * @author zys
 * @date 20210729
 * @since 1.0.0 数据源管理__数据源连接VO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ContentRowHeight(20)
@HeadRowHeight(20)
@ColumnWidth(25)
public class DsDatasourceVO {
  private Integer id;

  /** 系统名称 */
  @NotBlank(message = "连接名称不为空")
  private String dataSourceName;

  /** 所属系统 */
  @NotBlank(message = "所属系统不为空")
  private String homeSystem;

  /** 数据 源连接值 */
  @NotBlank(message = "数据源连接值")
  private LinkedHashMap<String, String> dataSourceValuesMap;

  /** 连接方式 */
  private String linkType;

  /** 标签（名称用逗号隔开） */
  private String tagNames;

  /** 标签id组合（ID用逗号隔开） */
  private String tagIds;

  /** 用途 */
  private String use;

  /** 部署地 */
  private String deployPlace;

  /** 状态设置状态值 */
  private Integer status;

  @ExcelIgnore
  @ExcelProperty("创建时间")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date gmtCreate;

  @ExcelIgnore
  @ExcelProperty("修改时间")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date gmtModified;

  /** 创建人id（后期设置为必填） */
  private String createUserid;

  /** 修改人id */
  private String updateUserid;

  /** 删除标识(0-保留 1-删除，后期设置为非空，默认为0) */
  private Integer delFlag;

  /** 多租户标识 */
  private String versionConsumer;

  /** 目录归属id */
  private String dicId;

  /** 备注 */
  private String remark;
}
