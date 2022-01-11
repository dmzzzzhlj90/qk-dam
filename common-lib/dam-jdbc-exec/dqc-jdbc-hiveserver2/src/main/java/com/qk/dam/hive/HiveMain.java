package com.qk.dam.hive;

import cn.hutool.db.DbUtil;
import cn.hutool.db.Entity;
import cn.hutool.db.handler.EntityListHandler;
import cn.hutool.db.sql.SqlExecutor;
import com.google.gson.Gson;
import com.qk.dam.jdbc.DbTypeEnum;
import com.qk.dam.jdbc.MysqlRawScript;
import com.qk.dam.jdbc.ResultTable;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.Statements;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.qk.dam.jdbc.util.JdbcSqlUtil.*;

/**
 * @author zhudaoming
 */
@Slf4j
public class HiveMain {

    /**
     * 入口
     * @param args 参数
     */
    public static void main(String[] args) {
        String jsonconfig = args[0];
        MysqlRawScript mysqlRawScript = new Gson().fromJson(jsonconfig, MysqlRawScript.class);
        String sqlRpcUrl = mysqlRawScript.getSql_rpc_url();
        ResultTable resultTable = getResultTable(mysqlRawScript);

        DB = getFromDb(mysqlRawScript, DbTypeEnum.HIVE);
        String sqlScript = null;
        try {
            sqlScript = generateSqlScript(sqlRpcUrl);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("请求生产校验SQL错误:【{}】",e.getLocalizedMessage());
            System.exit(-1);
        }
        List<Entity> entities = null;
        try {
            entities = runHiveSqL(sqlScript);
            List<Object[]> rst = entities.stream()
                    .map(entity -> entity.values().toArray())
                    .collect(Collectors.toList());
            resultTable.setRule_result(new Gson().toJson(rst));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("执行质量校验SQL发生错误:【{}】",e.getLocalizedMessage());
            System.exit(-1);
        }

        DB = getToDb(mysqlRawScript, DbTypeEnum.MYSQL);
        try {
            String warnRst = generateWarnRst(mysqlRawScript.getWarn_rpc_url());
            resultTable.setWarn_result(warnRst);
            log.info("插入结果数据入库【{}】",new Gson().toJson(resultTable));
            DB.insert(Entity.create(RST_TABLE).parseBean(resultTable));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("执行添加结果数据失败:【{}】",e.getLocalizedMessage());
            System.exit(-1);
        }


    }

    public static List<Entity> runHiveSqL(String sql) throws Exception {
        if (containsStartsWith(sql)){
            List<Entity> entities = execHiveSql(sql);
            log.info("SQL结果数据[{}]",new Gson().toJson(entities));
            return entities;
        }else {
            log.info("非查询类语句:{}",sql);
            submitSql(sql);
            return List.of();
        }
    }
    /**
     * 执行SQL
     * @param sql sql语句
     * @return List<Entity>
     * @throws SQLException sql异常
     */
    static List<Entity> execHiveSql(String sql) throws Exception {
        List<Entity> entities = new ArrayList<>(100);
        Statements stmt = CCJSqlParserUtil.parseStatements(sql);
        for (Statement st : stmt.getStatements()) {
            java.sql.Statement statement = null;
            try {
                Connection connection = DB.getConnection();
                statement = connection.createStatement();
                // todo 暂时支持cross join，已实现合并结果输出
                statement.execute("set hive.mapred.mode='strict'");
                EntityListHandler rsh = new EntityListHandler(true);
                entities.addAll(SqlExecutor.query(connection, st.toString(), rsh));
            } finally {
                DbUtil.close(statement);
            }
        }

        return entities;

    }

}
