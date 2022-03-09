package com.qk.dm.authority.vo.powervo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**授权信息
 * @author zys
 * @date 2022/2/25 11:12
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpowerVO {
  /**
   * 主键id(修改为必填参数)
   */
  private Long id;

  /**
   * 被授权主体类型(0表示用户，1表示角色，2表示用户组)
   */
  private String empoerType;

  /**
   * 被授权主体名称
   */
  private String empoerName;

  /**
   * 被授权主体id(当授权主体为角色时候存储对应的角色客户端id)
   */
  private String empoerId;

  /**
   * 授权类型(默认0标识拒绝,1标识允许)
   */
  private Integer type;

  /**
   * 被授权类型（默认0表示API,1标识资源）
   */
  private Integer powerType;

  /**
   * 授权资源uuid
   */
  private List<String> resourceSigns;

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
   * 服务uuid
   */
  private String serviceId;

  /**
   * 授权信息uuid
   */
  private String empowerId;

  /**
   * 当授权主体为角色时候，存角色对应的客户端名称
   */
  private String clientName;
}