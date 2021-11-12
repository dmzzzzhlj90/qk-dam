package com.qk.dam.sqlbuilder;
import com.alibaba.druid.DbType;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.qk.dam.sqlbuilder.enums.DataType;
import com.qk.dam.sqlbuilder.model.Column;
import com.qk.dam.sqlbuilder.model.Table;
import com.qk.dam.sqlbuilder.sqlparser.SqlParserFactory;
import tech.ibit.sqlbuilder.StringSql;

/**
 * SQL校验工厂
 *
 * @author wangzp
 * @date 2021/9/16 15:38
 * @since 1.0.0
 */
public class SqlBuilderFactory {
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
        sb.append("CREATE TABLE ").append(table.getName()).append(" (").append('\n');
        columns.forEach(column -> {
            sb.append("    ");
            // 字段名
            sb.append(column.getName());
            // 字段数据类型
            sb.append(" ").append(column.getDataType());
            // 字段数据长度
            if (column.getLength() != 0) {
                sb.append("(").append(column.getLength()).append(")");
            }
            // 字段主键
            if (column.getPrimaryKey()) {
                sb.append(" ").append("PRIMARY KEY");
                sb.append(" ").append( "AUTO_INCREMENT" );
            }
            // 字段是否为空
            if(column.getEmpty()){
                sb.append(" ").append("DEFAULT NULL");
            }else {
                sb.append(" ").append("NOT NULL");
            }
            // 字段注解
            if (Objects.nonNull(column.getComments())) {
                sb.append(" " + "COMMENT '").append(column.getComments()).append("',").append('\n');
            } else {
                sb.append("," + '\n');
            }
        });
        sb.deleteCharAt(sb.length() - 2);
        sb.append(");");
        return sb.toString();
    }

  public static void main(String[] args) {
      Table table = new Table();
      table.setName("user");
      table.addColumn(Column.builder().name("id").primaryKey(true).dataType(DataType.BIGINT).build());
      table.addColumn(Column.builder().name("name").primaryKey(false).dataType(DataType.VARCHAR).empty(true).build());
      System.out.println(creatTableSQL(table));
  }
}
