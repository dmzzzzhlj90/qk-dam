package com.qk.dm.dataquality.dolphinapi.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * @author shenpj
 * @date 2021/11/30 3:53 下午
 * @since 1.0.0
 */
@Data
@Builder
public class ScheduleDeleteDTO implements Serializable {
  String projectName;
  Integer scheduleId;
  String alertGroup;
  OffsetDateTime createTime;
  String email;
  Integer id;
  String phone;
  String queue;
  String queueName;
  String tenantCode;
  Integer tenantId;
  String tenantName;
  java.time.OffsetDateTime updateTime;
  String userName;
  String userPassword;
  String userType;
}
