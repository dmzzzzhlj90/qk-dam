package com.qk.dm.dataservice.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * API目录VO
 *
 * @author wjq
 * @date 20210907
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DasApiDirVO {

  /**
   * 主键ID
   */
  private Long id;

  /**
   * API分类目录ID
   */
  private String dirId;

  /**
   * API分类目录名称 title
   */
  @NotBlank(message = "API分类目录名称不能为空！")
  private String title;

  /**
   * API分类目录名称 value
   */
  private String value;

  /**
   * 父级id
   */
  private String parentId;

  /**
   * 描述
   */
  private String description;

  /**
   * 创建时间
   */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date gmtCreate;

  /**
   * 修改时间
   */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date gmtModified;

  /**
   * 是否删除；0逻辑删除，1物理删除；
   */
  private Integer delFlag;
}
