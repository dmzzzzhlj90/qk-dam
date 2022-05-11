package com.qk.dam.sqlbuilder;
import com.alibaba.druid.DbType;
import com.qk.dam.sqlbuilder.enums.DataType;
import com.qk.dam.sqlbuilder.model.Column;
import com.qk.dam.sqlbuilder.model.Table;
import com.qk.dam.sqlbuilder.sqlparser.SqlParserFactory;
import tech.ibit.sqlbuilder.StringSql;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * SQL校验工厂
 *
 * @author wangzp
 * @date 2021/9/16 15:41
 * @since 1.0.0
 */
public class SqlBuilderFactory {
    private static final String COMMA = ",";
    private static final String QUOTES="`";
    private static final String BLANK = " ";
    public static final String NEW_LINE="\n";

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
            String sql = builder().getSqlParams().getSql();
            // 校验SQL是否正确
            boolean isSql = SqlParserFactory.parseStatements(sql, DbType.hive);
            if (isSql) {
                return sql;
            } else {
                throw new RuntimeException("当前生成的SQL格式错误！！！");
            }
        } finally {
            reset();
        }
    }

    private static StringSql builder() {
        return localSQL.get();
    }

    /**
     * 生产基础SQL
     *
     * @param expression 查询的字段或函数
     * @param tableName 数据库表名
     * @return
     */
    public static String atomicSql(String expression, String tableName) {
        builder().select(Arrays.asList(expression.split(COMMA))).from(tableName);
        return sql();
    }

    /**
     * 组装删除语句
     * @param tableName 表名称
     * @return String 删除语句
     */
    public static String deleteSql(String tableName){
        builder().deleteFrom(tableName).where("1=1");
        return sql();
    }

    /**
     * 生成业务SQL，条件限定
     *
     * @param expression 查询的字段或函数
     * @param tableName 数据库表名
     * @param where 条件
     * @return
     */
    public static String derived(String expression, String tableName, String where) {
        builder().select(Arrays.asList(expression.split(COMMA))).from(tableName).where(where);
        return sql();
    }

    /**
     * 表关联
     *
     * @param expression 表达式（查询的字段）
     * @param tableName 表名称
     * @param where 查询条件
     * @param joinTable 关联的表
     * @return
     */
    public static String joinSql(
            String expression, String tableName, String where, String joinTable) {
        builder()
                .select(Arrays.asList(expression.split(COMMA)))
                .from(tableName)
                .where(where)
                .joinOn(joinTable);
        return sql();
    }

    /**
     * 生成SQL，支持排序
     *
     * @param tableName 表名
     * @param expression 字段
     * @param where 条件
     * @param orderBy 排序字段
     * @return
     */
    public static String orderBy(String tableName, String expression, String where, String orderBy) {
        builder()
                .select(Arrays.asList(expression.split(COMMA)))
                .from(tableName)
                .where(where)
                .orderBy(orderBy);
        return sql();
    }

    /**
     * 生成统计总数的SQL
     *
     * @param tableName 表名
     * @param where 条件
     * @return
     */
    public static String count(String tableName, String where) {
        builder().count().from(tableName).where(where);
        return sql();
    }

    /**
     * 生成统计总数的SQL，按列去重
     *
     * @param tableName 表名
     * @param where 条件
     * @param column 去重字段
     * @return
     */
    public static String countDistinct(String tableName, String where, String column) {
        builder().countDistinct(column).from(tableName).where(where);
        return sql();
    }

    public static String generaSql(String tableName, String where, String column) {
        builder().count().getSqlParams().getSql();
        return sql();
    }
    /**
     * 构建建表 SQL 语句, 分析 Table 模型数据, 拼接 SQL 语句
     *
     * @param table 数据库表模型对象
     * @return 建表 SQL 语句
     */
    public static String creatTableSQL(Table table) {
        StringBuffer sb = new StringBuffer();
        List<Column> columns = table.getColumns();
        // 表名
        sb.append("CREATE TABLE IF NOT EXISTS ").append(QUOTES).append(table.getName())
                .append(QUOTES).append(" (").append(NEW_LINE);
        columns.forEach(column -> {
            assemblyColumn(column,sb);
            // 字段主键
            if (column.getPrimaryKey()) {
                sb.append(BLANK).append( "AUTO_INCREMENT" );
            }
            // 字段注解
            if (Objects.nonNull(column.getComments())) {
                sb.append(BLANK + "COMMENT ").append(QUOTES).append(column.getComments())
                        .append(QUOTES)
                        .append(COMMA).append(NEW_LINE);
            } else {
                sb.append(COMMA +NEW_LINE);
            }
        });
        List<Column> collects = columns.stream().filter(Column::getPrimaryKey).collect(Collectors.toList());
        collects.forEach(e->{
            sb.append("   " +" PRIMARY KEY (`").append(e.getName()).append("`)"+COMMA+ NEW_LINE);
        });
        return  sb.substring(0,sb.lastIndexOf(COMMA+NEW_LINE))+NEW_LINE+");";
    }

    /**
     * 组装字段
     * @param column
     * @return
     */
    private static void assemblyColumn(Column column,StringBuffer sb){
        sb.append("    ");
        // 字段名
        sb.append(QUOTES).append(column.getName()).append(QUOTES);
        // 字段数据类型
        sb.append(BLANK).append(column.getDataType());
        // 字段数据长度
        if (column.getLength() != 0) {
            sb.append("(").append(column.getLength()).append(")");
        }
        // 字段是否为空
        if (column.getEmpty()) {
            sb.append(BLANK).append("DEFAULT NULL");
        } else {
            sb.append(BLANK).append("NOT NULL");
        }
    }

}
