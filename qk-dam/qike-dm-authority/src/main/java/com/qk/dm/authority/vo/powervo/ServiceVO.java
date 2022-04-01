package com.qk.dm.authority.vo.powervo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 权限管理VO
 * @author zys
 * @date 2022/2/24 11:39
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceVO {
  /**
   * 主键id(修改时候为必填字段)
   */
  private Long id;

  /**
   * 服务名称
   */
  @NotBlank(message = "服务名称不能为空")
  private String serviceName;

  /**
   * 描述
   */
  private String description;

  /**
   * 创建人id
   */
  private Long createUserid;

  /**
   * 修改人id
   */
  private Long updateUserid;

  /**
   * 创建人名称
   */
  private String createUsername;

  /**
   * 修改人名称
   */
  private String updateUsername;

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
   * 服务英文标识（作为keycloak中属性key）
   */
  //@NotBlank(message = "服务英文标识（作为keycloak中属性key）不能为空")
  //private String serviceSign;

  /**
   * 区域id（项目id）
   */
  @NotBlank(message = "域id不能为空")
  private String redionId;

  /**
   * 服务uuid
   */
  private String serviceId;

  /**
   * 微服务线上地址
   */
  @NotBlank(message = "微服务线上地址不为空能")
  private String entry;

  /**
   * 微服务项目名
   */
  @NotBlank(message = "微服务项目名不为空能")
  private String path;

  /**
   * 微应用
   */
  @NotBlank(message = "微应用不为空能")
  private String microApp;

  /**
   * 服务名称（前端展示使用）
   */

  private String title;

  /**
   * 服务的uuid（前端展示使用）
   */
  private String value;


}