package com.qk.dam.jdbc.util;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.simple.SimpleDataSource;
import cn.hutool.db.handler.EntityListHandler;
import cn.hutool.db.sql.SqlExecutor;
import com.google.gson.Gson;
import com.qk.dam.jdbc.ApiClient;
import com.qk.dam.jdbc.DbTypeEnum;
import com.qk.dam.jdbc.MysqlRawScript;
import com.qk.dam.jdbc.ResultTable;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.Statements;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * @author zhudaoming
 */
@Slf4j
public class JdbcSqlUtil {
    public static final String RST_TABLE = "qk_dqc_scheduler_result";
    protected static final List<String> QUERY_VAR_LIST = List.of("select", "SELECT", "show", "SHOW");
    public static Db DB;

    public static String generateSqlScript(String sqlRpcUrl) throws Exception {
        HttpClient httpClient = new ApiClient().getHttpClient();
        HttpResponse<InputStream> localVarResponse = httpClient.send(generateSqlCall(sqlRpcUrl),
                HttpResponse.BodyHandlers.ofInputStream());
        if (localVarResponse.statusCode() / 100 != 2) {
            throw new Exception("请求返回错误"+localVarResponse.statusCode());
        }
        InputStream body = localVarResponse.body();
        Map<String, Object> jsonMap = new Gson().fromJson(new String(body.readAllBytes()), HashMap.class);
        log.info("请求执行规则查询sql:【{}】", jsonMap.get("data"));
        return String.valueOf(jsonMap.get("data"));
    }

    public static String generateWarnRst(String warnRpcUrl) throws Exception {
        HttpClient httpClient = new ApiClient().getHttpClient();
        HttpResponse<InputStream> localVarResponse = httpClient.send(generateSqlCall(warnRpcUrl),
                HttpResponse.BodyHandlers.ofInputStream());
        if (localVarResponse.statusCode() / 100 != 2) {
            throw new Exception("请求返回错误");
        }
        InputStream body = localVarResponse.body();
        Map<String, Object> jsonMap = new Gson().fromJson(new String(body.readAllBytes()), HashMap.class);
        log.info("请求执行规则查询sql:【{}】", jsonMap.get("data"));
        return String.valueOf(jsonMap.get("data"));
    }
    public static ResultTable getResultTable(MysqlRawScript mysqlRawScript) {
        // 任务基本信息
        String jobId = mysqlRawScript.getJob_id();
        String ruleId = mysqlRawScript.getRule_id();
        String jobName = mysqlRawScript.getJob_name();
        String ruleName = mysqlRawScript.getRule_name();
        Long ruleTempId = mysqlRawScript.getRule_temp_id();
        Long taskCode = mysqlRawScript.getTask_code();
        return new ResultTable(jobId, jobName, ruleId, ruleName, ruleTempId, taskCode, mysqlRawScript.getRule_meta_data(),null, null, "0", new Date(), new Date());
    }
    public static Db getFromDb(MysqlRawScript mysqlRawScript, DbTypeEnum dbTypeEnum) {
        // 查询库
        String fromHost = mysqlRawScript.getFrom_host();
        String fromPort = mysqlRawScript.getFrom_port();
        String fromUser = mysqlRawScript.getFrom_user();
        String fromPassword = mysqlRawScript.getFrom_password();
        String fromDatabase = mysqlRawScript.getFrom_database();

        String fullHost = "";
        if (Objects.nonNull(fromPort) && Objects.nonNull(fromDatabase)) {
            fullHost = fromHost
                    + ":"
                    + fromPort
                    + "/" + fromDatabase;
        } else {
            fullHost = fromHost;
        }
        log.info("质量规则执行端信息【fromHost:{}】【fromUser:{}】【fromDatabase:{}】", fromHost, fromUser, fromDatabase);
        return Db.use(
                new SimpleDataSource(
                        "jdbc:" + dbTypeEnum.getSchema() + "://"
                                + fullHost,
                        Objects.requireNonNullElse(fromUser, "default"),
                        fromPassword, dbTypeEnum.getDriverName()));
    }

    public static Db getToDb(MysqlRawScript mysqlRawScript, DbTypeEnum dbTypeEnum) {
        // 结果库
        String toHost = mysqlRawScript.getTo_host();
        String toPort = mysqlRawScript.getTo_port();
        String toUser = mysqlRawScript.getTo_user();
        String toPassword = mysqlRawScript.getTo_password();
        String toDatabase = mysqlRawScript.getTo_database();
        log.info("质量规则执行结果存储到【toHost:{}】【toUser:{}】【toDatabase:{}】",toHost,toUser,toDatabase);

        String fullHost = "";
        if (Objects.nonNull(toPort) && Objects.nonNull(toDatabase)) {
            fullHost = toHost
                    + ":"
                    + toPort
                    + "/" + toDatabase;
        } else {
            fullHost = toHost;
        }
        return Db.use(
                new SimpleDataSource(
                        "jdbc:" + dbTypeEnum.getSchema() + "://"
                                + fullHost,
                        Objects.requireNonNullElse(toUser, "default"),
                        toPassword, dbTypeEnum.getDriverName()));
    }

    public static HttpRequest generateSqlCall(String sqlRpcUrl) {
        HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

        localVarRequestBuilder.uri(URI.create(sqlRpcUrl));

        localVarRequestBuilder.header("Accept", "application/json");

        return localVarRequestBuilder.build();
    }


    public static List<Entity> runSqL(String sql) throws Exception {
        if (containsStartsWith(sql)) {
            List<Entity> entities = execSql(sql);
            log.info("SQL结果数据[{}]", new Gson().toJson(entities));
            return entities;
        } else {
            log.info("非查询类语句:{}", sql);
            submitSql(sql);
            return List.of();
        }
    }

    /**
     * 执行SQL
     *
     * @param sql sql语句
     * @return List<Entity>
     * @throws SQLException sql异常
     */
    public static List<Entity> execSql(String sql) throws Exception {
        List<Entity> entities = new ArrayList<>(100);
        Statements stmt = CCJSqlParserUtil.parseStatements(sql.toString());
        for (Statement st : stmt.getStatements()) {
            java.sql.Statement statement = null;
            try {
                Connection connection = DB.getConnection();
                statement = connection.createStatement();
                EntityListHandler rsh = new EntityListHandler(true);
                entities.addAll(SqlExecutor.query(connection, st.toString(), rsh));
            } finally {
                cn.hutool.db.DbUtil.close(statement);
            }
        }

        return entities;

    }

    /**
     * 执行SQL 无返回值
     *
     * @param sql sql
     */
    public static void submitSql(String sql) throws SQLException {
        DB.execute(sql);
    }

    public static Boolean containsStartsWith(String sql) {
        for (String v : QUERY_VAR_LIST) {
            if (sql.trim().startsWith(v)) {
                return true;
            }
        }
        return false;
    }
}
