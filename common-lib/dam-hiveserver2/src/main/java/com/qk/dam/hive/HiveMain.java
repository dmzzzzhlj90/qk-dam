package com.qk.dam.hive;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.Statements;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import static com.google.common.base.Charsets.UTF_8;

@Slf4j
public class HiveMain {

    private static final String FILE_PATH_SQL="sql.file";
    private static final List<String> QUERY_VAR_LIST= List.of("select","SELECT","show","SHOW");
    private static final Db USE = Db.use("hive");


    /**
     * 入口
     * @param args 参数
     */
    public static void main(String[] args) throws IOException, SQLException, JSQLParserException {
        String sqlFile = System.getProperty(FILE_PATH_SQL);
        // 读文件sql文件方式获取查询sql
        if (Objects.nonNull(sqlFile)){
            log.info("sql.file:【{}】",sqlFile);
            BufferedReader reader = ResourceUtil.getReader(sqlFile, UTF_8);
            StringBuilder sql = new StringBuilder();
            String temp = "";
            while ((temp = reader.readLine())!=null){
                if (!temp.contains("--")){
                    sql.append(temp);
                }
            }
            Statements stmt = CCJSqlParserUtil.parseStatements(sql.toString());
            for (Statement statement : stmt.getStatements()) {
                getExecSqL(statement.toString());
            }

                log.info("sql file exec sql:【{}】",sql);
        }
        // 从参数获取查询sql
        if (Objects.nonNull(args)&&args.length>0){
            for (String sql : args) {
                getExecSqL(sql);
                log.info("args exec sql:【{}】",sql);
            }
        }
        USE.closeConnection(USE.getConnection());
        if (Objects.isNull(sqlFile)&&(Objects.isNull(args)||args.length==0)){
            throw new NullPointerException("无sql输入，无法执行hive sql！！！");
        }

    }

    private static void getExecSqL(String sql) throws SQLException {
        if (containsStartsWith(sql)){
            List<Entity> entities = execSql(sql);
            log.info("测试hive库表数据[{}]",new Gson().toJson(entities));
        }else {
            log.info("非查询类语句:{}",sql);
            submitSql(sql);
        }
    }

    /**
     * 执行SQL
     * @param sql sql语句
     * @return List<Entity>
     * @throws SQLException sql异常
     */
    static List<Entity> execSql(String sql) throws SQLException {
        return USE.query(sql);
    }

    /**
     * 执行SQL 无返回值
     * @param sql sql
     */
    static void submitSql(String sql) throws SQLException{
        USE.execute(sql);
    }
    static Boolean containsStartsWith(String sql){
        for (String v : QUERY_VAR_LIST) {
            if (sql.trim().startsWith(v)){
                return true;
            }
        }
        return false;
    }

}
