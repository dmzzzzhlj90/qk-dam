package com.qk.dm.dataquality.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qk.dm.dataquality.constant.DataSourceEnum;
import com.qk.dm.dataquality.constant.DimensionEnum;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

@Data
public class DqcRuleTemplateInfoVo {

  private Integer id;

  /** 模板名称 */
  private String tempName;

  /** 模板类型1-系统内置 2-自定义 */
  private Integer tempType;

  /** 分类目录 */
  private Integer dirId;

  /** 质量纬度id */
  private Integer dimensionId;

  /** 质量维度 */
  private String dimension;

  /** 适用引擎 1-hive, 2-mysql, 适用多个以逗号分隔 */
  private String engineType;

  /** 适用引擎 */
  private String engineName;

  /** 描述 */
  private String description;

  /** 模板sql */
  private String tempSql;

  /** 结果定义 */
  private String tempResult;

  /** 发布状态 0-下线 1-发布 */
  private Integer publishState;

  /** 创建人 */
  private String createUser;

  /** 修改时间 */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date gmtModified;

  public void setDimensionId(Integer dimensionId) {
    this.dimensionId = dimensionId;
    this.dimension = DimensionEnum.fromValue(dimensionId);
  }

  public void setEngineType(String engineType) {
    this.engineType = engineType;
    this.engineName =
        Arrays.stream(engineType.split(","))
            .map(i -> DataSourceEnum.fromValue(Integer.parseInt(i)))
            .collect(Collectors.joining(","));
  }
}
