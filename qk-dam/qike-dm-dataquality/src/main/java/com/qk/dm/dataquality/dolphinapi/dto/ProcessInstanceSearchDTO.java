package com.qk.dm.dataquality.dolphinapi.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author shenpj
 * @date 2021/11/30 3:05 下午
 * @since 1.0.0
 */
@Data
@Builder
public class ProcessInstanceSearchDTO implements Serializable {
  String projectName;
  /** 结束时间 */
  String endDate;
  /** EXECUTOR_NAME */
  String executorName;
  /** 运行任务的主机IP地址 */
  String host;
  Integer pageNo;
  Integer pageSize;
  Integer processDefinitionId;
  /** 搜索值 */
  String searchVal;
  /** 开始时间 */
  String startDate;
  /** 工作流和任务节点的运行状态 结果：InstanceStateTypeEnum.SUCCESS.getCode() */
  String stateType;
}
