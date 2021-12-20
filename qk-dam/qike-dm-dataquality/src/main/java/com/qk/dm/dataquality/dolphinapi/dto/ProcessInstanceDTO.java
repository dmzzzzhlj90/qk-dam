/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.qk.dm.dataquality.dolphinapi.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 流程实例
 * @author shenpengjie
 */
@Data
public class ProcessInstanceDTO implements Serializable {
  private int id;
  private int processDefinitionId;
  private String state;
  private String recovery;
  private String startTime;
  private String endTime;
  private int runTimes;
  private String name;
  private String host;
  private String processDefinition;
  private String commandType;
  private String commandParam;
  private String taskDependType;
  private int maxTryTimes;
  private String failureStrategy;
  private String warningType;
  private String warningGroupId;
  private String scheduleTime;
  private String commandStartTime;
  private String globalParams;
  private String processInstanceJson;
  private int executorId;
  private String executorName;
  private String tenantCode;
  private String queue;
  private String isSubProcess;
  private String locations;
  private String connects;
  private String historyCmd;
  private String dependenceScheduleTimes;
  private String duration;
  private String processInstancePriority;
  private String workerGroup;
  private int timeout;
  private int tenantId;
  private String receivers;
  private String receiversCc;
  private String cmdTypeIfComplement;
  private boolean complementData;
  private boolean processInstanceStop;
}
