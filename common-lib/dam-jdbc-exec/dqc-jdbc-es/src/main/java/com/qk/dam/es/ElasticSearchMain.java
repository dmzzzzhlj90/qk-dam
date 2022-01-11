package com.qk.dam.es;

import cn.hutool.db.Entity;
import com.google.gson.Gson;
import com.qk.dam.jdbc.DbTypeEnum;
import com.qk.dam.jdbc.MysqlRawScript;
import com.qk.dam.jdbc.ResultTable;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.Statements;
import net.sf.jsqlparser.util.validation.Validation;
import net.sf.jsqlparser.util.validation.feature.DatabaseType;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Node;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.qk.dam.jdbc.util.JdbcSqlUtil.*;

/**
 * @author zhudaoming
 */
@Slf4j
public class ElasticSearchMain {
    private static final String ttsql = "{\"from_host\":\"http://pre.es.com:31851\",\"from_user\":\"shujuzhongtai\",\"from_password\":\"QikeDC@sjzt\",\"from_database\":null,\"to_host\":\"172.20.0.24\",\"to_user\":\"root\",\"to_password\":\"Zhudao123!\",\"to_database\":\"qkdam\",\"job_id\":\"55884d435e5a45178ee19f40afa43b61\",\"job_name\":\"hive16-test\",\"rule_id\":\"85249fb423cd415ab8dac8d98c6829ec\",\"rule_name\":\"RULE_TYPE_FIELD/hd_company/company/entname&nacaoid\",\"rule_temp_id\":20,\"task_code\":4099159221728,\"rule_meta_data\":\"entname,nacaoid\",\"sql_rpc_url\":\"http://main.dam.qk.com:31851/dqc/sql/build/realtime/python?ruleId=85249fb423cd415ab8dac8d98c6829ec\",\"warn_rpc_url\":\"http://main.dam.qk.com:31851/dqc/scheduler/result/warn/result/info?ruleId=85249fb423cd415ab8dac8d98c6829ec\"}";
    private static final String test_sql = "select count(hive_db.*) as db_size from janusgraph_vertex_index where hive_db.clusterName is not null";
    private static final Pattern p = Pattern.compile("\\s|\t|\r|\n");

    /**
     * 入口
     *
     * @param args 参数
     */
    public static void main(String[] args) throws Exception {
        log.info("开始查询");
        String jsonconfig = ttsql;
        MysqlRawScript mysqlRawScript = new Gson().fromJson(jsonconfig, MysqlRawScript.class);
        ResultTable resultTable = getResultTable(mysqlRawScript);

        String authBase64 = Base64.getEncoder().encodeToString((mysqlRawScript.getFrom_user() + ":" + mysqlRawScript.getFrom_password()).getBytes(StandardCharsets.UTF_8));

        String sqlRpcUrl = mysqlRawScript.getSql_rpc_url();

        String script = null;
        try {
            script = generateSqlScript(sqlRpcUrl);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("请求生产校验SQL错误:【{}】",e.getLocalizedMessage());
            System.exit(-1);
        }
        try (RestClient restClient = getRestClient(mysqlRawScript, authBase64)) {

            Validation validation = new Validation(List.of(DatabaseType.ANSI_SQL), script);
            if(validation.validate().size()>0){
                // 当为sql api
                Statements stmt = CCJSqlParserUtil.parseStatements(script);
                for (Statement st : stmt.getStatements()) {
                    Request rq = fromEsSql(st.toString());
                    Response response = restClient.performRequest(rq);
                    String responseBody = EntityUtils.toString(response.getEntity());
                    String rstStr = p.matcher(responseBody).replaceAll("");
                    Object o = new Gson().fromJson(rstStr, Object.class);

                    overTableData(mysqlRawScript, resultTable, (Map<String, Object>) o);
                }
            }else {
                //当为rest api
                //==>/xx/xx/x
                //{}
                String[] lines = script.split("\\r?\\n");
                String prePath = lines[0].trim();
                Request request = fromEsDsl(script, prePath);
                Response response = restClient.performRequest(request);
                String responseBody = EntityUtils.toString(response.getEntity());
                Object o = new Gson().fromJson(responseBody, Object.class);

                overTableData(mysqlRawScript, resultTable, (Map<String, Object>) o);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取es客户端
     * @param mysqlRawScript mysql原始脚本
     * @param authBase64 认证信息编码
     * @return RestClient Rest Client
     */
    private static RestClient getRestClient(MysqlRawScript mysqlRawScript, String authBase64) {
        return RestClient
                .builder(HttpHost.create(mysqlRawScript.getFrom_host()))
                .setFailureListener(new RestClient.FailureListener() {
                    @Override
                    public void onFailure(Node node) {

                    }
                })
                .setDefaultHeaders(
                        new Header[]{
                                new BasicHeader(HttpHeaders.AUTHORIZATION, "Basic " + authBase64),
                                new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json"),
                                new BasicHeader("X-Elastic-Product", "pre-test")
                        }
                )
                .build();
    }

    /**
     * 处理结果数据
     * @param mysqlRawScript 数据库原始脚本
     * @param resultTable 结果表数据
     * @param o 结果数据
     */
    private static void overTableData(MysqlRawScript mysqlRawScript, ResultTable resultTable, Map<String, Object> o) {
        Map<String, Object> rstMap = o;

        log.info("返回es结果--->字段:【{}】", rstMap.get("columns"));
        log.info("返回es结果--->数据:【{}】", rstMap.get("rows"));

        Object columns = ((List) rstMap.get("columns")).stream().map(it -> ((Map) it).get("name")).collect(Collectors.joining(","));
        String rowData = new Gson().toJson(rstMap.get("rows"));
        log.info("最终数据：columns=====>{}", columns);
        log.info("最终数据：rowData=====>{}", rowData);
        // 开始写入结果
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

    private static Request fromEsSql(final String sqlScript) {
        Request rq = new Request("POST", "/_sql");
        rq.addParameter("pretty", "true");
        rq.setEntity(new NStringEntity(
                "{\"query\":\""+sqlScript+"\"}",
                ContentType.APPLICATION_JSON));
        return rq;
    }

    private static Request fromEsDsl(final String jsonScript,final String endpoint) {
        Request rq = new Request("GET", endpoint);
        rq.addParameter("pretty", "true");
        rq.setJsonEntity(jsonScript);
        return rq;
    }


}
