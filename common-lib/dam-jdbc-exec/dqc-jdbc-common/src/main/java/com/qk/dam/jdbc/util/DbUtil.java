package com.qk.dam.jdbc.util;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.simple.SimpleDataSource;
import cn.hutool.db.handler.EntityListHandler;
import cn.hutool.db.sql.SqlExecutor;
import com.google.gson.Gson;
import com.qk.dam.jdbc.ApiClient;
import com.qk.dam.jdbc.DbTypeEnum;
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
public class DbUtil {
    public static final String RST_TABLE="qk_dqc_scheduler_result";
    protected static final List<String> QUERY_VAR_LIST= List.of("select","SELECT","show","SHOW");
    public static Db DB;

   public static String generateSqlScript(String sqlRpcUrl) throws Exception {
        HttpClient httpClient = new ApiClient().getHttpClient();
        HttpResponse<InputStream> localVarResponse = httpClient.send(generateSqlCall(sqlRpcUrl),
                HttpResponse.BodyHandlers.ofInputStream());
        if (localVarResponse.statusCode()/ 100 != 2) {
            throw new Exception("请求返回错误");
        }
        InputStream body = localVarResponse.body();
        Map<String,Object> jsonMap = new Gson().fromJson(new String(body.readAllBytes()), HashMap.class);
        log.info("请求执行规则查询sql:【{}】",jsonMap.get("data"));
        return String.valueOf(jsonMap.get("data"));
    }

    public static String generateWarnRst(String warnRpcUrl) throws Exception {
        HttpClient httpClient = new ApiClient().getHttpClient();
        HttpResponse<InputStream> localVarResponse = httpClient.send(generateSqlCall(warnRpcUrl),
                HttpResponse.BodyHandlers.ofInputStream());
        if (localVarResponse.statusCode()/ 100 != 2) {
            throw new Exception("请求返回错误");
        }
        InputStream body = localVarResponse.body();
        Map<String,Object> jsonMap = new Gson().fromJson(new String(body.readAllBytes()), HashMap.class);
        log.info("请求执行规则查询sql:【{}】",jsonMap.get("data"));
        return String.valueOf(jsonMap.get("data"));
    }

    public static Db getDb(String database,
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

    public static HttpRequest generateSqlCall(String sqlRpcUrl)  {
        HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

        localVarRequestBuilder.uri(URI.create(sqlRpcUrl));

        localVarRequestBuilder.header("Accept", "application/json");

//        localVarRequestBuilder.method("POST", HttpRequest.BodyPublishers.noBody());

        return localVarRequestBuilder.build();
    }



    public static List<Entity> runSqL(String sql) throws Exception {
        if (containsStartsWith(sql)){
            List<Entity> entities = execSql(sql);
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
     * @param sql sql
     */
    public  static void submitSql(String sql) throws SQLException{
        DB.execute(sql);
    }
    public static Boolean containsStartsWith(String sql){
        for (String v : QUERY_VAR_LIST) {
            if (sql.trim().startsWith(v)){
                return true;
            }
        }
        return false;
    }
}
