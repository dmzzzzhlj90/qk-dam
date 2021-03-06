package com.qk.dam.model.constant;

/**
 * 模型状态常量
 */
public class ModelStatus {
    public static final int PUBLISH = 1; //已发布
    public static final int OFFLINE = 2;//已下线
    public static final int DRAFT=0;//草稿
    public static final String REPLACE="0";//逆向数据库更新
    public static final int NOREPLACE=1;//逆向数据库不更新
    public static final String CONNECT="HIVE";//校验数据库连接方式是HIVE
    public static final int SYNCHRONIZATION=0;//与数据库同步
    public static final int OUTOFSYNC=1;//与数据库不同步
    public static final int NO_EXIST = 0;//元数据判断库中不存在查询表
    public static final int EXIST_NO_DATA = 1;//元数据判断库中存在查询表但没有数据
    public static final int EXIST_DATA = 2;//元数据判断库中存在查询表且有数据
    public static final int EXIST_DEFAULT_DATA = 3;//查询元数据判断库中存在查询表默认返回值
    public static final String DIRNAME = "主题";//数据标准第一级目录名称
    public static final String CHECK="1";//校验主键、分区、是否为空
    public static final String SETDEFAULT="0";//默认不是主键不分区不为空
    public static final String ALLCHOICE="0";//逆向数据库全选
    public static final String PARTCHOICE="1";//逆向数据库部分
    public static final String DIRNAMEID = "-1";//数据标准第一级目录id
    public static final int FIRSTDIR=0;//获取前端传入的主题id数组第一个
    public static final String DATACONNECTION="hive";//基础信息数据源连接类型
    public static final String DATASOURCENAME="hive测试数据源";//基础信息数据源连接名称

}
