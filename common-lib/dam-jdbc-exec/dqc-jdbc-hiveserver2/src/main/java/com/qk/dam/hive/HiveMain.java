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
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.qk.dam.jdbc.util.DbUtil.*;

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

        // 任务基本信息
        String jobId = mysqlRawScript.getJob_id();
        String ruleId = mysqlRawScript.getRule_id();
        String jobName = mysqlRawScript.getJob_name();
        String ruleName = mysqlRawScript.getRule_name();
        Long ruleTempId = mysqlRawScript.getRule_temp_id();
        Long taskCode = mysqlRawScript.getTask_code();
        ResultTable resultTable = new ResultTable(jobId, jobName, ruleId, ruleName, ruleTempId, taskCode,mysqlRawScript.getRule_meta_data(),null, null, "0", new Date(), new Date());

        // 查询库
        String fromHost = mysqlRawScript.getFrom_host();
        String fromUser = mysqlRawScript.getFrom_user();
        String fromPassword = mysqlRawScript.getFrom_password();
        String fromDatabase = mysqlRawScript.getFrom_database();

        log.info("质量规则执行端信息【fromHost:{}】【fromUser:{}】【fromDatabase:{}】",fromHost,fromUser,fromDatabase);
        DB = getDb(fromDatabase, fromHost, fromUser, fromPassword, DbTypeEnum.HIVE);
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

        // 结果库
        String toHost = mysqlRawScript.getTo_host();
        String toUser = mysqlRawScript.getTo_user();
        String toPassword = mysqlRawScript.getTo_password();
        String toDatabase = mysqlRawScript.getTo_database();
        log.info("质量规则执行结果存储到【toHost:{}】【toUser:{}】【toDatabase:{}】",toHost,toUser,toDatabase);
        DB = getDb(toDatabase, toHost, toUser, toPassword, DbTypeEnum.MYSQL);
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
        Statements stmt = CCJSqlParserUtil.parseStatements(sql.toString());
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
