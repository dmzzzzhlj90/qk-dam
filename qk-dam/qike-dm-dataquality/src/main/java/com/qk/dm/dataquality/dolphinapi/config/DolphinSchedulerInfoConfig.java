package com.qk.dm.dataquality.dolphinapi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 调度引擎Dolphin Scheduler 规则调度配置信息
 *
 * @author wjq
 * @date 2021/11/17
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "dolphin-scheduler-info", ignoreInvalidFields = true)
@Component(value = "dolphinSchedulerInfoConfig")
public class DolphinSchedulerInfoConfig {

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 项目code
     */
    private Long projectCode;

    /**
     * 流程图DAG节点连接信息,默认[]
     */
    private String connects;

    //ResourceFileManager 文件资源
    /**
     * 文件顶级PID -1
     */
    private Integer parentPid;

    /**
     * mysql脚本文件资源路径
     */
    private String mysqlFullName;

    //##TenantManager 租户信息
    /**
     * 租户信息 root
     */
    private String tenantRoot;

    //##ProcessDataHandler 流程实例信息
    /**
     * 节点名称前缀匹配规则 tasks-
     */
    private String tasksNameMatch;

    /**
     * 运行标志 NORMAL正常 or FORBIDDEN 禁止
     */
    private String taskRunFlag;

    /**
     * 失败重试次数 默认 0
     */
    private Integer failRetryTimes;

    /**
     * 失败重试间隔 默认 1
     */
    private Integer failRetryInterval;

    /**
     * Worker分组 默认 "default"
     */
    private String taskWorkerGroup;

    /**
     * mysql脚本执行命令
     */
    private String mysqlExecuteCommand;

    /**
     * hive脚本执行命令
     */
    private String hiveExecuteCommand;

    /**
     * 任务节点类型
     */
    private String typeShell;

    /**
     * 运行环境Code
     */
    private Integer environmentCode;

    //##LocationsHandler
    /**
     * 节点位置编码 默认 0
     */
    private String locationNodeNumber;

    /**
     * 节点位置 X轴线 初始化值200
     */
    private Integer xLocationInitialValue;

    /**
     * 节点位置 Y轴线 初始化值200
     */
    private Integer yLocationInitialValue;

    /**
     * 节点位置增量值 默认150
     */
    private Integer locationIncrement;

    //##结果集存储目标数据库
    /**
     * 结果集存储 to_host
     */
    private String resultDataDbHost;

    /**
     * 结果集存储 to_user
     */
    private String resultDataDbUser;

    /**
     * 结果集存储 to_password
     */
    private String resultDataDbPassword;

    /**
     * 结果集存储 to_database
     */
    private String resultDataDbDatabase;

    /**
     * 动态实时sql获取url
     */
    private String sqlRpcUrl;

    /**
     * 告警表达结果获取url
     */
    private String warnRpcUrl;

}
