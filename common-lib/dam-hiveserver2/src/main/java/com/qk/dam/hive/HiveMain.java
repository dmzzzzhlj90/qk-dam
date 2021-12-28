package com.qk.dam.hive;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.simple.SimpleDataSource;
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
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * @author zhudaoming
 */
@Slf4j
public class HiveMain {

    private static final String jsonconfig = "{\"from_host\":\"172.21.32.4\",\"from_user\":\"root\",\"from_password\":\"\",\"from_database\":\"hd_company\",\"to_host\":\"172.21.33.141\",\"to_user\":\"root\",\"to_password\":\"JMFIuOx2\",\"to_database\":\"qkdam\",\"job_id\":\"a01dd381ccb84a2daea73b40d4c38718\",\"job_name\":\"测试动态实时sql生成1\",\"rule_id\":\"f7eb729b37894434af8701b040a7c635\",\"rule_name\":\"RULE_TYPE_FIELD/hd_company/qk_dqc_sql_test/code\",\"rule_temp_id\":1,\"task_code\":3955850086112,\"sql_rpc_url\":\"http://main-dev.dam.qk.com:31352/dqc/sql/build/realtime/python?ruleId=bc032b997d044f6b8b2454654f6ed90a\"}";
    private static final String FILE_PATH_SQL="sql.file";
    private static final List<String> QUERY_VAR_LIST= List.of("select","SELECT","show","SHOW");
    private static Db DB;


    /**
     * 入口
     * @param args 参数
     */
    public static void main(String[] args) throws Exception {
//        String jsonConf = args[0];
        MysqlRawScript mysqlRawScript = new Gson().fromJson(jsonconfig, MysqlRawScript.class);
        String sqlRpcUrl = mysqlRawScript.getSql_rpc_url();
        // 查询库
        String fromHost = mysqlRawScript.getFrom_host();
        String fromUser = mysqlRawScript.getFrom_user();
        String fromPassword = mysqlRawScript.getFrom_password();
        String fromDatabase = mysqlRawScript.getFrom_database();
        DB = getDb(fromDatabase, fromHost, fromUser, fromPassword, DbTypeEnum.HIVE);
        String sqlScript = generateSqlScript(sqlRpcUrl);
        List<Entity> entities = runSqL("select count(nacaoid) as n_count from company where nacaoid is null or nacaoid=''");

        System.out.println(new Gson().toJson(entities));


        // 结果库
        String toHost = mysqlRawScript.getTo_host();
        String toUser = mysqlRawScript.getTo_user();
        String toPassword = mysqlRawScript.getTo_password();
        String toDatabase = mysqlRawScript.getTo_database();
        DB = getDb(toDatabase, toHost, toUser, toPassword, DbTypeEnum.MYSQL);


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
                                        + "/"+database,
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



    private static List<Entity> runSqL(String sql) throws SQLException {
        if (containsStartsWith(sql)){
            List<Entity> entities = execSql(sql);
            log.info("测试hive库表数据[{}]",new Gson().toJson(entities));
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
    static List<Entity> execSql(String sql) throws SQLException {
        return DB.query(sql);
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

//    private static void execSQL(String[] args) throws IOException, SQLException, JSQLParserException{
//        String sqlFile = System.getProperty(FILE_PATH_SQL);
//        // 读文件sql文件方式获取查询sql
//        if (Objects.nonNull(sqlFile)){
//            log.info("sql.file:【{}】",sqlFile);
//            BufferedReader reader = ResourceUtil.getReader(sqlFile, UTF_8);
//            StringBuilder sql = new StringBuilder();
//            String temp = "";
//            while ((temp = reader.readLine())!=null){
//                if (!temp.contains("--")){
//                    sql.append(temp);
//                }
//            }
//            Statements stmt = CCJSqlParserUtil.parseStatements(sql.toString());
//            for (Statement statement : stmt.getStatements()) {
//                getExecSqL(statement.toString());
//            }
//
//            log.info("sql file exec sql:【{}】",sql);
//        }
//        // 从参数获取查询sql
//        if (Objects.nonNull(args)&&args.length>0){
//            for (String sql : args) {
//                getExecSqL(sql);
//                log.info("args exec sql:【{}】",sql);
//            }
//        }
//        if (Objects.isNull(sqlFile)&&(Objects.isNull(args)||args.length==0)){
//            throw new NullPointerException("无sql输入，无法执行hive sql！！！");
//        }
//    }

}
