package com.qk.dam.hive;

import cn.hutool.db.Db;
import cn.hutool.db.DbUtil;
import cn.hutool.db.Entity;
import cn.hutool.db.handler.EntityListHandler;
import cn.hutool.db.sql.SqlExecutor;
import com.google.gson.Gson;
import com.qk.dam.jdbc.DbTypeEnum;
import com.qk.dam.jdbc.RawScript;
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
        String jsonconfig = "{\"from_host\":\"172.21.32.4\",\"from_port\":10000,\"from_user\":\"root\",\"from_password\":null,\"from_database\":null,\"to_host\":\"172.20.0.24\",\"to_port\":3306,\"to_user\":\"root\",\"to_password\":\"Zhudao123!\",\"to_database\":\"qkdam\",\"job_id\":\"a00818c661ea4761bcdeeb608720125a\",\"job_name\":\"ttts\",\"rule_id\":\"4730b16e774d44a3a47fd45912883d43\",\"rule_name\":\"4554112512000/RULE_TYPE_FIELD/test_qiaosiwei/add_online/contact_source_url\",\"rule_temp_id\":39,\"task_code\":4554112512000,\"rule_meta_data\":\"contact_source_url\",\"sql_rpc_url\":\"http://dqc.qk.com:31851/dqc/sql/build/realtime?ruleId=4730b16e774d44a3a47fd45912883d43\",\"warn_rpc_url\":\"http://dqc.qk.com:31851/dqc/scheduler/result/warn/result/info?ruleId=4730b16e774d44a3a47fd45912883d43\"}";
        RawScript rawScript = new Gson().fromJson(jsonconfig, RawScript.class);
        String sqlRpcUrl = rawScript.getSql_rpc_url();
        ResultTable resultTable = getResultTable(rawScript);

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
            entities = runHiveSqL(rawScript,sqlScript);
            List<Object[]> rst = entities.stream()
                    .map(entity -> entity.values().toArray())
                    .collect(Collectors.toList());
            resultTable.setRule_result(new Gson().toJson(rst));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("执行质量校验SQL发生错误:【{}】",e.getLocalizedMessage());
            System.exit(-1);
        }

        Db toDb = getToDb(rawScript, DbTypeEnum.MYSQL);
        try {
            String warnRst = generateWarnRst(rawScript.getWarn_rpc_url());
            resultTable.setWarn_result(warnRst);
            log.info("插入结果数据入库【{}】",new Gson().toJson(resultTable));
            toDb.insert(Entity.create(RST_TABLE).parseBean(resultTable));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("执行添加结果数据失败:【{}】",e.getLocalizedMessage());
            System.exit(-1);
        }


    }

    public static List<Entity> runHiveSqL(RawScript rawScript,String sql) throws Exception {
        if (containsStartsWith(sql)){
            Db fromDb = getFromDb(rawScript, DbTypeEnum.HIVE);
            List<Entity> entities = execHiveSql(fromDb,sql);
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
    static List<Entity> execHiveSql(Db fromDb,String sql) throws Exception {
        List<Entity> entities = new ArrayList<>(100);
        java.sql.Statement statement = null;
        try {
            Connection connection = fromDb.getConnection();
            statement = connection.createStatement();
            // todo 暂时支持cross join，已实现合并结果输出
            statement.execute("set hive.mapred.mode='strict'");
            EntityListHandler rsh = new EntityListHandler(true);
            entities.addAll(SqlExecutor.query(connection, sql, rsh));
        } finally {
            DbUtil.close(statement);
        }
        return entities;

    }

}
