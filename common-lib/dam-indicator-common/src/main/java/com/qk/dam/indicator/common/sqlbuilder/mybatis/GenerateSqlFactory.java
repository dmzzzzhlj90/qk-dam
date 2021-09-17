package com.qk.dam.indicator.common.sqlbuilder.mybatis;

import com.alibaba.druid.DbType;
import com.qk.dam.indicator.common.sqlbuilder.sqlparser.SqlParserFactory;
import org.apache.ibatis.jdbc.SQL;

/**
 * 组装SQL  (mybatis)
 * @author wangzp
 * @date 2021/9/9 15:21
 * @since 1.0.0
 */
public class GenerateSqlFactory {

    private static final ThreadLocal<SQL> localSQL = new ThreadLocal<>();

    private GenerateSqlFactory() {
    }

    static {
        begin();
    }

    public static void begin() {
        reset();
    }

    public static void reset() {
        localSQL.set(new SQL());
    }

    /**
     * 生产基础SQL
     * @param expression 查询的字段或函数
     * @param tableName 数据库表名
     * @return
     */
    public static String baseSql(String expression,String tableName){
                 builder()
                .SELECT(expression)
                .FROM(tableName);
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
                .SELECT(expression)
                .FROM(tableName)
                .WHERE(where);
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
                .SELECT(expression)
                .FROM(tableName)
                .WHERE(where)
                .JOIN(joinTable);
        return sql();
    }

    /**
     * 左关联SQL
     * @param expression 表达式（查询的字段）
     * @param table 表名
     * @param where 查询条件
     * @param leftJoinTable 左关联的表
     * @return
     */
    public static String leftJoin(String expression,String table,
                                  String where,String leftJoinTable){
                 builder()
                .SELECT(expression)
                .FROM(table)
                .WHERE(where)
                .LEFT_OUTER_JOIN(leftJoinTable);
        return sql();
    }

    /**
     * 右关联SQL
     * @param expression 表达式（查询的字段）
     * @param table 表名
     * @param where 查询条件
     * @param rightJoinTable 右关联表名
     * @return
     */
    public static String rightJoin(String expression,String table,
                                   String where,String rightJoinTable){
                 builder()
                .SELECT(expression)
                .FROM(table)
                .WHERE(where)
                .RIGHT_OUTER_JOIN(rightJoinTable);
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
                .SELECT(expression)
                .FROM(tableName)
                .WHERE(where)
                .ORDER_BY(orderBy);
        return sql();
    }


    public static void select(String columns) {
        builder().SELECT(columns);
    }

    public static void selectDistinct(String columns) {
        builder().SELECT_DISTINCT(columns);
    }

    public static void from(String table) {
        builder().FROM(table);
    }

    public static void join(String join) {
        builder().JOIN(join);
    }

    public static void innerJoin(String join) {
        builder().INNER_JOIN(join);
    }

    public static void leftOuterJoin(String join) {
        builder().LEFT_OUTER_JOIN(join);
    }

    public static void rightOuterJoin(String join) {
        builder().RIGHT_OUTER_JOIN(join);
    }

    public static void outerJoin(String join) {
        builder().OUTER_JOIN(join);
    }

    public static void where(String conditions) {
        builder().WHERE(conditions);
    }

    public static void or() {
        builder().OR();
    }

    public static void and() {
        builder().AND();
    }

    public static void groupBy(String columns) {
        builder().GROUP_BY(columns);
    }

    public static void having(String conditions) {
        builder().HAVING(conditions);
    }

    public static void orderBy(String columns) {
        builder().ORDER_BY(columns);
    }


    public static String sql() {
        try {
            String sql = builder().toString();
            boolean isSql = SqlParserFactory.parseStatements(sql, DbType.hive);
            if(isSql){
                return sql;
            }else {
                throw new RuntimeException("当前生成的SQL格式错误！！！");
            }
        } finally {
            reset();
        }
    }

    private static SQL builder() {
        return localSQL.get();
    }



}

