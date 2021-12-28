package com.qk.dm.dataquality.dolphinapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * @author shenpj
 * @date 2021/12/9 12:03 下午
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessInstanceDeleteDTO {
    /**
     * batchDeleteProcessInstanceByIds
     *
     * @param processInstanceIds processInstanceIds (required)
     * @param projectName projectName (required)
     * @param alertGroup  (optional)
     * @param createTime  (optional)
     * @param email  (optional)
     * @param id  (optional)
     * @param phone  (optional)
     * @param queue  (optional)
     * @param queueName  (optional)
     * @param tenantCode  (optional)
     * @param tenantId  (optional)
     * @param tenantName  (optional)
     * @param updateTime  (optional)
     * @param userName  (optional)
     * @param userPassword  (optional)
     * @param userType  (optional)
     */

       /** @param processInstanceIds 流程实例ID集合 (required)
   * @param projectCode projectCode (required)
   * @param alertGroup  (optional)
   * @param createTime  (optional)
   * @param email  (optional)
   * @param id  (optional)
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
    String processInstanceIds;
    String alertGroup;
    OffsetDateTime createTime;
    String email;
    Integer id;
    String phone;
    String queue;
    String queueName;
    Integer state;
    String tenantCode;
    Integer tenantId;
//    String tenantName;
    OffsetDateTime updateTime;
    String userName;
    String userPassword;
    String userType;
}
