package com.qk.dam.es;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.google.gson.Gson;
import com.qk.dam.jdbc.DbTypeEnum;
import com.qk.dam.jdbc.RawScript;
import com.qk.dam.jdbc.ResultTable;
import lombok.extern.slf4j.Slf4j;
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

import java.nio.charset.StandardCharsets;
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
    private static final Pattern p = Pattern.compile("\\s|\t|\r|\n");

    /**
     * 入口
     *
     * @param args 参数
     */
    public static void main(String[] args) throws Exception {
        log.info("开始查询");
        String jsonconfig = args[0];
        RawScript rawScript = new Gson().fromJson(jsonconfig, RawScript.class);
        ResultTable resultTable = getResultTable(rawScript);

        String authBase64 = Base64.getEncoder().encodeToString((rawScript.getFrom_user() + ":" + rawScript.getFrom_password()).getBytes(StandardCharsets.UTF_8));

        String sqlRpcUrl = rawScript.getSql_rpc_url();

        String script = null;
        try {
            script = generateSqlScript(sqlRpcUrl);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("请求生产校验SQL错误:【{}】", e.getLocalizedMessage());
            System.exit(-1);
        }
        try (RestClient restClient = getRestClient(rawScript, authBase64)) {
            Request rq = fromEsSql(script);
            Response response = restClient.performRequest(rq);
            String responseBody = EntityUtils.toString(response.getEntity());
            String rstStr = p.matcher(responseBody).replaceAll("");
            Object o = new Gson().fromJson(rstStr, Object.class);

            overSqlData(rawScript, resultTable, (Map<String, Object>) o);

//            if (!sqlFl){
//                String[] lines = script.split("\\r?\\n");
//                String prePath = lines[0].trim();
//                Request request = fromEsDsl(script, prePath);
//                Response response = restClient.performRequest(request);
//                String responseBody = EntityUtils.toString(response.getEntity());
//                Object o = new Gson().fromJson(responseBody, Object.class);
//                log.info("rest api 返回结果数据信息：【{}】", o);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取es客户端
     *
     * @param rawScript mysql原始脚本
     * @param authBase64     认证信息编码
     * @return RestClient Rest Client
     */
    private static RestClient getRestClient(RawScript rawScript, String authBase64) {
        return RestClient
                .builder(HttpHost.create(rawScript.getFrom_host()))
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
     *
     * @param rawScript 数据库原始脚本
     * @param resultTable    结果表数据
     * @param o              结果数据
     */
    private static void overSqlData(RawScript rawScript, ResultTable resultTable, Map<String, Object> o) {
        Map<String, Object> rstMap = o;

        log.info("返回es结果--->字段:【{}】", rstMap.get("columns"));
        log.info("返回es结果--->数据:【{}】", rstMap.get("rows"));

        Object columns = ((List) rstMap.get("columns")).stream().map(it -> ((Map) it).get("name")).collect(Collectors.joining(","));
        Object columnList = ((List) rstMap.get("columns")).stream().map(it -> ((Map) it).get("name")).collect(Collectors.toList());
        String rowData = new Gson().toJson(rstMap.get("rows"));
//        log.info("最终数据：columns=====>{}", columns);
        log.info("最终数据：rowData=====>{}", rowData);
        // 开始写入结果
        Db rstDb = getToDb(rawScript, DbTypeEnum.MYSQL);

        try {

            resultTable.setRule_result(rowData);
//            resultTable.setRule_meta_data((String) columns);
            //结果表达式判断处理
            String ruleId = resultTable.getRule_id();

            rstDb.update(Entity.create("qk_dqc_scheduler_rules")
                    .set("fields", new Gson().toJson(columnList)), Entity.create().set("rule_id", ruleId));
            String warnRst = generateWarnRst(rawScript.getWarn_rpc_url());
            resultTable.setWarn_result(warnRst);
            log.info("插入结果数据入库【{}】", new Gson().toJson(resultTable));
            rstDb.insert(Entity.create(RST_TABLE).parseBean(resultTable));
            //@dmz todo 需开发刷新结果到promethues的即可
            generateWarnRst(rawScript.getWarn_rpc_url());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("执行添加结果数据失败:【{}】", e.getLocalizedMessage());
            System.exit(-1);
        }
    }

    private static Request fromEsSql(final String sqlScript) {
        Request rq = new Request("POST", "/_sql");
        rq.addParameter("pretty", "true");
        rq.setEntity(new NStringEntity(
                "{\"query\":\"" + sqlScript + "\"}",
                ContentType.APPLICATION_JSON));
        return rq;
    }

    private static Request fromEsDsl(final String jsonScript, final String endpoint) {
        Request rq = new Request("GET", endpoint);
        rq.addParameter("pretty", "true");
        rq.setJsonEntity(jsonScript);
        return rq;
    }


}
