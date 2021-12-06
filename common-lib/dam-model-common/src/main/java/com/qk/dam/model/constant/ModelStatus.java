package com.qk.dam.model.constant;

/**
 * 模型状态常量
 */
public class ModelStatus {
    public static final int PUBLISH = 1; //已发布
    public static final int OFFLINE = 2;//已下线
    public static final int DRAFT=0;//草稿
    public static final int REPLACE=0;//逆向数据库更新
    public static final int NOREPLACE=1;//逆向数据库不更新
    public static final String CONNECT="HIVE";//校验数据库连接方式是HIVE
    public static final int SYNCHRONIZATION=0;//与数据库同步
    public static final int OUTOFSYNC=1;//与数据库不同步
    public static final int NO_EXIST = 0;//元数据判断库中不存在查询表
    public static final int EXIST_NO_DATA = 1;//元数据判断库中存在查询表但没有数据
    public static final int EXIST_DATA = 2;//元数据判断库中存在查询表且有数据
}
