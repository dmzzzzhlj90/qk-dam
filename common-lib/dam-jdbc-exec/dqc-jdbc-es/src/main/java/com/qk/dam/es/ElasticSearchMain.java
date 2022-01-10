package com.qk.dam.es;

import cn.hutool.db.Entity;
import com.google.gson.Gson;
import com.qk.dam.jdbc.DbTypeEnum;
import com.qk.dam.jdbc.MysqlRawScript;
import com.qk.dam.jdbc.ResultTable;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

import static com.qk.dam.jdbc.util.JdbcSqlUtil.*;

/**
 * @author zhudaoming
 */
@Slf4j
public class ElasticSearchMain {
    private static final String ttsql = "{\"from_host\":\"http://pre.es.com:31851/\",\"from_user\":\"shujuzhongtai\",\"from_password\":\"QikeDC@sjzt\",\"from_database\":null,\"to_host\":\"172.20.0.24\",\"to_user\":\"root\",\"to_password\":\"Zhudao123!\",\"to_database\":\"qkdam\",\"job_id\":\"55884d435e5a45178ee19f40afa43b61\",\"job_name\":\"hive16-test\",\"rule_id\":\"85249fb423cd415ab8dac8d98c6829ec\",\"rule_name\":\"RULE_TYPE_FIELD/hd_company/company/entname&nacaoid\",\"rule_temp_id\":20,\"task_code\":4099159221728,\"rule_meta_data\":\"entname,nacaoid\",\"sql_rpc_url\":\"http://main.dam.qk.com:31851/dqc/sql/build/realtime/python?ruleId=85249fb423cd415ab8dac8d98c6829ec\",\"warn_rpc_url\":\"http://main.dam.qk.com:31851/dqc/scheduler/result/warn/result/info?ruleId=85249fb423cd415ab8dac8d98c6829ec\"}";
    /**
     * 入口
     * @param args 参数
     */
    public static void main(String[] args) {
        String jsonconfig = ttsql;
        MysqlRawScript mysqlRawScript = new Gson().fromJson(jsonconfig, MysqlRawScript.class);
        String sqlRpcUrl = mysqlRawScript.getSql_rpc_url();
        ResultTable resultTable = getResultTable(mysqlRawScript);

        DB = getFromDb(mysqlRawScript, DbTypeEnum.ES);
        String sqlScript = null;
        try {
//            sqlScript = generateSqlScript(sqlRpcUrl);
            sqlScript= "select hive_db.* from janusgraph_vertex_index where hive_db.clusterName is not null";
        } catch (Exception e) {
            e.printStackTrace();
            log.error("请求生产校验SQL错误:【{}】",e.getLocalizedMessage());
            System.exit(-1);
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


}
