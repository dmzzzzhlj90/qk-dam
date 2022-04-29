package com.qk.dm.dataingestion.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DisMigrationBaseInfoVO {

    private Long id;

    /**
     * 规则分类id
     */
    private String dirId;
    /**
     * 规则分类名称
     */
    private String dirName;
    /**
     * 调度实例code
     */
    private Long taskCode;

    /**
     * 作业名称
     */
    private String jobName;
    /**
     * 源数据连接类型
     */
    private String sourceConnectType;
    /**
     * 源连接
     */
    private String sourceConnect;

    /**
     * 源数据库
     */
    private String sourceDatabase;

    /**
     * 源表
     */
    private String sourceTable;

    /**
     * 当要读取的文件路径，
     * 数据源为hive时显示且必填
     */
    private String sourcePath;
    /**
     * Hadoop hdfs文件系统namenode节点地址
     * 数据源为hive时显示且必填
     */
    private String sourceDefaultFS;
    /**
     * 文件的类型，目前只支持用户配置为"text"、"orc"、"rc"、"seq"、"csv"
     * 当数据源为hive时显示且必填
     */
    private String sourceFileType;

    /**
     * 目标数据连接类型
     */
    private String targetConnectType;

    /**
     * 目标连接
     */
    private String targetConnect;

    /**
     * 目标数据库
     */
    private String targetDatabase;

    /**
     * 存储到Hadoop hdfs文件系统的路径信息
     * 当数据源是hive时显示且必填
     */
    private String targetPath;

    /**
     * Hadoop hdfs文件系统namenode节点地址
     * 当数据源为hive时显示且是必填
     */
    private String targetDefaultFS;

    /**
     * 文件的类型，目前只支持用户配置为"text"或"orc"。
     * 当数据源是hive是显示且必填
     */
    private String targetFileType;

    /**
     * hdfswriter写入时的字段分隔符
     * 数据源是hive是显示且必填
     */
    private String targetFieldDelimiter;

    /**
     * 表不存在时是否自动创建表（1表示自动创建）
     */
    private String autoCreate;

    /**
     * 目标表
     */
    private String targetTable;

    /**
     *导入前 （0 不清除 1清除）
     */
    private String targetWriteMode;

    /**
     * 主键或分区字段
     */
    private String primaryField;

    /**
     * where条件
     */
    private String whereCondition;

    /**
     * 耗时（单位秒）
     */
    private String timeConsuming;

    /**
     * 待迁移数量
     */
    private Integer waitNumber;

    /**
     * 迁移中数量
     */
    private Integer migrationNumber;

    /**
     * 状态 0未运行 1运行中2运行成功3失败
     */
    private Integer status;

    /**
     * 创建人
     */
    private String createUserid;

    /**
     * 修改人
     */
    private String updateUserid;

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

}
