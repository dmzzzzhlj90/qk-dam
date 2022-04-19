package com.qk.dm.dataingestion.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 数据迁移基础信息表
 */
@Data
@Entity
@Table(name = "qk_dis_migration_base_info")
@Where(clause = "del_flag = 0 ")
public class DisMigrationBaseInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 规则分类id
     */
    @Column(name = "dir_id")
    private String dirId;
    /**
     * 规则分类名称
     */
    @Column(name = "dir_name")
    private String dirName;

    /**
     * 调度实例code
     */
    @Column(name = "task_code")
    private Long taskCode;

    /**
     * 作业名称
     */
    @Column(name = "job_name", nullable = false)
    private String jobName;
    /**
     * 源数据连接类型
     */
    @Column(name = "source_connect_type", nullable = false)
    private String sourceConnectType;
    /**
     * 源连接
     */
    @Column(name = "source_connect", nullable = false)
    private String sourceConnect;

    /**
     * 源数据库
     */
    @Column(name = "source_database", nullable = false)
    private String sourceDatabase;

    /**
     * 源表
     */
    @Column(name = "source_table")
    private String sourceTable;
    /**
     * 目标数据库类型
     */
    @Column(name = "target_connect_type")
    private String targetConnectType;
    /**
     * 目标连接
     */
    @Column(name = "target_connect", nullable = false)
    private String targetConnect;

    /**
     * 当要读取的文件路径，
     * 数据源为hive时显示且必填
     */
    @Column(name = "source_path")
    private String sourcePath;

    /**
     * Hadoop hdfs文件系统namenode节点地址
     * 数据源为hive时显示且必填
     */
    @Column(name = "source_default_fs")
    private String sourceDefaultFS;

    /**
     * 文件的类型，目前只支持用户配置为"text"、"orc"、"rc"、"seq"、"csv"
     * 当数据源为hive时显示且必填
     */
    @Column(name = "source_file_type")
    private String sourceFileType;

    @Column(name = "target_database", nullable = false)
    private String targetDatabase;

    /**
     * 表不存在时是否自动创建表
     */
    @Column(name = "auto_create")
    private String autoCreate;

    /**
     * 目标表
     */
    @Column(name = "target_table")
    private String targetTable;

    /**
     * 存储到Hadoop hdfs文件系统的路径信息
     * 当数据源是hive时显示且必填
     */
    @Column(name = "target_path")
    private String targetPath;

    /**
     * Hadoop hdfs文件系统namenode节点地址
     * 当数据源为hive时显示且是必填
     */
    @Column(name = "target_default_fs")
    private String targetDefaultFS;

    /**
     * 文件的类型，目前只支持用户配置为"text"或"orc"。
     * 当数据源是hive是显示且必填
     */
    @Column(name = "target_file_type")
    private String targetFileType;

    /**
     * hdfswriter写入前数据清理处理模式
     * 数据源是hive是显示且必填
     */
    @Column(name = "target_write_node")
    private String targetWriteMode;

    /**
     * hdfswriter写入时的字段分隔符
     * 数据源是hive是显示且必填
     */
    @Column(name = "target_field_delimiter")
    private String targetFieldDelimiter;


    /**
     * 导入前类型（导入前是否清除数据）
     */
    @Column(name = "before_import_type")
    private String beforeImportType;

    /**
     * 主键或分区字段
     */
    @Column(name = "primary_field")
    private String primaryField;

    /**
     * 耗时（单位秒）
     */
    @Column(name = "time_consuming")
    private Integer timeConsuming;

    /**
     * 待迁移数量
     */
    @Column(name = "wait_number")
    private Integer waitNumber;

    /**
     * 迁移中数量
     */
    @Column(name = "migration_number")
    private Integer migrationNumber;

    /**
     * 状态 0未运行 1运行中2运行成功3失败
     */
    @Column(name = "status", nullable = false)
    private Integer status;

    /**
     * 创建人
     */
    @Column(name = "create_userid")
    private String createUserid;

    /**
     * 修改人
     */
    @Column(name = "update_userid")
    private String updateUserid;

    /**
     * 常见时间
     */
    @Column(name = "gmt_create")
    @CreationTimestamp
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @Column(name = "gmt_modified")
    @UpdateTimestamp
    private Date gmtModified;

    /**
     * 0未删除 1已删除
     */
    @Column(name = "del_flag", nullable = false)
    private Integer delFlag = 0;

}
