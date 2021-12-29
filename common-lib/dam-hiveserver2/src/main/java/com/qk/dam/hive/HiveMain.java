package com.qk.dam.hive;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.db.*;
import cn.hutool.db.ds.simple.SimpleDataSource;
import cn.hutool.db.handler.EntityListHandler;
import cn.hutool.db.sql.SqlExecutor;
import com.beust.jcommander.internal.Lists;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.Statements;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;


/**
 * @author zhudaoming
 */
@Slf4j
public class HiveMain {

    private static final String RST_TABLE="qk_dqc_scheduler_result";
    private static final List<String> QUERY_VAR_LIST= List.of("select","SELECT","show","SHOW");
    private static Db DB;


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
        ResultTable resultTable = new ResultTable(jobId, jobName, ruleId, ruleName, ruleTempId, taskCode, null, "0", new Date(), new Date());

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
        }
        List<Entity> entities = null;
        try {
            entities = runSqL(sqlScript);
            List<Object[]> rst = entities.stream()
                    .map(entity -> entity.values().toArray())
                    .collect(Collectors.toList());
            resultTable.setRule_result(new Gson().toJson(rst));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("执行质量校验SQL发生错误:【{}】",e.getLocalizedMessage());
        }

        // 结果库
        String toHost = mysqlRawScript.getTo_host();
        String toUser = mysqlRawScript.getTo_user();
        String toPassword = mysqlRawScript.getTo_password();
        String toDatabase = mysqlRawScript.getTo_database();
        log.info("质量规则执行结果存储到【toHost:{}】【toUser:{}】【toDatabase:{}】",toHost,toUser,toDatabase);
        DB = getDb(toDatabase, toHost, toUser, toPassword, DbTypeEnum.MYSQL);
        try {
            DB.insert(Entity.create(RST_TABLE).parseBean(resultTable));
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("执行添加结果数据失败:【{}】",e.getLocalizedMessage());
        }


    }

    static String generateSqlScript(String sqlRpcUrl) throws Exception {
        HttpClient httpClient = new ApiClient().getHttpClient();
        HttpResponse<InputStream> localVarResponse = httpClient.send(generateSqlCall(sqlRpcUrl),
                HttpResponse.BodyHandlers.ofInputStream());
        if (localVarResponse.statusCode()/ 100 != 2) {
            throw new Exception("请求返回错误");
        }
        InputStream body = localVarResponse.body();
        Map<String,Object> jsonMap = new Gson().fromJson(new String(body.readAllBytes()), HashMap.class);
        log.info("请求执行规则查询sql:【{}】",jsonMap.get("tips"));
        return String.valueOf(jsonMap.get("tips"));
    }

    static Db getDb(String database,
                    String host,
                    String user,
                    String password,
                    DbTypeEnum dbTypeEnum) {
        return Db.use(
                        new SimpleDataSource(
                                "jdbc:"+dbTypeEnum.getSchema()+"://"
                                        + host
                                        + ":"
                                        + dbTypeEnum.getPort()
                                        + "/"+database
                                ,
                                Objects.requireNonNullElse(user,"default"),
                                password,dbTypeEnum.getDriverName()));
    }

    private static HttpRequest generateSqlCall(String sqlRpcUrl)  {
        HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

        localVarRequestBuilder.uri(URI.create(sqlRpcUrl));

        localVarRequestBuilder.header("Accept", "application/json");

//        localVarRequestBuilder.method("POST", HttpRequest.BodyPublishers.noBody());

        return localVarRequestBuilder.build();
    }



    private static List<Entity> runSqL(String sql) throws Exception {
        if (containsStartsWith(sql)){
            List<Entity> entities = execSql(sql);
            log.info("SQL结果数据[{}]",new Gson().toJson(entities));
            return entities;
        }else {
            log.info("非查询类语句:{}",sql);
            submitSql(sql);
            return Lists.newArrayList();
        }
    }

    /**
     * 执行SQL
     * @param sql sql语句
     * @return List<Entity>
     * @throws SQLException sql异常
     */
    static List<Entity> execSql(String sql) throws Exception {
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

    /**
     * 执行SQL 无返回值
     * @param sql sql
     */
    static void submitSql(String sql) throws SQLException{
        DB.execute(sql);
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
