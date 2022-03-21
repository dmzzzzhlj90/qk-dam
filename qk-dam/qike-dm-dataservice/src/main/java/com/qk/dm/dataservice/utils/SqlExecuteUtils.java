package com.qk.dm.dataservice.utils;

import com.qk.dm.dataservice.constant.CreateParamSortStyleEnum;
import com.qk.dm.dataservice.constant.OperationSymbolEnum;
import com.qk.dm.dataservice.vo.DasApiCreateOrderParasVO;
import com.qk.dm.dataservice.vo.DasApiCreateRequestParasVO;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 执行SQL工具类
 *
 * @author wjq
 * @date 2022/03/04
 * @since 1.0.0
 */
public class SqlExecuteUtils {

    public static final String TAB_KEY = "${TAB}";
    public static final String COL_LIST_KEY = "${COL_LIST}";


    public static final String COL_LIST_DEFAULT = "*";
    public static final String SINGLE_TABLE_SELECT_SQL_TEMPLATE = "SELECT ${COL_LIST} FROM ${TAB} WHERE 1=1 ";
    public static final String AND = " AND ";
    public static final String APOSTROPHE_STRING = "'";
    public static final String PERCENT_SIGN = "%";
    public static final String LIKE = " like ";
    /**
     * 分页
     */
    public static final String PAGE_NUM = "page_num";
    public static final String PAGE_SIZE = "page_size";
    public static final String LIMIT = " LIMIT ";

    public static final String ORDER_BY = " ORDER BY ";


    /*********************************************MYSQL******************************************************/
    /**
     * MySQL生成查询sql
     *
     * @param tableName     表名称
     * @param reqParams     请求参数
     * @param resParaMap    响应参数
     * @param mappingParams 参数字段映射关系
     * @param orderByStr 排序SQL
     * @return
     */
    public static String mysqlExecuteSQL(String tableName,
                                         Map<String, String> reqParams,
                                         Map<String, String> resParaMap,
                                         Map<String, List<DasApiCreateRequestParasVO>> mappingParams,
                                         String orderByStr) {
        String sql = SINGLE_TABLE_SELECT_SQL_TEMPLATE;
        // 表名称
        sql = sql.replace(TAB_KEY, tableName);

        // 查询字段
        if (resParaMap.size() > 0) {
            sql = sql.replace(COL_LIST_KEY, String.join(",", resParaMap.keySet()));
        }
        sql = sql.replace(COL_LIST_KEY, COL_LIST_DEFAULT);

        // where条件
        if (mappingParams.size() > 0) {
            StringBuilder whereBuffer = new StringBuilder();
            for (String paraName : mappingParams.keySet()) {
                DasApiCreateRequestParasVO apiCreateRequestParasVO = mappingParams.get(paraName).get(0);
                // 数据库对应字段
                String column = apiCreateRequestParasVO.getMappingName();
                // 请求参数
                String value = reqParams.get(paraName);
                // 操作符
                String conditionType = apiCreateRequestParasVO.getConditionType();
                //字段映射操作比较符号
                mysqlSwitchOperationSymbol(whereBuffer, column, value, conditionType);
            }
            sql = sql + whereBuffer;
        }

        //排序
        sql = sql +orderByStr;

        //分页查询
        sql = sql + getPageSqlPart(reqParams);
        return sql;
    }

    /**
     * 字段映射操作比较符号
     *
     * @param whereBuffer
     * @param column
     * @param value
     * @param conditionType
     */
    private static void mysqlSwitchOperationSymbol(StringBuilder whereBuffer, String column, String value, String conditionType) {
        String condition = null;
        OperationSymbolEnum symbolEnum = OperationSymbolEnum.getVal(conditionType);
        switch (Objects.requireNonNull(symbolEnum)) {
            case NO_GREATER_LESS_THAN:
                condition = OperationSymbolEnum.NO_GREATER_LESS_THAN.getValue();
                break;
            case MORE_THAN:
                condition = OperationSymbolEnum.MORE_THAN.getValue();
                break;
            case LESS_THAN:
                condition = OperationSymbolEnum.LESS_THAN.getValue();
                break;
            case LESS_THAN_EQUAL:
                condition = OperationSymbolEnum.LESS_THAN_EQUAL.getValue();
                break;
            case ALL_LIKE:
                condition = LIKE;
                value = APOSTROPHE_STRING + PERCENT_SIGN + value + PERCENT_SIGN + APOSTROPHE_STRING;
                break;
            case LEFT_LIKE:
                condition = LIKE;
                value = APOSTROPHE_STRING + value + PERCENT_SIGN + APOSTROPHE_STRING;
                break;
            default:
                condition = OperationSymbolEnum.EQUAL.getValue();
                break;
        }
        whereBuffer.append(AND).append(column).append(condition).append(value);
    }

    /**
     * 执行SQL片段中的参数替换
     *
     * @param sqlPara 输入SQL片段
     * @param reqParams 真实请求参数
     * @param orderByStr 排序SQL
     * @return
     */
    public static String mysqlSqlPara(String sqlPara, Map<String, String> reqParams, String orderByStr) {
        String replaceSql = sqlPara;
        for (String para : reqParams.keySet()) {
            String value = reqParams.get(para);
            replaceSql = replaceSql.replace(para, value);
        }

        //排序
        replaceSql = replaceSql + orderByStr;
        //分页查询
        replaceSql = replaceSql + getPageSqlPart(reqParams);
        return replaceSql;
    }

    /**
     * 分页查询
     *
     * @param reqParams
     * @return
     */
    private static String getPageSqlPart(Map<String, String> reqParams) {
        String pageSqlPart = "";
        int pageNum = Integer.parseInt(reqParams.get(PAGE_NUM));

        int pageSize = Integer.parseInt(reqParams.get(PAGE_SIZE));
        if (!ObjectUtils.isEmpty(pageNum) && !ObjectUtils.isEmpty(pageSize)) {
            int offset = (pageNum - 1) * pageSize;
            pageSqlPart += LIMIT + offset + "," + pageSize;
        }
        return pageSqlPart;
    }

    /*********************************************HIVE*****************************************************/
    /**
     * HIVE 生成查询sql
     *
     * @param tableName     表名称
     * @param reqParams     请求参数
     * @param resParaMap    响应参数
     * @param mappingParams 参数字段映射关系
     * @return
     */
    public static String hiveExecuteSQL(String tableName,
                                        Map<String, String> reqParams,
                                        Map<String, String> resParaMap,
                                        Map<String, List<DasApiCreateRequestParasVO>> mappingParams) {
        String sql = SINGLE_TABLE_SELECT_SQL_TEMPLATE;
        // 表名称
        sql = sql.replace(TAB_KEY, tableName);

        // 查询字段
        if (resParaMap.size() > 0) {
            sql = sql.replace(COL_LIST_KEY, String.join(",", resParaMap.keySet()));
        }
        sql = sql.replace(COL_LIST_KEY, COL_LIST_DEFAULT);

        // where条件
        if (reqParams.size() > 0) {
            StringBuilder whereBuffer = new StringBuilder();
            for (String paraName : reqParams.keySet()) {
                DasApiCreateRequestParasVO apiCreateRequestParasVO = mappingParams.get(paraName).get(0);
                // 数据库对应字段
                String column = apiCreateRequestParasVO.getMappingName();
                // 请求参数
                String value = reqParams.get(paraName);
                // 操作符
                String conditionType = apiCreateRequestParasVO.getConditionType();
                //字段映射操作比较符号
                mysqlSwitchOperationSymbol(whereBuffer, column, value, conditionType);
            }
            sql = sql + whereBuffer;
        }
        return sql;
    }

    /**
     * 字段映射操作比较符号
     *
     * @param whereBuffer
     * @param column
     * @param value
     * @param conditionType
     */
    private static void hiveSwitchOperationSymbol(StringBuilder whereBuffer, String column, String value, String conditionType) {
        String condition = null;
        OperationSymbolEnum symbolEnum = OperationSymbolEnum.getVal(conditionType);
        switch (Objects.requireNonNull(symbolEnum)) {
            case NO_GREATER_LESS_THAN:
                condition = OperationSymbolEnum.NO_GREATER_LESS_THAN.getValue();
                break;
            case MORE_THAN:
                condition = OperationSymbolEnum.MORE_THAN.getValue();
                break;
            case LESS_THAN:
                condition = OperationSymbolEnum.LESS_THAN.getValue();
                break;
            case LESS_THAN_EQUAL:
                condition = OperationSymbolEnum.LESS_THAN_EQUAL.getValue();
                break;
            case ALL_LIKE:
                condition = LIKE;
                value = APOSTROPHE_STRING + PERCENT_SIGN + value + PERCENT_SIGN + APOSTROPHE_STRING;
                break;
            case LEFT_LIKE:
                condition = LIKE;
                value = APOSTROPHE_STRING + value + PERCENT_SIGN + APOSTROPHE_STRING;
                break;
            default:
                condition = OperationSymbolEnum.EQUAL.getValue();
                break;
        }
        whereBuffer.append(AND).append(column).append(condition).append(value);
    }

    /**
     * 执行SQL片段中的参数替换
     *
     * @param sqlPara
     * @param reqParams
     * @return
     */
    public static String hiveSqlPara(String sqlPara, Map<String, String> reqParams) {
        String replaceSql = sqlPara;
        for (String para : reqParams.keySet()) {
            String value = reqParams.get(para);
            replaceSql = replaceSql.replace(para, value);
        }
        return replaceSql;
    }
}