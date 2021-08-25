package com.qk.dm.datasource.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author zys
 * @date 20210729
 * @since 1.0.0 数据源管理__应用系统VO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ContentRowHeight(20)
@HeadRowHeight(20)
@ColumnWidth(25)
public class DsDirectoryVO {
  /** 主键ID */
  private Integer id;

  /** 应用系统名称 */
  @NotBlank(message = "应用系统名称不能为空！")
  private String sysName;

  /** 应用系统名称简称 */
  @NotBlank(message = "应用系统名称简称不能为空！")
  private String sysShortName;

  /** 区域 */
  private String area;

  /** IT部门 */
  private String itDepartment;

  /** 业务部门 */
  private String busiDepartment;

  /** 重要性(1-高 2-中 3-低) */
  private Integer importance;

  /** 负责人 */
  private String leader;

  /** 部署地 */
  private String deployPlace;

  /** 创建时间 */
  @ExcelIgnore
  @ExcelProperty("创建时间")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date gmtCreate;

  /** 修改时间 */
  @ExcelIgnore
  @ExcelProperty("修改时间")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date gmtModified;

  /** 创建人id（后期设置为非空） */
  private Integer createUserid;

  /** 修改人 */
  private Integer updateUserid;

  /** 删除标识(0-保留 1-删除，后期设置为非空默认为0) */
  private Integer delFlag;

  /** 多租户标识 */
  private String versionConsumer;

  /** 描述 */
  private String sysDesc;

  /** 标签名称组合(ETL,PII)名称用逗号隔开 */
  private String tagNames;

  /** 标签ID组合(1,2,3)ID逗号隔开 */
  private String tagIds;
}
