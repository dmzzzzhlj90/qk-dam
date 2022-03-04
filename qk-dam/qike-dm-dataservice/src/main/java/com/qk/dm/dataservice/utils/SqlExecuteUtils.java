package com.qk.dm.dataservice.utils;

import java.util.List;
import java.util.Map;

/**
 * 执行SQL工具类
 *
 * @author wjq
 * @date 2022/03/04
 * @since 1.0.0
 */
public class SqlExecuteUtils {

    public static final String TAB_KEY = " ${TAB}";
    public static final String COL_LIST_KEY = "${COL_LIST}";


    public static final String COL_LIST_DEFAULT = "*";
    public static final String SINGLE_TABLE_SELECT_SQL_TEMPLATE = "SELECT ${COL_LIST} FROM ${TAB} WHERE 1=1 ";
    public static final String AND = " AND ";
    public static final String EQUAL_SIGN = " = ";


    /**
     * MySQL生成查询sql
     *
     * @param tableName     表名称
     * @param reqParams     请求参数
     * @param resParams     响应参数
     * @param mappingParams 参数字段映射关系
     * @return
     */
    public static String mysqlExecuteSQL(String tableName,
                                         Map<String, String> reqParams,
                                         List<String> resParams,
                                         Map<String, String> mappingParams) {
        String sql = SINGLE_TABLE_SELECT_SQL_TEMPLATE;
        //表名称
        sql = sql.replace(TAB_KEY, tableName);

        //查询字段
        if (resParams.size() > 0) {
            String selectStr = String.join(",", resParams);
            sql = sql.replace(COL_LIST_KEY, String.join(",", resParams));
        }
        sql = sql.replace(COL_LIST_KEY, COL_LIST_DEFAULT);

        //where条件
        if (reqParams.size() > 0) {
            StringBuilder whereBuffer = new StringBuilder();
            for (String paraName : reqParams.keySet()) {
                String column = mappingParams.get(paraName);
                String value = reqParams.get(paraName);
                whereBuffer.append(AND).append(column).append(EQUAL_SIGN).append(value);
            }
            sql = sql + whereBuffer;
        }
        return sql;
    }

}