package com.qk.dam.indicator.common.sqlbuilder;


import tech.ibit.sqlbuilder.StringSql;

import java.util.Arrays;

/**
 * 组装SQL
 * @author wangzp
 * @date 2021/9/7 10:45
 * @since 1.0.0
 */
public class SqlBuilderUtil {

    public static final String COMMA = ",";

    /**
     * 生产基础SQL
     * @param expression 查询的字段或函数
     * @param tableName 数据库表名
     * @return
     */
    public static String atomicSql(String expression,String tableName){
        StringSql sql = new StringSql()
                .select(Arrays.asList(expression.split(COMMA)))
                .from(tableName);
        return sql.getSqlParams().getSql();
    }

    /**
     * 生成业务SQL，条件限定
     * @param expression 查询的字段或函数
     * @param tableName 数据库表名
     * @param where 条件
     * @return
     */
    public static String derived(String expression,String tableName,String where){
        StringSql sql = new StringSql()
                .select(Arrays.asList(expression.split(COMMA)))
                .from(tableName)
                .where(where);
        return sql.getSqlParams().getSql();
    }

    /**
     * 生成SQL，支持排序
     * @param tableName 表名
     * @param expression 字段
     * @param where 条件
     * @param orderBy 排序字段
     * @return
     */
    public static String orderBy(String tableName,String expression,
                                 String where,String orderBy){
        StringSql sql = new StringSql()
                .select(Arrays.asList(expression.split(COMMA)))
                .from(tableName)
                .where(where)
                .orderBy(orderBy);
        return sql.getSqlParams().getSql();
    }

    /**
     * 生成统计总数的SQL
     * @param tableName 表名
     * @param where 条件
     * @return
     */
    public static String count(String tableName,String where){
        StringSql sql = new StringSql()
                .count()
                .from(tableName)
                .where(where);
        return sql.getSqlParams().getSql();
    }

    /**
     * 生成统计总数的SQL，按列去重
     * @param tableName 表名
     * @param where 条件
     * @param column 去重字段
     * @return
     */
    public static String countDistinct(String tableName,String where,String column){
         StringSql sql = new StringSql()
                 .countDistinct(column)
                 .from(tableName)
                 .where(where);
         return sql.getSqlParams().getSql();
    }

}
