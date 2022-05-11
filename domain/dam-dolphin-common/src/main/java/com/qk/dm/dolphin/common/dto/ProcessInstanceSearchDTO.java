package com.qk.dm.dolphin.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author shenpj
 * @date 2021/11/30 3:05 下午
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessInstanceSearchDTO implements Serializable {
  /**
   * @param pageNo 页码号 (required)
   * @param pageSize 页大小 (required)
   * @param projectCode PROJECT_CODE (required)
   * @param endDate 结束时间 (optional)
   * @param executorName 流程名称 (optional)
   * @param host 运行任务的主机IP地址 (optional)
   * @param processDefineCode processDefineCode (optional, default to 0)
   * @param searchVal 搜索值 (optional)
   * @param startDate 开始时间 (optional)
   * @param stateType 工作流和任务节点的运行状态 (optional)
   */
  Integer pageNo;
  Integer pageSize;
  String endDate;
  String executorName;
  String host;
  Long processDefineCode;
  String searchVal;
  String startDate;
  String stateType;
}
