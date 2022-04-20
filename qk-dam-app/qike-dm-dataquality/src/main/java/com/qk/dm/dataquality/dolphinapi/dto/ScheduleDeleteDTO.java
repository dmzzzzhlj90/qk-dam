package com.qk.dm.dataquality.dolphinapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * @author shenpj
 * @date 2021/11/30 3:53 下午
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDeleteDTO implements Serializable {
  /**
   * deleteScheduleById
   * 定时下线
   * @param id 定时ID (required)
   * @param projectCode PROJECT_CODE (required)
   * @param alertGroup  (optional)
   * @param createTime  (optional)
   * @param email  (optional)
   * @param phone  (optional)
   * @param queue  (optional)
   * @param queueName  (optional)
   * @param state  (optional)
   * @param tenantCode  (optional)
   * @param tenantId  (optional)
   * @param updateTime  (optional)
   * @param userName  (optional)
   * @param userPassword  (optional)
   * @param userType  (optional)
   */
  Integer scheduleId;
  String alertGroup;
  OffsetDateTime createTime;
  String email;
  String phone;
  String queue;
  String queueName;
  Integer state;
  String tenantCode;
  Integer tenantId;
  OffsetDateTime updateTime;
  String userName;
  String userPassword;
  String userType;
}
