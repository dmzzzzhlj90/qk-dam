package com.qk.dam.indicator.common.sqlbuilder;

import tech.ibit.sqlbuilder.StringSql;
import java.util.Arrays;

/**
 * 组装SQL
 * @author wangzp
 * @date 2021/9/7 10:45
 * @since 1.0.0
 */
public class SqlBuilder {

    private static final String COMMA = ",";

    private static final ThreadLocal<StringSql> localSQL = new ThreadLocal<>();

    static {
        begin();
    }

    private static void begin() {
        reset();
    }

    private static void reset() {
        localSQL.set(new StringSql());
    }

    private static String sql() {
        try {
            return builder().getSqlParams().getSql();
        } finally {
            reset();
        }
    }

    private static StringSql builder(){
        return localSQL.get();
    }

    /**
     * 生产基础SQL
     * @param expression 查询的字段或函数
     * @param tableName 数据库表名
     * @return
     */
    public static String atomicSql(String expression,String tableName){
                 builder()
                .select(Arrays.asList(expression.split(COMMA)))
                .from(tableName);
        return sql();
    }

    /**
     * 生成业务SQL，条件限定
     * @param expression 查询的字段或函数
     * @param tableName 数据库表名
     * @param where 条件
     * @return
     */
    public static String derived(String expression,String tableName,
                                 String where){
                 builder()
                .select(Arrays.asList(expression.split(COMMA)))
                .from(tableName)
                .where(where);
        return sql();
    }


    /**
     * 表关联
     * @param expression 表达式（查询的字段）
     * @param tableName 表名称
     * @param where 查询条件
     * @param joinTable 关联的表
     * @return
     */
    public static String joinSql(String expression,String tableName,
                                 String where,String joinTable){
                 builder()
                .select(Arrays.asList(expression.split(COMMA)))
                .from(tableName)
                .where(where)
                .joinOn(joinTable);
        return sql();
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
                 builder()
                .select(Arrays.asList(expression.split(COMMA)))
                .from(tableName)
                .where(where)
                .orderBy(orderBy);
        return sql();
    }

    /**
     * 生成统计总数的SQL
     * @param tableName 表名
     * @param where 条件
     * @return
     */
    public static String count(String tableName,String where){
                 builder()
                .count()
                .from(tableName)
                .where(where);
        return sql();
    }

    /**
     * 生成统计总数的SQL，按列去重
     * @param tableName 表名
     * @param where 条件
     * @param column 去重字段
     * @return
     */
    public static String countDistinct(String tableName,String where,
                                       String column){
                  builder()
                 .countDistinct(column)
                 .from(tableName)
                 .where(where);
         return sql();
    }

    public static String genarSql(String tableName,String where, String column){
        builder()
                .count()
                .getSqlParams()
                .getSql();
        return sql();
    }

}
