package com.qk.dm.datasource.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
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
 * @since 1.0.0 数据源管理__目录VO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DsDirVO {

  /** 主键ID */
  private Integer id;

  /** 目录名称 */
  @NotBlank(message = "目录名称")
  private String dicName;

  /** 父级id */
  @NotBlank(message = "父级id(一级目录是0，二级目录是上级的id)")
  private Integer parentId;

  /** 目录层级编码 */
  @NotBlank(message = "目录层级编码")
  /** 目录编码 */
  private String dsDirCode;

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

  /** 创建人(后期设计为非空) */
  private String createUserid;

  /** 修改人 */
  private String updateUserid;

  /** 删除标识(0-保留 1-删除，后期设置为非空，默认是0) */
  private Integer delFlag;

  /** 多租户标识 */
  private String versionConsumer;
}
